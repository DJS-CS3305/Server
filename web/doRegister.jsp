<%-- 
    Document   : doRegister
    Created on : 22-Mar-2013, 19:09:13
    Author     : David
--%>

<%@ page language="java" import="java.sql.*" errorPage="" %>
<%@page  import="java.security.MessageDigest"%>
<%@page import="sql.Query" %>
<%
    String username=request.getParameter("userName");
    String password=request.getParameter("Pwd");
    String fName=request.getParameter("fName");
    String lName=request.getParameter("lName");
    String sex=request.getParameter("sex");
    String stAddress=request.getParameter("stAddr");
    String townAddress=request.getParameter("townAddr");
    String stateAddress=request.getParameter("stateAddr");
    String countryAddress=request.getParameter("countryAddr");
    String email=request.getParameter("email");
    String telNo=request.getParameter("telNo");
    String age=request.getParameter("age");
    
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(password.getBytes("UTF-8"));
    byte[] digest = md.digest();
    StringBuilder sb = new StringBuilder();
    for(byte b : digest) {
        sb.append(String.format("%02x", b));
    }
    String passhash = sb.toString();
    
    int gender = (sex.equals("m")) ? 1 : 0;

    String message="You have sucessfully registered. View your profile now, by logging in below!";
    
    try{
        String sqlOption="INSERT INTO USERS(username, password, firstname, surname, "
        + "streetAddr, townAddr, stateAddr, countryAddr, telNo, gender, age, email) "
        + "VALUES('"+username+"', '"+passhash+"', '"+fName+"', '"+lName+"', "
        + "'"+stAddress+"', '"+townAddress+"', '"+stateAddress+"', '"+countryAddress+"',"
        + " '"+telNo+"', "+gender+", "+age+", '"+email+"')";
        
        Query.query(sqlOption);
        
        response.sendRedirect("index.jsp?success="+message);
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>

