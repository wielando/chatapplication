package ChatWebSocket.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientInfo {

    private String username;
    private boolean online;
    private Integer id;

    public ClientInfo(ResultSet set) {

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
