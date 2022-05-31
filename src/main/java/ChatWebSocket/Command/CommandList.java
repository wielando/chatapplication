package ChatWebSocket.Command;

import ChatWebSocket.Command.Callback.Info;
import ChatWebSocket.Command.Callback.Kick;

import java.util.HashMap;

public class CommandList {

    HashMap<String, Class<?>> commands = new HashMap<>();

    public CommandList(CommandListener commandListener) {
        commands.put("kick", Kick.class);
        commands.put("info", Info.class);
    }
}