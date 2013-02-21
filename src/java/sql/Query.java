package sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import log.ErrorLogger;

/**
 * Class for making queries to the MySQL Database. Relies on Connector
 * to make the connection.
 * 
 * @author Stephen Fahy
 */
public class Query {
    /**
     * Queries the database with the given query.
     * 
     * @param query
     * @return The ResultSet returned by the database, or null if an error is
     * encountered.
     */
    public static ResultSet query(String query) {
        ResultSet output = null;
        Connection con = Connector.getConnection();
        
        try {
            Statement st = con.createStatement();
            output = st.executeQuery(query);
        }
        catch(Exception e) {
            e.printStackTrace();
            ErrorLogger.get().log(e.toString() + " Query [" + 
                    query + "] has failed.");
        }
        
        return output;
    }
}
