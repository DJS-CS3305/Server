package log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Class for logging the connections and actions of administrator clients
 * that access the system.
 * 
 * @author Stephen Fahy
 */
public class AccessLogger extends AbstractLogger {
    private static final String FILEPATH = "access.log";
    private static final Level SEVERITY = Level.INFO;
    private static AccessLogger INSTANCE;
    
    /**
     * Constructor.
     * @throws FileNotFoundException 
     */
    private AccessLogger() throws FileNotFoundException, IOException {
        super(FILEPATH, SEVERITY);
    }
    
    /**
     * @return The instance of AccessLogger.
     */
    public static AccessLogger get() {
        if(INSTANCE == null) {
            try {
                INSTANCE = new AccessLogger();
                return INSTANCE;
            }
            catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        
        return INSTANCE;
    }
}