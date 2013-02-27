package net;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import log.ErrorLogger;

/**
 * Class for constructing messages that will go between the Server and the
 * Administrator Clients. Specific messages must be subclasses of this.
 * The content of the message is not set by this superclass; it must be done
 * by the subclasses.
 * 
 * @author Stephen Fahy
 */
public abstract class Message implements Serializable {
    protected HashMap<String, Object> content;
    private int id;
    
    /**
     * Constructor.
     * 
     * @param id The message's unique identification number.
     */
    public Message(int id) {
        this.id = id;
        content = new HashMap<String, Object>();
    }
    
    /**
     * 
     * @param out The output stream for a net socket.
     */
    public void send(ObjectOutputStream out) {
        try {
            out.writeObject(this);
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
        }
    }
    
    //getters
    public HashMap<String, Object> getContent() {
        return content;
    }
    public int getId() {
        return id;
    }
}
