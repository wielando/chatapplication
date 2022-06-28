package ChatWebSocket.Chat;

import ChatWebSocket.App;
import ChatWebSocket.Client.Client;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleChatRoomAction {

    private SingleChatRoom currentRoom;
    private App app;

    private SingleChatRoomAction(SingleChatRoom room, App app) {
        this.currentRoom = room;
        this.app = app;
    }

    public static SingleChatRoomAction initSingleChatRoomAction(SingleChatRoom room, App app) {
        if (room.getRoomId() == null) return null;

        return new SingleChatRoomAction(room, app);
    }

    public void sendRoomDataToClient() throws Exception {
        ArrayList<ArrayList<Object>> conversationList = this.currentRoom.getConversationList();
        JSONObject conversationHeader = new JSONObject();
        ArrayList<JSONObject> conversationData = new ArrayList<>(conversationList.size());

        for (int i = 0; i < conversationList.size(); i++) {
            conversationData.add(new JSONObject());
        }

        int conversationDataCount = 0;

        for (ArrayList<Object> conversation : conversationList) {
            conversationData.get(conversationDataCount).put("username", conversation.get(0));
            conversationData.get(conversationDataCount).put("message", conversation.get(1));
            conversationData.get(conversationDataCount).put("liked", conversation.get(2));
            conversationData.get(conversationDataCount).put("timestamp", conversation.get(3));

            conversationDataCount++;
        }

        conversationHeader.put("messages", conversationData);
        this.app.getServerAction().sendMessageToClient(conversationHeader, this.currentRoom.getRoomClient());
    }

}
