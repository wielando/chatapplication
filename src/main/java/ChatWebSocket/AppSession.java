package ChatWebSocket;

import ChatWebSocket.Client.Client;
import jakarta.websocket.Session;

import java.util.HashMap;

public class AppSession {

    private static Session currentSession;

    private static final HashMap<Integer, HashMap<Client, Session>> clientSessionsHashMap = new HashMap<>();

    private AppSession(App app, Session currentSession) {
        this.setCurrentSession(currentSession);
    }

    public static AppSession initSession(App app, Session currentSession) {
        return new AppSession(app, currentSession);
    }

    private void setCurrentSession(Session currentSession) {
        AppSession.currentSession = currentSession;
    }

    public void addClientSessionsHashMap(Session session, Client client) {
        Integer clientId = client.getClientInfo().getId();
        HashMap<Client, Session> clientSessionHashMap = new HashMap<>();
        clientSessionHashMap.put(client, session);

        AppSession.clientSessionsHashMap.put(clientId, clientSessionHashMap);
    }

    public HashMap<Client, Session> getClientSession(Integer clientId) throws Exception {

        try {
            if (!AppSession.clientSessionsHashMap.containsKey(clientId)) throw new Exception();

            return AppSession.clientSessionsHashMap.get(clientId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


}
