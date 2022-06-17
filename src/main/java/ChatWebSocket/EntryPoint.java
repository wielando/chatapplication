package ChatWebSocket;

import ChatWebSocket.Client.Client;
import ChatWebSocket.Decoder.JSONTextDecoder;
import ChatWebSocket.Encoder.JSONTextEncoder;
import ChatWebSocket.Messages.MessageRegistry;
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
public class EntryPoint {

    private final App app = new App(this);
    private final MessageRegistry messageRegistry = new MessageRegistry();

    @OnMessage
    public void onMessage(JsonObject data, Session session) throws Exception {
        try {
            if (!data.containsKey("token") && !data.containsKey("header")) {
                session.close();
                return;
            }

            Integer header = Integer.parseInt(String.valueOf(data.getString("header")));

            if (header.equals(0)) {
                session.close();
                return;
            }

            String SSOToken = String.valueOf(data.getString("token"));

            if (this.getApp().getCurrentClient() == null) {
                this.getApp().setCurrentClient(SSOToken, session);

                System.out.println("New Client with ID " + this.getApp().getCurrentClient().getClientInfo().getId() + " logged In!");
            }

            this.messageRegistry.handleMessage(this.getApp().getCurrentClient(), header, data, this.getApp());
        } catch (Exception e) {
            throw new Exception(e);
        }

    }


    @OnOpen
    public void onOpen(Session session) {
        this.getApp().getAppSession().setCurrentSession(session);
    }

    @OnClose
    public void onClose(Session session) {

    }

    public App getApp() {
        return this.app;
    }


}
