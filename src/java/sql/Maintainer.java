package sql;

import log.AccessLogger;

/**
 * Thread for checking for if the database should be maintained.
 * 
 * @author saf3
 */
public class Maintainer implements Runnable {
    /**
     * Runs forever, checking if it is the maintenance tie and maintaining if
     * it is.
     */
    @Override
    public void run() {
        while(true) {
            try {
                if(Maintenance.isMaintenanceTime()) {
                    if(Maintenance.maintain()) {
                        AccessLogger.get().log("Database maintained.");
                    }
                    else {
                        AccessLogger.get().log("Database maintainance failed.");
                    }
                }
            }
            catch(Exception e) {
            }
        }
    }
}
