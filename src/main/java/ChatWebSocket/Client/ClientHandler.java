package ChatWebSocket.Client;

import jakarta.jms.Session;

import java.sql.SQLException;
import java.util.*;

abstract class ClientHandler {
    private final ClientInfo clientInfo = new ClientInfo(this);

    protected ClientHandler() throws SQLException {
    }


    protected ClientInfo getClientInfo() {
        return this.clientInfo;
    }


}
