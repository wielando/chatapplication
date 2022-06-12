package ChatWebSocket.Client;

import ChatWebSocket.ChatApp;
import ChatWebSocket.Database.Database;
import ChatWebSocket.ServerAction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Client {

    private ClientInfo clientInfo;
    private final ServerAction serverAction;

    public Client(String token) throws SQLException {
        String dummyToken = "SLK2"; // only test purpose!
        this.serverAction = ChatApp.getServerAction();

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

    public void loadClientFriendList() {

        if (this.getClientInfo().getClientChatPartnerList() != null) {
            return;
        }

        Database connection = new Database();

        String statement = "SELECT friend_ids FROM friendlist WHERE user_id = ?";

        try (PreparedStatement queryStmt = connection.getDataSource().getConnection().prepareStatement(statement)) {
            queryStmt.setInt(1, this.getClientInfo().getId());

            try (ResultSet set = queryStmt.executeQuery()) {
                if (set.next()) {
                    List<String> friendIds = List.of(set.getString("friend_ids").split(","));

                    HashMap<String, HashMap<String, String>> friendList = this.createFriendList(friendIds);

                    this.clientInfo.setPartnerList(friendList);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private HashMap<String, HashMap<String, String>> createFriendList(List<String> friendIds) {

        Database connection = new Database();
        String statement = "SELECT * FROM users WHERE id = ?";

        HashMap<String, HashMap<String, String>> friendList = new HashMap<>();

        for (String friendId : friendIds) {

            try (PreparedStatement queryStmt = connection.getDataSource().getConnection().prepareStatement(statement)) {
                queryStmt.setInt(1, Integer.parseInt(friendId));

                try (ResultSet set = queryStmt.executeQuery()) {
                    if (!set.next()) {
                        continue;
                    }

                    friendList.put(set.getString("username"), new HashMap<>());

                    friendList.get(set.getString("username")).put("avatarUrl", set.getString("avatarUrl"));
                    friendList.get(set.getString("username")).put("online", set.getString("online"));
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return friendList;
    }

    public Client loadClientPartner(int id) throws Exception {

        Integer partnerId = id;

        if (partnerId.equals(this.clientInfo.getId())) {
            return null;
        }

        Database connection = new Database();
        String statement = "SELECT token FROM users WHERE id = ?";

        try (PreparedStatement queryStmt = connection.getDataSource().getConnection().prepareStatement(statement)) {
            queryStmt.setInt(1, id);
            try (ResultSet set = queryStmt.executeQuery()) {
                if (set.next()) {
                    return new Client(set.getString("token"));
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }

        return null;
    }

    public void TextMessageToUser(Client clientPartner, String textMessage) throws Exception {
        this.serverAction.sendTextMessageToClientPartner(textMessage, clientPartner);
    }

    public ServerAction getServerAction() {
        return this.serverAction;
    }

    public ClientInfo getClientInfo() {
        return this.clientInfo;
    }

}
