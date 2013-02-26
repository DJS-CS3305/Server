package net;

/**
 * Checks the server for activity in a loop.
 * 
 * @author Stephen Fahy
 */
public class ServerChecker {
    private Server server;
    
    /**
     * Constructor.
     */
    public ServerChecker() {
        server = Server.get();
        loop();
    }
    
    /**
     * Loops forever, checking sockets.
     */
    private void loop() {
        while(true) {
            server.checkAuth();
            server.checkClients();
        }
    }
}
