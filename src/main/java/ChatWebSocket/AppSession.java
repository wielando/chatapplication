package ChatWebSocket;

import ChatWebSocket.Client.Client;

import java.util.HashMap;

public class Session {

    private static Session currentSession;

    private static final HashMap<Integer, HashMap<Client, Session>> clientSessionsHashMap = new HashMap<>();

    private Session(App app, Session currentSession) {
        this.setCurrentSession(currentSession);
    }

    public static Session initSession(App app, Session currentSession) {
        return new Session(app, currentSession);
    }

    private void setCurrentSession(Session currentSession) {
        Session.currentSession = currentSession;
    }

    public void addClientSessionsHashMap(Session session, Client client) {
        Integer clientId = client.getClientInfo().getId();
        HashMap<Client, Session> clientSessionHashMap = new HashMap<>();
        clientSessionHashMap.put(client, session);

        Session.clientSessionsHashMap.put(clientId, clientSessionHashMap);
    }

    public HashMap<Client, Session> getClientSession(Integer clientId) throws Exception {

        try {
            if (!Session.clientSessionsHashMap.containsKey(clientId)) throw new Exception();

            return Session.clientSessionsHashMap.get(clientId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


}
