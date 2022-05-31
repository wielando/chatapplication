package ChatWebSocket.Command.Callback;

import ChatWebSocket.Command.AbstractCallback;
import ChatWebSocket.Command.CallbackInterface;
import ChatWebSocket.Command.CommandListener;

import java.util.ArrayList;

import java.util.List;

public class Kick extends AbstractCallback implements CallbackInterface {

    public Kick(CommandListener object, ArrayList<String> paramValues) {
        super(object, paramValues, List.of("username"));

        this.callbackResponse();
    }


    @Override
    public void callbackResponse() {
        if(!this.isCallable) {
            // PERFORM  ACTION
        }
    }

    @Override
    public void buildCallback() {

    }
}
