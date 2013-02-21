package log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Class for logging errors in the system.
 * 
 * @author Stephen Fahy
 */
public class ErrorLogger extends AbstractLogger {
    private static final String FILEPATH = "error.log";
    private static final Level SEVERITY = Level.SEVERE;
    private static ErrorLogger INSTANCE;
    
    /**
     * Constructor.
     * @throws FileNotFoundException 
     */
    private ErrorLogger() throws FileNotFoundException, IOException {
        super(FILEPATH, SEVERITY);
    }
    
    /**
     * @return The instance of ErrorLogger.
     */
    public static ErrorLogger get() {
        if(INSTANCE == null) {
            try {
                INSTANCE = new ErrorLogger();
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
