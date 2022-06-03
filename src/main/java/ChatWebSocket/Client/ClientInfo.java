package ChatWebSocket.Client;

public class ClientInfo {

    private String username;
    private boolean online;
    private Integer id;

    public ClientInfo(ClientHandler App) {

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
