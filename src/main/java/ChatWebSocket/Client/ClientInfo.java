package ChatWebSocket.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientInfo {

    private String username;
    private int online;
    private Integer id;
    private String avatarUrl;

    private HashMap<String, HashMap<String, String>> partnerList;

    public ClientInfo(ResultSet set) throws SQLException {
        try {
            this.setOnline(set.getInt("online"));
            this.setUsername(set.getString("username"));
            this.setId(set.getInt("id"));
            this.setAvatarUrl(set.getString("avatarUrl"));
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public HashMap<String, HashMap<String, String>> getClientChatPartnerList() {
        return this.partnerList;
    }

    public void setPartnerList(HashMap<String, HashMap<String, String>> friendList) throws SQLException {
        this.partnerList = friendList;
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
