package ChatWebSocket.Client;

import ChatWebSocket.App;
import ChatWebSocket.Database.Database;
import ChatWebSocket.ServerAction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Client {

    private ClientInfo clientInfo;
    private App app;

    public Client(String token, App app) throws SQLException {
        String dummyToken = "SLK2"; // only test purpose!
        this.app = app;

        this.clientInfo = this.loadClientInfo(token);
    }

    private ClientInfo loadClientInfo(String token) throws SQLException {
        ClientInfo info = null;

        String statement = "SELECT id, username, avatarUrl, online FROM users WHERE token = ?";

        try (PreparedStatement queryStmt = this.app.getDatabase().getDataSource().getConnection().prepareStatement(statement)) {
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

        String statement = "SELECT friend_ids FROM friendlist WHERE user_id = ?";

        try (PreparedStatement queryStmt = this.app.getDatabase().getDataSource().getConnection().prepareStatement(statement)) {
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
        String statement = "SELECT * FROM users WHERE id = ?";

        HashMap<String, HashMap<String, String>> friendList = new HashMap<>();

        for (String friendId : friendIds) {

            try (PreparedStatement queryStmt = this.app.getDatabase().getDataSource().getConnection().prepareStatement(statement)) {
                queryStmt.setInt(1, Integer.parseInt(friendId));

                try (ResultSet set = queryStmt.executeQuery()) {
                    if (!set.next()) {
                        continue;
                    }

                    friendList.put(set.getString("username"), new HashMap<>());

                    friendList.get(set.getString("username")).put("id", set.getString("id"));
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

        String statement = "SELECT token FROM users WHERE id = ?";

        try (PreparedStatement queryStmt = this.app.getDatabase().getDataSource().getConnection().prepareStatement(statement)) {
            queryStmt.setInt(1, id);
            try (ResultSet set = queryStmt.executeQuery()) {
                if (set.next()) {
                    return new Client(set.getString("token"), this.app);
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }

        return null;
    }

    public void TextMessageToUser(Client clientPartner, String textMessage) throws Exception {
        this.app.getServerAction().sendTextMessageToClientPartner(textMessage, clientPartner);
    }

    public ClientInfo getClientInfo() {
        return this.clientInfo;
    }

}
