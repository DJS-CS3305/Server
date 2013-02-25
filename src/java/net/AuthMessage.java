package net;

/**
 * Message for requesting authentication for the client from the server.
 * Contents are the username and password hash.
 * 
 * @author Stephen Fahy
 */
public class AuthMessage extends Message {
    public static final String USERNAME = "user";
    public static final String PASSWORD = "pass";
    
    /**
     * Constructor.
     * 
     * @param id
     * @param username The username the user wishes to log in with.
     * @param password The password associated with the username. Stores as hash.
     */
    public AuthMessage(int id, String username, String password) {
        super(id);
        content.put(USERNAME, username);
        content.put(PASSWORD, password.hashCode());
    }
}
