package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import log.ErrorLogger;

/**
 * Class for connecting to the MySQL Database.
 * 
 * @author Stephen Fahy
 */
public class Connector {
    private static final String ADDRESS = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "SCOffice";
    private static final String USERNAME = "backend";
    private static final String PASSWORD = "xRd43FF65Vc";
    
    private static Connection con;
    
    /**
     * Creates a connection to the database if none exists and returns the
     * connection.
     * 
     * @return Connection to the database.
     */
    public static Connection getConnection() {
        if(con == null) {
            String url = "jdbc:mysql://" + ADDRESS + ":" + PORT + "/" +
                    DATABASE;
            
            try {
                con = DriverManager.getConnection(url, USERNAME, PASSWORD);
            }
            catch(Exception e) {
                e.printStackTrace();
                ErrorLogger.get().log(e.toString() + 
                        " SQL connection was unable to be established");
            }
        }
        
        return con;
    }
    
    /**
     * Closes the connection.
     */
    public static void close() {
        try {
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            ErrorLogger.get().log(e.toString() + 
                    " SQL connection was unable to close.");
        }
        finally {
            con = null;
        }
    }
}
