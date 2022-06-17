package ChatWebSocket;

import ChatWebSocket.Client.Client;
import jakarta.websocket.Session;

public class App {
    private AppSession appSession = null;
    private ServerAction serverAction = null;
    private static Client currentClient = null;

    public App(EntryPoint entryPoint) {
        this.appSession = AppSession.initAppSession(this);
        this.serverAction = new ServerAction(this);
    }

    public ServerAction getServerAction() {
        return this.serverAction;
    }

    public AppSession getAppSession() {
        return this.appSession;
    }

    public Client getCurrentClient() {
        if (App.currentClient == null) return null;

        return App.currentClient;
    }

    public void setCurrentClient(String SSOToken, Session clientSession) throws Exception {
        try {

            if (App.currentClient != null) throw new Exception("Client is already loaded!");

            App.currentClient = new Client(SSOToken);
            this.getAppSession().addClientSessionsHashMap(clientSession, this.getCurrentClient());

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
