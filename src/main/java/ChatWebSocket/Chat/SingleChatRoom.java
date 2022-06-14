package ChatWebSocket.Chat;

import ChatWebSocket.Client.Client;
import ChatWebSocket.Database.Database;


public class SingleChatRoom extends SingleChatRoomFactory {

    private final Database connection = new Database();

    protected SingleChatRoom() {
        super();

    }
}
