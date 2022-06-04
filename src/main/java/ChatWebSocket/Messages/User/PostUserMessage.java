package ChatWebSocket.Messages.User;

import ChatWebSocket.Messages.MessageHandler;

public class PostUserMessage extends MessageHandler {
    @Override
    public void handle() {
        System.out.println("Message is handled!");
    }
}
