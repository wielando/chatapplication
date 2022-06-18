package ChatWebSocket.Messages.User;

import ChatWebSocket.Messages.MessageHandler;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetUserFriendList extends MessageHandler {
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

            this.app.getServerAction().sendMessageToClient(jsonObject, this.client);
        }
    }
}
