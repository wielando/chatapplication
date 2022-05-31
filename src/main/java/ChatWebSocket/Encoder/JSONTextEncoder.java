package ChatWebSocket.Encoder;

import jakarta.json.JsonObject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class JSONTextEncoder implements Encoder.Text<JsonObject> {
    @Override
    public String encode(JsonObject object) throws EncodeException {
        return object.toString();
    }
}
