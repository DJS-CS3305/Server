package sql;

/**
 * Class for doing the automatic maintenance on the database.
 * 
 * @author Stephen Fahy
 */
public class Maintenance {
    /**
     * Maintains the database by removing unneeded rows, decrementing days
     * remaining counters, etc. Also readjusts the time of the next
     * maintenance session.
     * 
     * @return True if the maintenance was successful.
     */
    public static boolean maintain() {
        boolean successful = true;
        
        dump();
        decrement();
        removeOldRows();
        adjustTime();
        
        return successful;
    }
    
    /**
     * Decrements the days remaining counters.
     */
    private static void decrement() {
        
    }
    
    /**
     * Removes old rows such as registrations for courses that have completed,
     * and messages that have been replied to.
     */
    private static void removeOldRows() {
        
    }
    
    /**
     * Dumps the database to a file and archives it.
     */
    private static void dump() {
        
    }
    
    /**
     * Adjusts the time of the next maintenance session.
     */
    private static void adjustTime() {
        
    }
}
