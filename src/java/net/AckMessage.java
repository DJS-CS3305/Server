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
     * @param id The message's unique identification number.
     */
    public AckMessage(int id, boolean value) {
        super(id);
        content.put(VALUE, value);
    }
}
