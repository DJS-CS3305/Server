package main;

import log.AccessLogger;
import net.Server;

/**
 * Main class for the server backend.
 * 
 * @author Stephen Fahy
 */
public class Main {
    public static void main(String[] args) {
        Server server = Server.get();
        AccessLogger.get().log("Server started.");
    }
}
