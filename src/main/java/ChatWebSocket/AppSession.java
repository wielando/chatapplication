package ChatWebSocket;

import ChatWebSocket.Chat.SingleChatRoom;
import ChatWebSocket.Client.Client;
import jakarta.websocket.Session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AppSession {

    private static Session currentSession = null;
    private static final HashMap<Integer, HashMap<Client, Session>> clientSessionsHashMap = new HashMap<>();
    private static Set<Session> availableSessions = new HashSet<>();

    private static HashMap<Session, HashMap<Integer, SingleChatRoom>> activeRoomList = new HashMap<>();


    private AppSession(App app) {
    }

    public static AppSession initAppSession(App app) {
        return new AppSession(app);
    }

    public void setCurrentSession(Session currentSession) {
        if (AppSession.currentSession != null) return;

        AppSession.currentSession = currentSession;
        this.addSessionInList(currentSession);
    }

    public boolean addRoomToSession(Session session, SingleChatRoom room) {
        if (!this.isSessionAvailable(session)) return false;

        if (!AppSession.activeRoomList.containsKey(session)) {
            HashMap<Integer, SingleChatRoom> roomInfo = new HashMap<>();
            roomInfo.put(room.getRoomId(), room);

            AppSession.activeRoomList.put(session, roomInfo);
            return true;
        }

        return false;
    }

    public void addSessionInList(Session session) {
        AppSession.availableSessions.add(session);
    }

    public Set<Session> getAvailableSessions() {
        return AppSession.availableSessions;
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

    public boolean isSessionAvailable(Session session) {
        if (AppSession.availableSessions.contains(session)) {
            return true;
        }

        return false;
    }


}
