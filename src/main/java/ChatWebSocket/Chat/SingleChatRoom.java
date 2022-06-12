package ChatWebSocket.Chat;

import ChatWebSocket.Client.Client;
import ChatWebSocket.Database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SingleChatRoom {

    private Client client = null;
    private Client partnerClient = null;

    private final Database connection = new Database();

    public void createChatRoom(Client client, Client partnerClient) {
        if (this.client == null && this.partnerClient == null) {
            this.client = client;
            this.partnerClient = partnerClient;
        }
    }

    public void loadChatMessages() throws Exception {

        String statement = "SELECT cm.message, cm.timestamp FROM chat_messages as cm " +
                "LEFT JOIN users as u ON cm.to_userid = u.id " +
                "WHERE cm.from_userid = ?";

        try (PreparedStatement queryStmt = this.connection.getDataSource().getConnection().prepareStatement(statement)) {
            queryStmt.setInt(1, this.client.getClientInfo().getId());

        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

}
