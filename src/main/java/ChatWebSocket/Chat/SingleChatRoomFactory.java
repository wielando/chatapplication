package ChatWebSocket.Chat;

import ChatWebSocket.ChatApp;
import ChatWebSocket.Client.Client;
import jakarta.jms.Session;

import java.util.HashMap;
import java.util.Map;

public class SingleChatRoomFactory {

    protected Client client = null;
    protected Client clientPartner = null;

    private boolean isCreateable = false;

    HashMap<Session, Client> clientPartners = new HashMap<>();

    protected SingleChatRoomFactory() {
    }

    public static SingleChatRoomFactory createSingleChatRoomFactory(Client client) {
        SingleChatRoomFactory factory = new SingleChatRoomFactory();

        return factory.buildFactoryConfiguration(client);
    }

    private SingleChatRoomFactory buildFactoryConfiguration(Client client) {

        this.client = client;
        this.setClientPartners();

        return new SingleChatRoomFactory();
    }

    public SingleChatRoom createSingleChatRoom(Client clientPartner) {

        if (!isCreateable) return null;

        return new SingleChatRoom();
    }


    private boolean isClientPartnerAllowed() {

        return false;
    }

    private void setClientPartners() {
        HashMap<String, HashMap<String, String>> partnerList = this.client.getClientInfo().getClientChatPartnerList();

        if(partnerList.isEmpty()) return;

    }


}
