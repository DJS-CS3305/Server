package net;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import log.ErrorLogger;

/**
 * Message for returning the results of SELECT query messages. Contents are
 * the ResultSet of the query translated into a linked list of rows with
 * hash maps of the column names mapped to String representations of the
 * database data. If the ResultSet used to construct this message was null,
 * the results put in the contents will also be null.
 * 
 * @author Stephen Fahy
 */
public class ResultMessage extends Message {
    public static final String RESULTS = "results";
    
    private boolean constructed;
    
    /**
     * Constructor.
     * 
     * @param rawResults The ResultSet from the SELECT query.
     * @param id 
     */
    public ResultMessage(int id, ResultSet rawResults) {
        super(id);
        constructed = true;
        
        LinkedList<HashMap<String, String>> results = 
                new LinkedList<HashMap<String, String>>();
        LinkedList<String> colNames = new LinkedList<String>();
        
        if(rawResults != null) {
            try {
                ResultSetMetaData md = rawResults.getMetaData();
                int cols = md.getColumnCount();

                for(int i = 1; i <= cols; i++) { //resultsets start at 1, not 0
                    colNames.add(md.getColumnName(i));
                }

                while(rawResults.next()) {
                    HashMap<String, String> row = new HashMap<String, String>();
                    Iterator<String> iter = colNames.iterator();

                    while(iter.hasNext()) {
                        String colName = iter.next();
                        row.put(colName, rawResults.getString(colName));
                    }

                    results.add(row);
                }

                content.put(RESULTS, results);
            }
            catch(Exception e) {
                ErrorLogger.get().log(e.toString());
                e.printStackTrace();

                constructed = false;
            }
        }
        else {
            //null rawResults
            content.put(RESULTS, null);
        }
    }
    
    //getters
    public boolean isConstructed() {
        return constructed;
    }
}
