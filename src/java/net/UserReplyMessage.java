package net;

/**
 * Message for replying to a user message. These are sent by the client to the
 * server when trying to reply to a user message in the database. The server
 * then processes it, and if the message has been answered already in the 
 * meantime, sends a NACK back. Otherwise, it sends an ACK.
 * 
 * @author Stephen Fahy
 */
public class UserReplyMessage extends Message {
    public static final String ORIGINAL_MESSAGE_ID = "originalMessageId";
    public static final String REPLY_TEXT = "replyText";
    
    private int originalMessageId;
    private String replyText;
    
    public UserReplyMessage(int id, int originalMessageId, String replyText) {
        super(id);
        content.put(ORIGINAL_MESSAGE_ID, originalMessageId);
        content.put(REPLY_TEXT, replyText);
    }
}
