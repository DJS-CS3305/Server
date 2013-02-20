package net;

/**
 * Class for managing requests from administrator clients. This class does not
 * set up the connections though, it accepts them from the Authenticator.
 * 
 * @author Stephen Fahy
 */
public class Server {
    //group of connections.
    //list of possible & used port numbers.
    
    /**
     * @return An unused port number.
     */
    private static int getFreePort() {
        return 0;
    }
    
    /**
     * Handles an incoming message.
     */
    private static void handleRequest() {
        
    }
    
    /**
     * Adds a connection to the server's handled connections.
     */
    private static void addConnection() {
        
    }
    
    /**
     * Checks if a connection is still alive.
     * 
     * @return True if the client is still connected.
     */
    private static boolean checkLife() {
        return false;
    }
}
