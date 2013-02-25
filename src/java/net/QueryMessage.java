package net;

/**
 * Message for sending a query to the server. The query will be processed by
 * the server and a reply will be sent. The reply will also have the results
 * if the query was a SELECT query. Contents are the query alone.
 * 
 * @author Stephen Fahy
 */
public class QueryMessage extends Message {
    public static final String QUERY = "query";
    
    /**
     * Constructor.
     * 
     * @param query SQL query.
     * @param id 
     */
    public QueryMessage(int id, String query) {
        super(id);
        content.put(QUERY, query);
    }
}
