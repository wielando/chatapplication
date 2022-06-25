package ChatWebSocket.Messages.User;

import ChatWebSocket.Chat.SingleChatRoomFactory;
import ChatWebSocket.Client.Client;
import ChatWebSocket.Messages.MessageHandler;

public class PostUserMessage extends MessageHandler {
    @Override
    public void handle() throws Exception {

        System.out.println("Handle!");

        Client clientPartner = this.client.loadClientPartner(2);

        SingleChatRoomFactory factory = new SingleChatRoomFactory();
        factory.createSingleChatRoom(this.client, clientPartner, this.app);
    }
}
