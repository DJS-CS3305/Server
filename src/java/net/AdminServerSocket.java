package net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import log.ErrorLogger;

/**
 * Class for a net socket on the server that communicates with administrator
 * clients.
 * 
 * @author Stephen Fahy
 */
public class AdminServerSocket implements Runnable {
    private static final int START_PORT = 40580;
    private static final int MAX_PORTS = 1000;
    private static int PORTS_USED = 0;
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private String username;
    private boolean run;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
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
            
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            
            run = true;
        }
        else {
            throw new Exception();
        }
    }
    
    /**
     * Checks for a message.
     * @return A message in the buffer, or null if none found.
     */
    private Message checkForMessage() throws SocketException, Exception {
        Message output = null;
        
        Object obj = in.readObject();
            
        if(obj instanceof Message) {
            output = (Message) obj;
        }
        
        return output;
    }
    
    /**
     * Closes the socket.
     */
    public void close() {
        try {
            serverSocket.close();
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
        check();
    }
    
    /**
     * Checks for incoming messages. Stops if there is a SocketException.
     */
    private void check() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());

            while(run) {
                Message msg = checkForMessage();

                if(msg != null) {
                    Server.get().handleMessage(msg, username);
                }
            }
        }
        catch(SocketException se) {
            //connection lost, disconnect.
            ErrorLogger.get().log(se.toString());
            se.printStackTrace();
            run = false;
            close();
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
    public int getPort() {
        return serverSocket.getLocalPort();
    }
}
