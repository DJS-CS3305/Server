package net;

/**
 * A message that sends a client their port for the connection after 
 * authentication.
 * 
 * @author Stephen Fahy
 */
public class ConnectionMessage extends Message {
    public static final String PORT = "port";
    
    public ConnectionMessage(int id, int port) {
        super(id);
        content.put(PORT, port);
    }
}
