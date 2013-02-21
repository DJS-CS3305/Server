package net;

import java.util.HashMap;

/**
 * Class for constructing messages that will go between the Server and the
 * Administrator Clients. Specific messages must be subclasses of this.
 * The content of the message is not set by this superclass; it must be done
 * by the subclasses.
 * 
 * @author Stephen Fahy
 */
public abstract class Message {
    private String destination;
    protected HashMap<String, Object> content;
    private int id;
    
    /**
     * Constructor.
     * 
     * @param destination The receiver's IP address as a String.
     * @param id The message's unique identification number.
     */
    public Message(String destination, int id) {
        this.destination = destination;
        this.id = id;
        content = new HashMap<String, Object>();
    }
    
    /**
     * Sends the message.
     */
    public void send() {
        
    }
    
    //getters
    public String getDestination() {
        return destination;
    }
    public HashMap<String, Object> getContent() {
        return content;
    }
    public int getId() {
        return id;
    }
}
