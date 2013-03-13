package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import log.ErrorLogger;
import sql.Maintenance;

/**
 * Tests the automatic maintenance of the database.
 * 
 * @author Stephen Fahy
 */
public class MaintenanceTest {
    public static void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        boolean maintained = false;
        
        try {
            Date maintTime = sdf.parse("13/03/2013 10:41");
            Maintenance.adjustTime(maintTime.getTime());
            System.out.println(maintTime);
            
            while(!maintained) {
                if(Maintenance.isMaintenanceTime()) {
                    maintained = Maintenance.maintain();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            ErrorLogger.get().log(e.toString());
        }
    }
}
