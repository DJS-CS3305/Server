package net;

/**
 * Message for sending a query to the server. The query will be processed by
 * the server and a reply will be sent. Contents are the query alone.
 * 
 * @author Stephen Fahy
 */
public class QueryMessage extends Message {
    public static final String QUERY = "query";
    
    public QueryMessage(String destination, String query, int id) {
        super(destination, id);
        content.put(QUERY, query);
    }
}
