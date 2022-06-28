package ChatWebSocket;

import ChatWebSocket.Client.Client;
import jakarta.json.JsonObject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class ServerAction {

    private final Client client;
    private final App app;

    public ServerAction(App app) {
        this.app = app;

        this.client = this.app.getCurrentClient();
    }

    public void sendTextMessageToClientPartner(String textMessage, Client clientPartner) throws Exception {
        if (Objects.equals(clientPartner.getClientInfo().getId(), this.client.getClientInfo().getId())) {
            return;
        }

        Integer partnerId = clientPartner.getClientInfo().getId();
        Session clientPartnerSession = this.app.getAppSession().getClientSession(partnerId).get(clientPartner);

        if (this.app.getAppSession().isSessionAvailable(clientPartnerSession)) {

            JSONObject textMessageObject = new JSONObject();
            textMessageObject.put("textMessage", textMessage);

            this.broadcastToSession(clientPartnerSession, textMessageObject);
        }
    }

    public void sendMessageToClient(JSONObject jsonObject, Client client) throws Exception {
        Session clientSession = this.app.getAppSession().getClientSession(client.getClientInfo().getId()).get(client);

        if (this.app.getAppSession().isSessionAvailable(clientSession)) {
            this.broadcastToSession(clientSession, jsonObject);
        }
    }

    private void broadcastToSession(Session session, JSONObject jsonObject) throws Exception {

        if (!this.app.getAppSession().isSessionAvailable(session)) return;

        try {
            session.getBasicRemote().sendObject(jsonObject);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void broadcast(JsonObject output) {
        Set<Session> AppSessions = this.app.getAppSession().getAvailableSessions();

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
