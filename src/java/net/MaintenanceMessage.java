/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

/**
 * Message for requesting the current maintenance time or setting a new time.
 * 
 * @author saf3
 */
public class MaintenanceMessage extends Message {
    public static final String TYPE = "type";
    public static final String TIME = "time";
    public static final boolean ASK_TYPE = true;
    public static final boolean TELL_TYPE = !ASK_TYPE;
    
    /**
     * Constructor for a request of the current maintenance time.
     * @param id 
     */
    public MaintenanceMessage(int id) {
        super(id);
        content.put(TYPE, ASK_TYPE);
        content.put(TIME, 0);
    }
    
    /**
     * Constructor for telling the other machine the time. Sent to the client 
     * this tells it the current maintenance time; to the server it sets a new 
     * time.
     * @param id
     * @param millis 
     */
    public MaintenanceMessage(int id, long millis) {
        super(id);
        content.put(TYPE, TELL_TYPE);
        content.put(TIME, millis);
    }
}
