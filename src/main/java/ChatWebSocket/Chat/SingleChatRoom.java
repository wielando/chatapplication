package ChatWebSocket.Chat;

import ChatWebSocket.App;
import ChatWebSocket.Client.Client;
import jakarta.websocket.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleChatRoom {

    private ArrayList<ArrayList<Object>> conversationList;

    private Integer roomId;
    private Client client;
    private Client clientPartner;
    private App app;
    private SingleChatRoomAction roomAction;

    private SingleChatRoom(Client client, Client clientPartner, App app) throws Exception {
        this.client = client;
        this.clientPartner = clientPartner;
        this.app = app;

        this.setRoomId();

        if (this.roomId != null) {
            this.loadChatMessages();

            this.roomAction = SingleChatRoomAction.initSingleChatRoomAction(this, app);
            this.roomAction.sendRoomDataToClient();
        }
    }

    public static SingleChatRoom initSingleChatRoom(SingleChatRoomFactory factory) throws Exception {
        return new SingleChatRoom(factory.client, factory.clientPartner, factory.app);
    }

    private void createRoomId() throws SQLException {
        String statement = "SELECT * FROM active_rooms as ar " +
                "WHERE ar.user_id = ? " +
                "LIMIT 1";

        try (PreparedStatement queryStmt = this.app.getDatabase().getDataSource().getConnection().prepareStatement(statement)) {
            queryStmt.setInt(1, this.client.getClientInfo().getId());

            try (ResultSet set = queryStmt.executeQuery()) {
                if (set.next()) return;

                String insertStatement = "INSERT INTO active_rooms (user_id) VALUES(?)";

                try (PreparedStatement queryInsertStmt = this.app.getDatabase().getDataSource().getConnection().prepareStatement(insertStatement)) {
                    queryInsertStmt.setInt(1, this.client.getClientInfo().getId());
                    queryInsertStmt.executeQuery();
                }
            }

        }
    }

    private void setRoomId() throws Exception {
        if (this.roomId != null) return;
        this.createRoomId();

        String statement = "SELECT * FROM active_rooms as ar " +
                "WHERE ar.user_id = ? " +
                "LIMIT 1";

        try (PreparedStatement queryStmt = this.app.getDatabase().getDataSource().getConnection().prepareStatement(statement)) {
            queryStmt.setInt(1, this.client.getClientInfo().getId());

            try (ResultSet set = queryStmt.executeQuery()) {
                if (set.next()) {
                    this.roomId = set.getInt("id");

                    Session clientSession = this.app.getAppSession().getClientSession(this.client.getClientInfo().getId()).get(this.client);
                    this.app.getAppSession().addRoomToSession(clientSession, this);
                }
            }
        }
    }

    private void loadChatMessages() throws SQLException {
        Integer clientId = this.client.getClientInfo().getId();
        Integer clientPartnerId = this.clientPartner.getClientInfo().getId();

        String statement = "SELECT u.username, cm.message, cm.timestamp, cm.liked FROM chat_messages AS cm " +
                "LEFT JOIN users AS u ON cm.from_userid = u.id " +
                "WHERE cm.from_userid = ? AND cm.to_userid = ? " +
                "OR cm.from_userid = ? AND cm.to_userid = ? " +
                "ORDER BY cm.timestamp";

        try (PreparedStatement queryStmt = this.app.getDatabase().getDataSource().getConnection().prepareStatement(statement)) {
            queryStmt.setInt(1, clientId);
            queryStmt.setInt(2, clientPartnerId);
            queryStmt.setInt(3, clientPartnerId);
            queryStmt.setInt(4, clientId);

            try (ResultSet set = queryStmt.executeQuery()) {
                ResultSetMetaData resultSetMetaData = set.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();

                this.conversationList = new ArrayList<>(columnCount);

                for (int i = 0; i < columnCount; i++) {
                    this.conversationList.add(new ArrayList<>());
                }

                int conversationCount = 0;

                while (set.next()) {
                    this.conversationList.get(conversationCount).add(set.getString("username"));
                    this.conversationList.get(conversationCount).add(set.getString("message"));
                    this.conversationList.get(conversationCount).add(set.getInt("liked"));
                    this.conversationList.get(conversationCount).add(set.getInt("timestamp"));

                    conversationCount++;
                }
            }
        }

    }

    public ArrayList<ArrayList<Object>> getConversationList() {
        return this.conversationList;
    }

    public Integer getRoomId() {
        return this.roomId;
    }

    public Client getRoomClient() {
        return this.client;
    }

    public Client getRoomClientPartner() {
        return this.clientPartner;
    }
}
