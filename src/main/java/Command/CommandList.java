package Command;

import Command.Callback.Info;
import Command.Callback.Kick;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandList {

    HashMap<String, Class<?>> commands = new HashMap<>();

    public CommandList(CommandListener commandListener) {
        commands.put("kick", Kick.class);
        commands.put("info", Info.class);
    }
}