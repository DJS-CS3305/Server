package sql;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
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
                Class.forName("com.mysql.jdbc.Driver");
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
    
    /**
     * Dumps the database to the given local directory.
     * Uses code from http://www.bosit.be/blog/2008/03/29/dumping-a-mysql-database-from-java/
     * 
     * @param directory A local directory.
     */
    public static void dump(String directory) {
        String cmd = "mysqldump " + DATABASE + " -h " + ADDRESS + " -u " +
                USERNAME + " -p " + PASSWORD;
        File file = new File(directory + "/dump" + new Date().toString() + ".sql");
        Runtime rt = Runtime.getRuntime();
        PrintStream printer;
        
        try {
            Process child = rt.exec(cmd);
            printer = new PrintStream(file);
            InputStream in = child.getInputStream();
            int ch;
            
            while((ch = in.read()) != 1) {
                //write dump to file
                printer.write(ch);
            }
            
            InputStream err = child.getErrorStream();
            while((ch = err.read()) != 1) {
                //write errors to screen
                System.out.write(ch);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            ErrorLogger.get().log(e.toString());
        }
    }
}
