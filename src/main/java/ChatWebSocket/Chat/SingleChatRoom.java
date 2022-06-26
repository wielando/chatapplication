package ChatWebSocket.Chat;

import ChatWebSocket.App;
import ChatWebSocket.Client.Client;
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

    private SingleChatRoom(Client client, Client clientPartner, App app) throws Exception {
        this.client = client;
        this.clientPartner = clientPartner;
        this.app = app;

        this.loadChatMessages();
        this.sendRoomDataToClient();
    }

    public static SingleChatRoom initSingleChatRoom(SingleChatRoomFactory factory) throws Exception {
        return new SingleChatRoom(factory.client, factory.clientPartner, factory.app);
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

    public void sendRoomDataToClient() throws Exception {

        JSONObject conversationHeader = new JSONObject();
        ArrayList<JSONObject> conversationData = new ArrayList<>(this.conversationList.size());

        for(int i = 0; i < this.conversationList.size(); i++) {
            conversationData.add(new JSONObject());
        }

        int conversationDataCount = 0;

        for(ArrayList<Object> conversation : this.conversationList) {
            conversationData.get(conversationDataCount).put("username", conversation.get(0));
            conversationData.get(conversationDataCount).put("message", conversation.get(1));
            conversationData.get(conversationDataCount).put("liked", conversation.get(2));
            conversationData.get(conversationDataCount).put("timestamp", conversation.get(3));

            conversationDataCount++;
        }

        conversationHeader.put("messages", conversationData);
        this.app.getServerAction().sendMessageToClient(conversationHeader, this.client);
    }

}
