package ChatWebSocket.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientInfo {

    private String username;
    private int online;
    private Integer id;
    private String avatarUrl;

    public ClientInfo(ResultSet set) throws SQLException {
        this.setOnline(set.getInt("online"));
        this.setUsername(set.getString("username"));
        this.setId(set.getInt("id"));
        this.setAvatarUrl(set.getString("avatarUrl"));
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return this.username;
    }

    public Integer getId() {
        return this.id;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }
}
