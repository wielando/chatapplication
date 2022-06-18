package ChatWebSocket.Chat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SingleChatRoom extends SingleChatRoomFactory {

    private final HashMap<String, String> chatMessages = new HashMap<>();

    protected SingleChatRoom() {
        super();
    }

    private void loadChatMessages() throws SQLException {
        Integer clientId = this.client.getClientInfo().getId();
        Integer clientPartnerId = this.clientPartner.getClientInfo().getId();

        String statement = "SELECT u.username, cm.message, cm.timestamp FROM chat_messages AS cm " +
                "LEFT JOIN users AS u ON cm.from_userid = u.id " +
                "WHERE cm.from_userid = ? AND cm.to_userid = ? " +
                "OR cm.from_userid = ? AND cm.to_userid = ?" +
                "ORDER BY cm.timestamp";

        try (PreparedStatement queryStmt = this.app.getDatabase().getDataSource().getConnection().prepareStatement(statement)) {
            queryStmt.setInt(1, clientId);
            queryStmt.setInt(2, clientPartnerId);
            queryStmt.setInt(3, clientPartnerId);
            queryStmt.setInt(4, clientId);

            try (ResultSet set = queryStmt.executeQuery()) {
                while (set.next()) {
                    chatMessages.put(set.getString("username"), set.getString("message"));
                }
            }
        }

    }

}
