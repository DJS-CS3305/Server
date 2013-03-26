<%-- 
    Document   : doLogout
    Created on : 24-Mar-2013, 21:17:09
    Author     : David
--%>

<%@ page language="java" %>
<%
    session.invalidate();
    response.sendRedirect("index.jsp");
%>
