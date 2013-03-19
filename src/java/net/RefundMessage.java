package net;

/**
 * Class for requesting refunds on behalf of a user.
 * 
 * @author Stephen Fahy
 */
public class RefundMessage extends Message {
    public static final String USERNAME = "user";
    public static final String CODE = "code";
    
    /**
     * Constructor.
     * 
     * @param id The message id#.
     * @param username The username of the person receiving the refund.
     * @param courseCode The code of the course the person is getting a refund for.
     */
    public RefundMessage(int id, String username, String courseCode) {
        super(id);
        content.put(USERNAME, username);
        content.put(CODE, courseCode);
    }
}
