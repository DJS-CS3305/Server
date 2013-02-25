package net;

/**
 * Message for sending positive or negative replies (ie: like ACK and NACK).
 * The contents are a single boolean value.
 * 
 * @author Stephen Fahy
 */
public class AckMessage extends Message {
    public static final String VALUE = "value";
    
    /**
     * Constructor.
     * 
     * @param value The boolean value of the message.
     * @param destination The receiver's IP address as a String.
     * @param id The message's unique identification number.
     */
    public AckMessage(String destination, boolean value, int id) {
        super(destination, id);
        content.put(VALUE, value);
    }
}
