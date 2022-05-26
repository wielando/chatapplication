import org.glassfish.tyrus.server.Server;

public class WebSocketServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server("localhost", 8025, "/websockets", null, ChatEndpoint.class);

        try {
            server.start();
            while(true) { }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


}

