package net;

import java.net.Socket;

/**
 * Class for a net socket from an administrator client.
 * 
 * @author Stephen Fahy
 */
public class AdminClientSocket {
    private Socket socket;
    
    public AdminClientSocket(int port, String ip) {
        try {
            socket = new Socket();
        }
        catch(Exception e) {
            
        }
    }
}
