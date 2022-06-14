package ChatWebSocket.Messages.User;

import ChatWebSocket.Chat.SingleChatRoomFactory;
import ChatWebSocket.Messages.MessageHandler;

public class PostUserMessage extends MessageHandler {
    @Override
    public void handle() {
        SingleChatRoomFactory factory = SingleChatRoomFactory.createSingleChatRoomFactory(this.client);
    }
}
