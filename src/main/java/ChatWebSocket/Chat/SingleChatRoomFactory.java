package ChatWebSocket.Chat;

import ChatWebSocket.Client.Client;
import jakarta.jms.Session;

import java.util.HashMap;

public class SingleChatRoomFactory {

    protected Client client = null;
    protected Client clientPartner = null;

    private boolean isCreateable = false;

    HashMap<Session, Client> clientPartners = new HashMap<>();

    public static SingleChatRoomFactory createSingleChatRoomFactory(Client client) {
        SingleChatRoomFactory factory = new SingleChatRoomFactory();

        return factory.buildFactoryConfiguration();
    }

    private SingleChatRoomFactory buildFactoryConfiguration() {

        return new SingleChatRoomFactory();
    }

    private SingleChatRoom createSingleChatRoom(Client clientPartner) {

        if(!isCreateable) return null;

        return new SingleChatRoom();
    }


    private boolean isClientPartnerAllowed() {

        return false;
    }

    private void setClientPartners() {

    }


}
