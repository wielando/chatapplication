package ChatWebSocket.Client;

import ChatWebSocket.Database.Mapper.ClientInfoMapper;

import java.sql.SQLException;
import java.util.HashMap;

public class ClientInfo {

    private String username;
    private boolean online;
    private Integer id;

    private ClientInfoMapper clientInfoMapper;

    public ClientInfo(ClientHandler App) throws SQLException {
        this.clientInfoMapper = new ClientInfoMapper();
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public Integer getId() {
        return this.id;
    }

}
