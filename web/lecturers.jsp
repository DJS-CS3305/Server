<%-- 
    Document   : lecturers
    Created on : 11-Mar-2013, 11:27:19
    Author     : saf3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="web.LecturerPage" %>
<%-- The lecturer name should be supplied by GET --%>
<% String name = request.getParameter("name"); %>
<% LecturerPage pageContent = new LecturerPage(name); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%= pageContent.getTitle() %>
    </head>
    <body>
        <%= pageContent.getBody() %>
    </body>
</html>
