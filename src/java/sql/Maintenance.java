package sql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import log.ErrorLogger;

/**
 * Class for doing the automatic maintenance on the database.
 * 
 * @author Stephen Fahy
 */
public class Maintenance {
    public static final String TIME_FILEPATH = "./";
    public static final String TIME_FILENAME = "maintenance.time";
    public static final String DUMP_FILEPATH = "./sqlDumps/";
    
    private static Date NEXT_MAINTENANCE = new Date(new Date().getTime() + 
            86400000);
    
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
            dumpDatabase(); //must dump first to preserve state for archiving
            decrementDaysRemaining();
            removeOldRows();
            startRegistrations();
            //set maintenance time to the next day.
            adjustTime(NEXT_MAINTENANCE.getTime() + (1000 * 60 * 60 * 24));
        }
        catch(Exception e) {
            e.printStackTrace();
            ErrorLogger.get().log(e.toString());
            ErrorLogger.get().log("Database maintenance has failed.");
            successful = false;
        }
        
        return successful;
    }
    
    /**
     * Decrements the days remaining counters for active registrations.
     */
    private static void decrementDaysRemaining() throws SQLException {
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
     * Activates the registrations for courses that start today if the 
     * maintenance is performed before midday, or tomorrow if the maintenance
     * is done after midday.
     */
    private static void startRegistrations() throws SQLException {
        String activationDay;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh");
        
        if(Integer.parseInt(hourFormat.format(NEXT_MAINTENANCE)) > 11) {
            //after midday, use tomorrow
            Date tomorrow = new Date(NEXT_MAINTENANCE.getTime() + (1000 * 60 * 60 * 24));
            activationDay = dateFormat.format(tomorrow);
        }
        else {
            //before midday, use today
            activationDay = dateFormat.format(NEXT_MAINTENANCE);
        }
        
        ResultSet updateCourses = Query.query("SELECT code FROM Courses WHERE " + 
                "startDate = '" + activationDay + "';");
        
        while(updateCourses.next()) {
            String code = updateCourses.getString(1);
            Query.query("UPDATE Registrations SET hasStarted = TRUE WHERE " +
                    "courseCode = '" + code + "';");
        }
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
        String canonPath = new File(TIME_FILEPATH).getCanonicalPath();
        File file = new File(canonPath + "/" + TIME_FILENAME);
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
     * Returns true if it is time for maintenance.
     * @return True if it is time for maintenance.
     */
    public static boolean isMaintenanceTime() throws IOException {
        return NEXT_MAINTENANCE.before(new Date());
    }
    
    /**
     * @return Next maintenance time in milliseconds.
     */
    public static long getTime() {
        return NEXT_MAINTENANCE.getTime();
    }
}
