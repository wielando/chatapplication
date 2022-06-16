package ChatWebSocket;

import ChatWebSocket.Client.Client;
import ChatWebSocket.Messages.MessageRegistry;
import jakarta.json.JsonObject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import ChatWebSocket.Decoder.JSONTextDecoder;
import ChatWebSocket.Encoder.JSONTextEncoder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(
        value = "/start",
        encoders = {JSONTextEncoder.class},
        decoders = {JSONTextDecoder.class}
)
public class ChatApp {
    private static final Set<AppSession> sessions = new HashSet<>();

    public static final HashMap<Client, AppSession> clientSessionsHashMap = new HashMap<>();

    private final MessageRegistry registry = new MessageRegistry();
    private Client client = null;
    private static ServerAction serverAction;


    public Set<AppSession> getSessions() {
        return ChatApp.sessions;
    }

    public Client getClient() {
        return this.client;
    }

    public static HashMap<Client, AppSession> getClientSessionsHashMap() {
        return ChatApp.clientSessionsHashMap;
    }

    @OnMessage
    public void onMessage(JsonObject data, AppSession session) throws Exception {

        try {

            if (!data.containsKey("token") && !data.containsKey("header")) {
                session.close();
                return;
            }

            if (Integer.parseInt(String.valueOf(data.getString("header"))) == 0) {
                session.close();
                return;
            }

            serverAction = new ServerAction(this);

            Integer header = Integer.parseInt(String.valueOf(data.getString("header")));
            String SSOToken = String.valueOf(data.getString("token"));

            if (this.client == null) {
                this.client = new Client(SSOToken);
                this.clientSessionsHashMap.put(this.client, session);

                System.out.println("New Client with logged in with ID " + this.client.getClientInfo().getId());
            }

            registry.handleMessage(this.client, header, data);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static ServerAction getServerAction() {
        return serverAction;
    }

}

