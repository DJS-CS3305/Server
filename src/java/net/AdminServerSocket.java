package net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import log.ErrorLogger;

/**
 * Class for a net socket on the server that communicates with administrator
 * clients.
 * 
 * @author Stephen Fahy
 */
public class AdminServerSocket implements Runnable {
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
            int port = getNextFreePort();
            PORTS_USED++;
            
            socket = new ServerSocket(port);
        }
        else {
            throw new Exception();
        }
    }
    
    /**
     * Checks for a message.
     * @return A message in the buffer, or null if none found.
     */
    private Message checkForMessage() {
        Message output = null;
        
        try {
            Object obj = in.readObject();
            
            if(obj instanceof Message) {
                output = (Message) obj;
            }
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
        }
        
        return output;
    }
    
    /**
     * Closes the socket.
     */
    public void close() {
        try {
            socket.close();
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
        }
    }
    
    /**
     * Runs forever, checking for messages.
     */
    @Override
    public void run() {
        checkForMessages();
    }
    
    /**
     * Checks for incoming messages after initializing out and in streams.
     */
    private void checkForMessages() {
        try {
            System.out.println("Server socket starting run...");
            Socket s = socket.accept();
            System.out.println("Server socket initializing out...");
            out = new ObjectOutputStream(s.getOutputStream());
            out.flush();
            System.out.println("Server socket initializing in...");
            in = new ObjectInputStream(s.getInputStream());

            while(true) {
                Message msg = checkForMessage();

                if(msg != null) {
                    System.out.println("Message found in server socket.");
                    System.out.println(msg);
                    System.out.println(username);
                    Server.get().handleMessage(msg, username);
                    System.out.println("Message handled.");
                }
            }
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
        }
    }
    
    /**
     * @return The next free port number.
     */
    public static int getNextFreePort() {
        int port = START_PORT + PORTS_USED;
        return port;
    }
    
    //getters
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
