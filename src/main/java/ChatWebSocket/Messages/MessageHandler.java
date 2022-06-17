package ChatWebSocket.Messages;

import ChatWebSocket.App;
import ChatWebSocket.Client.Client;

public abstract class MessageHandler {

    public Client client;
    public ClientMessage clientMessage;

    public App app;

    public abstract void handle() throws Exception;
}
