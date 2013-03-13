package sql;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
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
    private static final String DUMP_COMMAND = "D:\\wamp\\bin\\mysql\\mysql5.5.24" + 
            "\\bin\\mysqldump.exe -h " + ADDRESS + " -u " + USERNAME + " -p" + 
            PASSWORD + " --add-drop-database -B " + DATABASE;
    private static int BUFFER = 10485760;
    
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
     * Dumps the database to the given directory.
     * Uses code from 
     * http://www.bosit.be/blog/2008/03/29/dumping-a-mysql-database-from-java/
     * and
     * http://gilbertadjin.wordpress.com/2009/06/02/backup-mysql-database-from-a-java-application/
     * 
     * @param directory A local directory.
     */
    public static void dump(String directory) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
        
        try {
            String canonPath = new File(directory).getCanonicalPath();
            String filename = "dump_" + sdf.format(new Date()) + ".sql";
            
            new File(canonPath).mkdirs(); //make file path if it doesn't exist
            File file = new File(canonPath + "/" + filename);
            file.createNewFile();
            String cmd = DUMP_COMMAND + " -r \"" + file.getCanonicalPath() + "\"";
            
            Process child = Runtime.getRuntime().exec(cmd);
            int processFinished = child.waitFor();
            
            if(processFinished == 0) {
                //success
            }
            else {
                //failure
                ErrorLogger.get().log("Failed to dump SQL.");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            ErrorLogger.get().log(e.toString());
        }
    }
}
