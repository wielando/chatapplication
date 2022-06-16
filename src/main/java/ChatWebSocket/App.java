package ChatWebSocket;

import ChatWebSocket.Decoder.JSONTextDecoder;
import ChatWebSocket.Encoder.JSONTextEncoder;
import jakarta.json.JsonObject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(
        value = "/start",
        encoders = {JSONTextEncoder.class},
        decoders = {JSONTextDecoder.class}
)
public class App {

    private AppSession appSession = null;

    @OnMessage
    public void onMessage(JsonObject data, Session session) throws Exception {

    }

    @OnOpen
    public void onOpen(Session session) {
        this.appSession = AppSession.initSession(this, session);
    }

    @OnClose
    public void onClose(Session session) {

    }

    public getSession

}
