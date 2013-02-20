package log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 * Abstract class for logging system information. The class takes in logs and
 * exports them to the specified file.
 * 
 * @author Stephen Fahy
 */
public abstract class AbstractLogger extends Thread {
    private static final Logger logger = Logger.getAnonymousLogger();
    
    private StreamHandler handler;
    private Level severity;
    
    /**
     * Constructor. It is protected because there should only be a single
     * instance of a logger created at any given time. Each subclass must
     * implement a method to return their own individual single instance.
     * 
     * @param filepath The log's file path.
     * @param severity The severity of log entries from this logger.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    protected AbstractLogger(String filepath, Level severity) 
            throws FileNotFoundException, IOException {
        File file = new File(filepath);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file, true); //'true' makes it append
        handler = new StreamHandler(fos, new SimpleFormatter());
        logger.addHandler(handler);
        this.severity = severity;
    }
    
    /**
     * Logs the given message.
     * @param message 
     */
    public void log(String message) {
        logger.log(severity, message);
        handler.flush();
    }
}
