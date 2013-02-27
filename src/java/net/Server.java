package net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.HashMap;
import log.AccessLogger;
import log.ErrorLogger;
import sql.Query;

/**
 * Contains the methods for handling requests from connected administrator
 * clients. Server will be off by default.
 * 
 * @author Stephen Fahy
 */
public class Server extends Thread {
    private static Server INSTANCE = new Server();
    public static final int AUTH_PORT = 579;
    
    private ServerSocket authPort;
    private HashMap<String, AdminServerSocket> sockets;
    //integers represent checks to sockets without any activity
    private HashMap<String, Integer> activity;
    private Authenticator auth;
    
    /**
     * Constructor.
     */
    private Server() {
        System.out.println("Starting server.");
        try {
            authPort = new ServerSocket(AUTH_PORT, 0, InetAddress.getLocalHost());
            System.out.println(authPort.getInetAddress().getHostAddress());
            sockets = new HashMap<String, AdminServerSocket>();
            activity = new HashMap<String, Integer>();
            auth = new Authenticator();
        }
        catch(Exception e) {
            ErrorLogger.get().log(e.toString());
            e.printStackTrace();
            System.exit(1);
        }
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
     * Handles a message.
     * 
     * @param msg A message from a user.
     * @param username That user's username.
     */
    public void handleMessage(Message msg, String username) {
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
    
    /**
    * Checks the authorization port for activity in a loop.
    * 
    * @author Stephen Fahy
    */
    private class Authenticator extends Thread {
        /**
         * Constructor.
         */
        private Authenticator() {
            loop();
        }
        
        /**
         * Loops as long as the server runs, checking for messages/connections.
         * Must be called when the server is turned on.
         */
        private void loop() {
            while(true) {
                try {
                    handleAuthRequest(authPort.accept());
                }
                catch(Exception e) {
                    ErrorLogger.get().log(e.toString());
                    e.printStackTrace();
                }
            }
        }
       
       /**
        * Handles a request from a client wishing to connect to the server.
        * All other messages on this port will be ignored.
        */
       private void handleAuthRequest(Socket client) throws Exception {
           ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
           ObjectInputStream in = new ObjectInputStream(client.getInputStream());
           
           Message msg = (Message) in.readObject();
           
           if(msg instanceof AuthMessage) {
               AuthMessage authMsg = (AuthMessage) msg;
               String username = authMsg.getContent().get(AuthMessage.USERNAME).toString();
               String passhash = authMsg.getContent().get(AuthMessage.PASSWORD).toString();
               
               boolean valid = authenticate(username, passhash);

               if(valid) {
                   ConnectionMessage con = new ConnectionMessage(msg.getId(), 
                           AdminServerSocket.getNextFreePort());
                   con.send(out);
                   out.flush();
                   
                   AdminServerSocket serverSocket = new AdminServerSocket(username);
                   addSocket(serverSocket);
                   
                   AccessLogger.get().log(username + " connected.");
               }
               else {
                   //invalid login details
                   AckMessage ack = new AckMessage(msg.getId(), false);
                   ack.send(out);
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
           System.out.println("Username: " + username);
           System.out.println("Passhash: " + passhash);

           ResultSet results = Query.query("SELECT * FROM Users WHERE isAdmin = TRUE " +
                   "AND username = '" + username + "' " +
                   "AND password = '" + passhash + "';");

           try {
               results.last();
               int resultSize = results.getRow();
               System.out.println("Result Size: " + resultSize);
               if(resultSize == 1) {
                   output = true;
               }
           }
           catch(Exception e) {
               ErrorLogger.get().log(e.toString());
               e.printStackTrace();
           }

           return output;
       }
    }
}
