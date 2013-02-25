package net;

import java.util.HashMap;
import java.net.ServerSocket;
import java.net.Socket;
import log.ErrorLogger;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class for a net socket on the server that communicates with administrator
 * clients.
 * 
 * @author Stephen Fahy
 */
public class AdminServerSocket {
    private static final HashMap<String, AdminServerSocket> SOCKETS = 
            new HashMap<String, AdminServerSocket>();
    private static final int START_PORT = 580;
    private static final int MAX_PORTS = 1000;
    private static int PORTS_USED = 0;
    
    private ServerSocket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String username;
    
    /**
     * Constructor. Throws an exception if all available ports in the range
     * are used.
     * 
     * @param username The username of the connected user.
     * @throws Exception 
     */
    public AdminServerSocket(String username) throws Exception {
        if(PORTS_USED < MAX_PORTS) {
            this.username = username;
            int port = START_PORT + PORTS_USED;
            PORTS_USED++;
            
            socket = new ServerSocket(port);
            Socket s = socket.accept();
            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());
            
            SOCKETS.put(username, this);
        }
        else {
            throw new Exception();
        }
    }
    
    /**
     * @param username A username.
     * @return The socket associated with the user; null if none found.
     */
    public static AdminServerSocket getSocket(String username) {
        return SOCKETS.get(username);
    }
    public ObjectInputStream getIn() {
        return in;
    }
    public ObjectOutputStream getOut() {
        return out;
    }
    public String getUsername() {
        return username;
    }
    public ServerSocket getSocket() {
        return socket;
    }
    public int getPort() {
        return socket.getLocalPort();
    }
}
