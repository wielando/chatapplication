package Decoder;

import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

import java.io.StringReader;

public class JSONTextDecoder implements Decoder.Text<JsonObject>, Decoder {

    @Override
    public JsonObject decode(String s) throws DecodeException {
        try (JsonReader jsonReader = Json.createReader(new StringReader(s))) {
            return jsonReader.readObject();
        }
    }

    @Override
    public boolean willDecode(String s) {

        try (JsonReader jsonReader = Json.createReader(new StringReader(s))) {
            jsonReader.readObject();

            return true;
        } catch (JsonException e) {
            myException(e);
            return false;
        }

    }

    public void myException(JsonException ex) {
        throw new JsonException(ex.getMessage());
    }
}
