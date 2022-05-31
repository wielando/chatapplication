package ChatWebSocket;

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
public class ChatEndpoint {
    private static final Set<Session> sessions = new HashSet<>();

    @OnMessage
    public void onMessage(JsonObject data, Session session) {




        broadcast(data);
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

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("Client has connected with ID: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Client-ID " + session.getId() + " has disconnected");
        sessions.remove(session);
    }
}

