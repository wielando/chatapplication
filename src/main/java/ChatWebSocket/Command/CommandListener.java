package ChatWebSocket.Command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CommandListener {

    private final CommandList commandList = new CommandList(this);

    private String chatCommandKey = "";

    /**
     * @param chatCommand
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void listen(String chatCommand) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String trimmedChatCommand = chatCommand.replaceAll("\"", "").trim();
        List<String> chatChunks = new ArrayList<>(List.of(trimmedChatCommand.split(" ")));

        if (chatChunks.get(0).startsWith("/")) {

            String chatCommandKey = chatChunks.get(0).replaceAll("/", "");
            this.setChatCommandKey(chatCommandKey);

            chatChunks.remove(0);

            if (this.isCommandAvailable(chatCommandKey)) {
                ArrayList<String> paramValues = new ArrayList<String>(chatChunks);

                this.callCommandCallback(paramValues);
            }
        }
    }

    /**
     * @param paramValues
     */
    private void callCommandCallback(ArrayList<String> paramValues) {

        try {

            Class<?> c = commandList.commands.get(this.chatCommandKey);
            Constructor<?> ctor = c.getConstructor(CommandListener.class, ArrayList.class);
            Object object = ctor.newInstance(this, paramValues);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param key
     */
    private void setChatCommandKey(String key) {
        this.chatCommandKey = key;
    }

    /**
     * @param chatCommandKey
     * @return boolean
     */
    private boolean isCommandAvailable(String chatCommandKey) {
        return commandList.commands.containsKey(chatCommandKey);
    }

}
