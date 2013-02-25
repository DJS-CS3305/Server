package net;

import java.util.HashMap;

/**
 * Contains the methods for handling requests from connected administrator
 * clients.
 * 
 * @author Stephen Fahy
 */
public class Server {
    private static Server INSTANCE = new Server();
    
    private Authenticator auth;
    private HashMap<String, AdminServerSocket> sockets = 
            new HashMap<String, AdminServerSocket>();
    
    private Server() {
        auth = new Authenticator();
    }
    
    
    
    //getters
    public static Server get() {
        return INSTANCE;
    }
}
