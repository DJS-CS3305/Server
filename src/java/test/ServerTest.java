package test;

import net.*;

/**
 * Tests the server/administrator client connectivity.
 * 
 * @author Stephen Fahy
 */
public class ServerTest {
    public static void test() {
        Server server = Server.get();
        System.out.println("Server initialized.");
    }
}
