package ChatWebSocket.Client;

import ChatWebSocket.Database.Mapper.ClientInfoMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Client {

    private ClientInfo clientInfo;

    private ClientInfoMapper clientInfoMapper = new ClientInfoMapper();

    public Client() throws SQLException {
        String dummyToken = "SLK2"; // only test purpose!

        ResultSet client = this.getClient(dummyToken);
        this.setClientInfo(client);

    }

    public ResultSet getClient(String token) throws SQLException {
        try {

            String statement = "SELECT id, username, avatarUrl, online FROM users WHERE token = ?";
            HashMap<String, Object> params = new HashMap<>();
            params.put(token, String.class);

            return this.clientInfoMapper.executeStatementWithParams(statement, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setClientInfo(ResultSet set) {
        this.clientInfo = new ClientInfo(set);
    }

    public ClientInfo getClientInfo() {
        return this.clientInfo;
    }

}
