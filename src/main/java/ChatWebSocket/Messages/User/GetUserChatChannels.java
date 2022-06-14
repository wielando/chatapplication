package ChatWebSocket.Messages.User;

import ChatWebSocket.Chat.SingleChatRoomFactory;
import ChatWebSocket.ChatApp;
import ChatWebSocket.Messages.MessageHandler;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetUserChatChannels extends MessageHandler {
    @Override
    public void handle() throws Exception {
        this.client.loadClientFriendList();

        HashMap<String, HashMap<String, String>> friendList = this.client.getClientInfo().getClientChatPartnerList();

        if (friendList != null) {

            JSONObject userData = new JSONObject();
            JSONObject jsonObject = new JSONObject();

            for (Map.Entry<String, HashMap<String, String>> friend : friendList.entrySet()) {
                userData.put("avatarUrl", friend.getValue().get("avatarUrl"));
                userData.put("online", friend.getValue().get("online"));

                jsonObject.put(friend.getKey(), userData);
            }

            this.client.getServerAction().sendMessageToClient(jsonObject, this.client);
        }
    }
}
