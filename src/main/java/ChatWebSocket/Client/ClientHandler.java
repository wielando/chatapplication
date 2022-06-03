package ChatWebSocket.Client;

import jakarta.jms.Session;

import java.util.*;

abstract class ClientHandler {
    private final ClientInfo clientInfo = new ClientInfo(this);


    protected ClientInfo getClientInfo() {
        return this.clientInfo;
    }


}
