package ChatWebSocket.Chat;

import ChatWebSocket.App;
import ChatWebSocket.Client.Client;
import jakarta.jms.Session;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SingleChatRoomFactory {

    public Client client = null;
    public Client clientPartner = null;
    public App app = null;

    public Set<Integer> clientPartnerIds = null;

    public SingleChatRoom createSingleChatRoom(Client client, Client clientPartner, App app) throws Exception {
        this.setConfiguration(client, clientPartner, app);
        //if (!this.isClientPartnerAvailable(clientPartner)) return null;

        return SingleChatRoom.initSingleChatRoom(this);
    }

    private void setConfiguration(Client client, Client clientPartner, App app) throws Exception {
        this.client = client;
        this.clientPartner = clientPartner;
        this.app = app;
        this.setClientPartnerId();
    }

    private boolean isClientPartnerAvailable(Client clientPartner) {
        if (this.clientPartnerIds == null) return false;
        if (this.client.getClientInfo().getId().equals(this.clientPartner.getClientInfo().getId())) return false;

        return this.clientPartnerIds.contains(clientPartner.getClientInfo().getId());
    }

    private void setClientPartnerId() throws Exception {
        HashMap<String, HashMap<String, String>> partnerList = this.client.getClientInfo().getClientChatPartnerList();

        if (partnerList.isEmpty()) return;

        Integer clientId = null;

        for (Map.Entry<String, HashMap<String, String>> partner : partnerList.entrySet()) {
            clientId = Integer.parseInt(partner.getValue().get("id"));

            if (this.app.getAppSession().getClientSession(clientId) == null) {
                continue;
            }

            this.clientPartnerIds.add(clientId);
        }

    }


}
