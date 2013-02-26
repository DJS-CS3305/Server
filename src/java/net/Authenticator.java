package net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import log.AccessLogger;
import log.ErrorLogger;
import sql.Query;

/**
 * Authenticates connection requests and returns valid connections to the
 * Server.
 * 
 * Default port is 579.
 * 
 * @author Stephen Fahy
 */
public class Authenticator {
    public static final int AUTH_PORT = 579;
    
    private ServerSocket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    /**
     * Constructor.
     */
    public Authenticator() {
        try {
            socket = new ServerSocket(AUTH_PORT);
            Socket s = socket.accept();
            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Handles a request from a client wishing to connect to the server.
     * All other messages on this port will be ignored.
     */
    public void handleRequest(Message msg) {
        if(msg instanceof AuthMessage) {
            AuthMessage authMsg = (AuthMessage) msg;
            String username = (String)authMsg.getContent().get(AuthMessage.USERNAME);
            String passhash = (String)authMsg.getContent().get(AuthMessage.PASSWORD);
            
            boolean valid = authenticate(username, passhash);
            
            AckMessage ack = new AckMessage(msg.getId(), valid);
            ack.send(out);
            
            if(valid) {
                try {
                    AdminServerSocket serverSocket = new AdminServerSocket(username);
                    Server.get().addSocket(serverSocket);
                    ConnectionMessage con = 
                            new ConnectionMessage(msg.getId(), serverSocket.getPort());
                    con.send(out);
                    
                    AccessLogger.get().log(username + " connected.");
                }
                catch(Exception e) {
                    ErrorLogger.get().log(e.toString());
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Authenticates a user wishing to connect to the system.
     * 
     * @param username The user's username.
     * @param passhash The hash of the user's password.
     * @return True if the given username/password hash combo are on the DB as admin.
     */
    private boolean authenticate(String username, String passhash) {
        boolean output = false;
        
        ResultSet results = Query.query("SELECT * FROM Users WHERE admin = TRUE " +
                "AND username = '" + username + "' " +
                "AND password = '" + passhash + "';");
        
        try {
            if(results.getFetchSize() == 1) {
                output = true;
            }
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
        }
        
        return output;
    }
    
    /**
     * Checks the input for a message.
     * 
     * @return The next message in.
     */
    public Message checkForRequest() {
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
}
