package net;

import java.util.HashMap;
import java.util.Iterator;
import log.AccessLogger;
import sql.Query;

/**
 * Contains the methods for handling requests from connected administrator
 * clients.
 * 
 * @author Stephen Fahy
 */
public class Server {
    private static Server INSTANCE = new Server();
    //amount of checks on a socket without activity for it to be closed
    private static final int MAX_IDLE_TIME = 50000;
    
    private Authenticator auth;
    private HashMap<String, AdminServerSocket> sockets;
    //integers represent checks to sockets without any activity
    private HashMap<String, Integer> activity;
    
    /**
     * Constructor.
     */
    private Server() {
        auth = new Authenticator();
        sockets = new HashMap<String, AdminServerSocket>();
        activity = new HashMap<String, Integer>();
    }
    
    /**
     * Adds a socket to it's list.
     * 
     * @param socket 
     */
    public void addSocket(AdminServerSocket socket) {
        sockets.put(socket.getUsername(), socket);
        activity.put(socket.getUsername(), 0);
    }
    
    /**
     * Checks the authenticator for any incoming connection messages.
     */
    public void checkAuth() {
        Message msg = auth.checkForRequest();
        
        if(msg != null) {
            auth.handleRequest(msg);
        }
    }
    
    /**
     * Checks all client sockets for messages.
     */
    public void checkClients() {
        Iterator<String> iter = sockets.keySet().iterator();
        
        while(iter.hasNext()) {
            String username = iter.next();
            AdminServerSocket socket = sockets.get(username);
            Message msg = socket.checkForMessage();
            
            if(msg != null) {
                handleMessage(msg, username);
                activity.put(username, 0);
            }
            else {
                activity.put(username, activity.get(username) + 1);
                
                //close connection & remove socket if idle too long.
                if(activity.get(username) == MAX_IDLE_TIME) {
                    socket.close();
                    sockets.remove(username);
                    activity.remove(username);
                    
                    AccessLogger.get().log(username + " disconnected due to timeout.");
                }
            }
        }
    }
    
    /**
     * Handles a message.
     * 
     * @param msg A message from a user.
     * @param username That user's username.
     */
    private void handleMessage(Message msg, String username) {
        if(msg instanceof QueryMessage) {
            //SQL query handling
            QueryMessage qmsg = (QueryMessage) msg;
            String query = (String) qmsg.getContent().get(QueryMessage.QUERY);
            ResultMessage reply = new ResultMessage(qmsg.getId(), Query.query(query));
            reply.send(sockets.get(username).getOut());
            
            AccessLogger.get().log(username + " sent SQL query " + 
                    query + ".");
        }
        else if(msg instanceof AckMessage) {
            //receiving acknowledgements
            boolean bool = (Boolean)((AckMessage) msg).getContent().get(AckMessage.VALUE);
            
            if(bool) {
                AccessLogger.get().log(username + " sent acknowledgement.");
            }
            else {
                AccessLogger.get().log(username + " sent unacknowledgement.");
            }
        }
    }
    
    //getters
    public static Server get() {
        return INSTANCE;
    }
}
