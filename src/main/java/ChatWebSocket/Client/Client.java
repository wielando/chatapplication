package ChatWebSocket.Client;

import ChatWebSocket.ChatApp;
import ChatWebSocket.Database.Database;
import ChatWebSocket.ServerAction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {

    private ClientInfo clientInfo;

    private final ServerAction serverAction = ChatApp.serverAction;

    public Client(String token) throws SQLException {
        String dummyToken = "SLK2"; // only test purpose!

        this.clientInfo = this.loadClientInfo(token);
    }

    private ClientInfo loadClientInfo(String token) throws SQLException {

        ClientInfo info = null;

        String statement = "SELECT id, username, avatarUrl, online FROM users WHERE token = ?";
        Database connection = new Database();

        try (PreparedStatement queryStmt = connection.getDataSource().getConnection().prepareStatement(statement)) {
            queryStmt.setString(1, token);

            try (ResultSet set = queryStmt.executeQuery()) {
                if (set.next()) {
                    info = new ClientInfo(set);

                    return info;

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /*public Client loadClientPartner(int id) throws Exception {

        Integer partnerId = id;

        if (partnerId.equals(this.clientInfo.getId())) {
            return null;
        }

        String statement = "SELECT token FROM users WHERE id = ?";
        DataMapper mapper = new DataMapper();

        try (PreparedStatement queryStmt = mapper.buildStatement(statement)) {
            queryStmt.setInt(1, id);
            ResultSet set = mapper.executeStatement();

            return new Client(set.getString("token"));
        } catch (Exception e) {
            throw new Exception(e);
        }

    }*/

    public void TextMessageToUser(Client clientPartner, String textMessage) throws Exception {
        this.serverAction.sendTextMessageToClientPartner(textMessage, clientPartner);
    }

    public void LikeTextMessage(Client client, int messageId) {

    }

    public ClientInfo getClientInfo() {
        return this.clientInfo;
    }

}
