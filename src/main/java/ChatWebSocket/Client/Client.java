package ChatWebSocket.Client;

import ChatWebSocket.ChatApp;
import ChatWebSocket.Database.Mapper.ClientInfoMapper;
import ChatWebSocket.ServerAction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Client {

    private ClientInfo clientInfo;

    private ClientInfoMapper clientInfoMapper = new ClientInfoMapper();

    private final ServerAction serverAction = ChatApp.serverAction;

    public Client(String token) throws SQLException {
        String dummyToken = "SLK2"; // only test purpose!

        ResultSet client = this.loadClient(dummyToken);
        this.setClientInfo(client);

    }

    public ResultSet loadClient(String token) throws SQLException {
        try {

            String statement = "SELECT id, username, avatarUrl, online FROM users WHERE token = ?";
            HashMap<String, Object> params = new HashMap<>();
            params.put(token, String.class);

            return this.clientInfoMapper.executeStatementWithParams(statement, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Client loadClientPartner(int id) throws SQLException {

        try {

            Integer partnerId = id;

            if (partnerId.equals(this.clientInfo.getId())) {
                throw new Exception("Not allowed!");
            }

            String statement = "SELECT token FROM users WHERE id = ?";
            HashMap<String, Object> params = new HashMap<>();
            params.put(String.valueOf(id), Integer.class);

            ResultSet set = this.clientInfoMapper.executeStatementWithParams(statement, params);
            return new Client(set.getString("token"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void TextMessageToUser(Client clientPartner, String textMessage) throws Exception {
        this.serverAction.sendTextMessageToClientPartner(textMessage, clientPartner);
    }

    public void LikeTextMessage(Client client, int messageId) {

    }

    private void setClientInfo(ResultSet set) throws SQLException {
        this.clientInfo = new ClientInfo(set);
    }

    public ClientInfo getClientInfo() {
        return this.clientInfo;
    }

}
