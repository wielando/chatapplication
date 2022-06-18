package ChatWebSocket.Chat;

import ChatWebSocket.App;
import ChatWebSocket.Client.Client;
import jakarta.jms.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SingleChatRoomFactory {

    protected Client client = null;
    protected Client clientPartner = null;

    private boolean isCreateable = false;
    protected App app = null;

    private Set<Integer> clientPartnerIds = null;

    protected SingleChatRoomFactory() {
    }

    public static SingleChatRoomFactory createSingleChatRoomFactory(Client client, App app) throws Exception {
        SingleChatRoomFactory factory = new SingleChatRoomFactory();

        return factory.buildFactoryConfiguration(client, app);
    }

    private SingleChatRoomFactory buildFactoryConfiguration(Client client, App app) throws Exception {
        this.client = client;
        this.app = app;
        this.setClientPartnerId();

        return new SingleChatRoomFactory();
    }

    public SingleChatRoom createSingleChatRoom(Client clientPartner) {
        if (!this.isClientPartnerAvailable(clientPartner)) return null;

        return new SingleChatRoom();
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
