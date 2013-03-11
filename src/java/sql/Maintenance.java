package sql;

import java.sql.SQLException;
import java.util.Date;
import log.ErrorLogger;
import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;

/**
 * Class for doing the automatic maintenance on the database.
 * 
 * @author Stephen Fahy
 */
public class Maintenance {
    public static final String TIME_FILEPATH = "./";
    public static final String TIME_FILENAME = "maintenance.time";
    public static final String DUMP_FILEPATH = "./sqlDumps/";
    
    private static Date NEXT_MAINTENANCE = new Date();
    
    /**
     * Maintains the database by removing unneeded rows, decrementing days
     * remaining counters, etc. Also readjusts the time of the next
     * maintenance session to the next day.
     * 
     * @return True if the maintenance was successful.
     */
    public static boolean maintain() {
        boolean successful = true;
        
        try {
            dumpDatabase();
            decrement();
            removeOldRows();
            //set maintenance time to the next day.
            adjustTime(NEXT_MAINTENANCE.getTime() + (1000 * 60 * 60 * 24));
        }
        catch(Exception e) {
            e.printStackTrace();
            ErrorLogger.get().log(e.toString());
            ErrorLogger.get().log("Database maintenance has failed.");
        }
        
        return successful;
    }
    
    /**
     * Decrements the days remaining counters for active registrations.
     */
    private static void decrement() throws SQLException {
        Query.query("UPDATE Registrations SET daysRemaining = daysRemaining - 1 " +
                "WHERE hasStarted = TRUE AND wasRefunded = FALSE AND " +
                "daysRemaining > 0;");
    }
    
    /**
     * Removes old rows such as registrations for courses that have been
     * refunded.
     */
    private static void removeOldRows() {
        Query.query("DELETE FROM Registrations WHERE wasRefunded = TRUE;");
    }
    
    /**
     * Dumps the database to a file for archiving.
     */
    private static void dumpDatabase() {
        Connector.dump(DUMP_FILEPATH);
    }
    
    /**
     * Dumps the maintenance time to file.
     */
    private static void dumpTime() throws IOException {
        File file = new File(TIME_FILEPATH);
        file.delete();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(String.valueOf(NEXT_MAINTENANCE.getTime()));
        bw.flush();
        bw.close();
    }
    
    /**
     * Adjusts the time of the next maintenance session. Needs to also dump the
     * time to file.
     */
    public static void adjustTime(long millis) throws IOException {
        NEXT_MAINTENANCE = new Date(millis);
        dumpTime();
    }
    
    /**
     * Returns true if it is time for maintenance and dumps the time to file.
     * @return True if it is time for maintenance.
     */
    public static boolean isMaintenanceTime() throws IOException {
        dumpTime();
        return NEXT_MAINTENANCE.before(new Date());
    }
}
