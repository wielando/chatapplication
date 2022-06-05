package ChatWebSocket;

import ChatWebSocket.Client.Client;
import ChatWebSocket.Messages.MessageRegistry;
import jakarta.json.JsonObject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import ChatWebSocket.Decoder.JSONTextDecoder;
import ChatWebSocket.Encoder.JSONTextEncoder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(
        value = "/start",
        encoders = {JSONTextEncoder.class},
        decoders = {JSONTextDecoder.class}
)
public class ChatApp {
    private static final Set<Session> sessions = new HashSet<>();
    private final MessageRegistry registry = new MessageRegistry();

    private static Client client;

    @OnMessage
    public void onMessage(JsonObject data, Session session) throws Exception {

        try {

            if(!data.containsKey("token") && !data.containsKey("header")) {
                session.close();
                return;
            }

            if(Integer.parseInt(String.valueOf(data.get("header"))) == 0) {
                session.close();
                return;
            }

            Integer header = Integer.parseInt(String.valueOf(data.get("header")));

            String SSOToken = String.valueOf(data.get("token"));
            Client client = new Client(SSOToken);

            registry.handleMessage(client, header);


        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);

    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Client-ID " + session.getId() + " has disconnected");
        sessions.remove(session);
    }

    private static void broadcast(JsonObject output) {
        for (Session session : sessions) {
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

