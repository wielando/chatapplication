package Command.Callback;

import Command.AbstractCallback;
import Command.CallbackInterface;
import Command.CommandListener;

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
            // Auto message
        }

        
    }

    @Override
    public void buildCallback() {

    }


}
