package ChatWebSocket.Messages;

import ChatWebSocket.Client.Client;

public abstract class MessageHandler {

    public Client client;
    public ClientMessage clientMessage;

    public abstract void handle() throws Exception;
}
