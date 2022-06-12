package ChatWebSocket.Messages.User;

import ChatWebSocket.Chat.SingleChatRoom;
import ChatWebSocket.Client.Client;
import ChatWebSocket.Messages.MessageHandler;

public class GetChatPartner extends MessageHandler {
    @Override
    public void handle() throws Exception {

        Integer partnerId = null;

        if (this.clientMessage.getClientMessage().get("userid") != null) {
            partnerId = Integer.parseInt(String.valueOf(this.clientMessage.getClientMessage().get("userid")));
            Client partnerClient = this.client.loadClientPartner(partnerId);

            if (partnerClient == null) return;

            SingleChatRoom singleChatRoom = new SingleChatRoom();
            singleChatRoom.createChatRoom(this.client, partnerClient);
        }

    }
}
