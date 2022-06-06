package ChatWebSocket;

import ChatWebSocket.Client.Client;
import jakarta.json.JsonObject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ServerAction {

    private final Client client;
    private final ChatApp app;

    public ServerAction(ChatApp chatApp) {
        this.app = chatApp;

        this.client = this.app.getClient();
    }

    public void sendToClientPartner(JsonObject output, Client clientPartner) {
        if (Objects.equals(clientPartner.getClientInfo().getId(), this.client.getClientInfo().getId())) {
            return;
        }


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
