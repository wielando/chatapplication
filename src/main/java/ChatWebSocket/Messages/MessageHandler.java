package ChatWebSocket.Messages;

import ChatWebSocket.Client.Client;

public abstract class MessageHandler {

    protected Client client;

    public abstract void handle();
}
