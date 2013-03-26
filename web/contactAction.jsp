<%-- 
    Document   : contactAction
    Created on : 26-Mar-2013, 21:10:11
    Author     : David
--%>

<%@page import="sql.Query"%>
<%@ page language="java" import="java.sql.*" errorPage="" %>
<%
    ResultSet rsdoLogin = null;
    
    String message=request.getParameter("feedback");
    String username=(String)session.getAttribute("username");
    String successMessage= "You message has been sent. Please wait for a reply";
    try{
    String sqlOption="INSERT INTO MESSAGES(username, content, replyId) "
        + "VALUES('"+username+"', '"+message+"', 0)";
    Query.query(sqlOption);
    
    response.sendRedirect("contact.jsp?success="+successMessage);
    }
    catch(Exception e)
    {
      
        e.printStackTrace();
    }

%>
