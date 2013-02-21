package sql;

import java.util.Date;

/**
 * Class for doing the automatic maintenance on the database.
 * 
 * @author Stephen Fahy
 */
public class Maintenance {
    public static final String TIME_FILEPATH = "";
    public static final String TIME_FILENAME = "maintenance.time";
    
    private static Date NEXT_MAINTENANCE = new Date();
    
    /**
     * Maintains the database by removing unneeded rows, decrementing days
     * remaining counters, etc. Also readjusts the time of the next
     * maintenance session.
     * 
     * @return True if the maintenance was successful.
     */
    private static boolean maintain() {
        boolean successful = true;
        
        dumpDatabase();
        decrement();
        removeOldRows();
        //set maintenance time to the next day.
        adjustTime(NEXT_MAINTENANCE.getTime() + (1000 * 60 * 60 * 24));
        
        return successful;
    }
    
    /**
     * Decrements the days remaining counters for active registrations.
     */
    private static void decrement() {
        
    }
    
    /**
     * Removes old rows such as registrations for courses that have been
     * refunded.
     */
    private static void removeOldRows() {
        
    }
    
    /**
     * Dumps the database to a file for archiving.
     */
    private static void dumpDatabase() {
        
    }
    
    /**
     * Dumps the maintenance time to file.
     */
    private static void dumpTime() {
        
    }
    
    /**
     * Adjusts the time of the next maintenance session. Needs to also dump the
     * time to file.
     */
    public static void adjustTime(long millis) {
        NEXT_MAINTENANCE = new Date(millis);
        dumpTime();
    }
    
    /**
     * Returns true if it is time for maintenance and dumps the time to file.
     * @return True if it is time for maintenance.
     */
    public static boolean isMaintenanceTime() {
        dumpTime();
        return NEXT_MAINTENANCE.before(new Date());
    }
    
    /**
     * Maintains the database if it is time.
     */
    public static void maintainIfTime() {
        if(isMaintenanceTime()) {
            maintain();
        }
    }
}
