<%-- 
    Document   : doLogin
    Created on : 22-Mar-2013, 19:09:13
    Author     : David
--%>

<%@ page language="java" import="java.sql.*" errorPage="" %>
<%@page  import="java.security.MessageDigest"%>
<%@page import="sql.Query" %>
<%
    ResultSet rsdoLogin = null;
    
    String username=request.getParameter("sUserName");
    String password=request.getParameter("sPwd");

    String message="User login successfull";
    
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(password.getBytes("UTF-8"));
    byte[] digest = md.digest();
    StringBuilder sb = new StringBuilder();
    for(byte b : digest) {
        sb.append(String.format("%02x", b));
    }
    String passhash = sb.toString();
    
    try{

        String sqlOption="SELECT * from USERS where USERNAME='" + username + 
                "' and PASSWORD='"+passhash+"';";
        rsdoLogin = Query.query(sqlOption);

        if(rsdoLogin.next())  {
          String sUserName=rsdoLogin.getString("FIRSTNAME")+" "+rsdoLogin.getString("SURNAME");
          session.setAttribute("username",rsdoLogin.getString("USERNAME"));
          session.setAttribute("name",sUserName);
          response.sendRedirect("index.jsp");
        }
        else
        {
          message="Incorrect username/password. Please try again." ;
          response.sendRedirect("index.jsp?error="+message);
        }
    }
    catch(Exception e)
    {
        
        e.printStackTrace();
    }
%>
