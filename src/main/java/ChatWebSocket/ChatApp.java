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
    private static final Set<Session> sessions = new HashSet<>();

    private final HashMap<Client, Session> clientSessionsHashMap = new HashMap<>();

    private final MessageRegistry registry = new MessageRegistry();
    private Client client = null;
    public static ServerAction serverAction;


    public Set<Session> getSessions() {
        return ChatApp.sessions;
    }


    public Client getClient() {
        return this.client;
    }

    public HashMap<Client, Session> getClientSessionsHashMap() {
        return this.clientSessionsHashMap;
    }

    @OnMessage
    public void onMessage(JsonObject data, Session session) throws Exception {

        System.out.println("Client send message");

        try {

            if (!data.containsKey("token") && !data.containsKey("header")) {
                session.close();
                return;
            }

            if (Integer.parseInt(String.valueOf(data.getString("header"))) == 0) {
                session.close();
                return;
            }

            Integer header = Integer.parseInt(String.valueOf(data.getString("header")));
            String SSOToken = String.valueOf(data.getString("token"));

            System.out.println(SSOToken);

            if (this.client == null) {
                this.client = new Client(SSOToken);
                this.clientSessionsHashMap.put(this.client, session);

                System.out.println("New Client with logged in with ID " + this.client.getClientInfo().getId());
            }

            ChatApp.serverAction = new ServerAction(this);
            registry.handleMessage(this.client, header);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("Session connected");
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Client-ID " + session.getId() + " has disconnected");
        sessions.remove(session);
    }
}

