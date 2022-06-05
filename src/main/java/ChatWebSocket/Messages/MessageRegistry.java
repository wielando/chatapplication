package ChatWebSocket.Messages;

import ChatWebSocket.Client.Client;
import ChatWebSocket.Messages.User.PostUserMessage;

import java.util.HashMap;

public class MessageRegistry {

    HashMap<Integer, Class<? extends MessageHandler>> messages;

    public MessageRegistry() {
        this.messages = new HashMap<>();

        this.registerUserMessages();
    }

    public void handleMessage(Client client, Integer header) throws Exception {
        try {

            if (this.isRegistered(header)) {
                Class<? extends MessageHandler> handlerClass = this.messages.get(header);

                if (handlerClass == null) throw new Exception("Unknown message with header " + header);


                final MessageHandler handler = handlerClass.newInstance();
                handler.handle();

                handler.client = client;
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private boolean isRegistered(int header) {
        return this.messages.containsKey(header);
    }

    private void registerHandler(Integer header, Class<? extends MessageHandler> handler) {
        if (header < 0) {
            return;
        }

        if (messages.containsKey(header)) {
            return;
        }

        this.messages.putIfAbsent(header, handler);
    }

    private void registerUserMessages() {
        this.registerHandler(Packets.PostUserMessage, PostUserMessage.class);
    }

}
