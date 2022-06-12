package ChatWebSocket.Messages;

import jakarta.json.JsonObject;

public class ClientMessage {

    private JsonObject clientMessage;

    public void addClientMessage(JsonObject clientMessage) {
        this.clientMessage = clientMessage;
    }

    public JsonObject getClientMessage() {
        return this.clientMessage;
    }

}
