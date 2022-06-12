package ChatWebSocket.Messages.User;

import ChatWebSocket.ChatApp;
import ChatWebSocket.Messages.MessageHandler;

import java.util.HashMap;

public class GetUserChatChannels extends MessageHandler {
    @Override
    public void handle() {
        this.client.loadClientFriendList();

        HashMap<String, HashMap<String, String>> friendList = this.client.getClientInfo().getClientChatPartnerList();

        if (friendList != null) {
            this.client.getServerAction().initialFriendListToClient(friendList, this.client);
        }
    }
}
