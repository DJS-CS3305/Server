package main;

import log.AccessLogger;
import net.Server;
import sql.Maintainer;

/**
 * Main class for the server backend.
 * 
 * @author Stephen Fahy
 */
public class Main {
    public static void main(String[] args) {
        new Thread(new Maintainer());
        Server server = Server.get();
        AccessLogger.get().log("Server started.");
    }
}
