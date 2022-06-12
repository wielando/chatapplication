package ChatWebSocket;

import ChatWebSocket.Client.Client;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class ServerAction {

    private final Client client;
    private final ChatApp app;

    public ServerAction(ChatApp chatApp) {
        this.app = chatApp;

        this.client = this.app.getClient();
    }

    public void sendTextMessageToClientPartner(String textMessage, Client clientPartner) throws Exception {
        if (Objects.equals(clientPartner.getClientInfo().getId(), this.client.getClientInfo().getId())) {
            return;
        }

        Session clientPartnerSession = this.app.getClientSessionsHashMap().get(clientPartner);

        if (this.isSessionAvailable(clientPartnerSession)) {

            JSONObject textMessageObject = new JSONObject();
            textMessageObject.put("textMessage", textMessage);

            this.broadcastToSession(clientPartnerSession, textMessageObject);
        }
    }

    public void sendMessageToClient(JSONObject jsonObject, Client client) throws Exception {
        Session clientSession = this.app.getClientSessionsHashMap().get(client);

        if (this.isSessionAvailable(clientSession)) {
            this.broadcastToSession(clientSession, jsonObject);
        }
    }

    private void broadcastToSession(Session session, JSONObject jsonObject) throws Exception {

        if (!this.isSessionAvailable(session)) return;

        try {
            session.getBasicRemote().sendObject(jsonObject);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private boolean isSessionAvailable(Session session) {
        Set<Session> availableSessions = this.app.getSessions();

        if (availableSessions.contains(session)) {
            return true;
        }

        return false;
    }

    public void broadcast(JsonObject output) {
        Set<Session> AppSessions = this.app.getSessions();

        for (Session session : AppSessions) {
            synchronized (session) {
                try {
                    session.getBasicRemote().sendObject(output);
                } catch (EncodeException | IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
