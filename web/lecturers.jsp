<%-- 
    Document   : lecturers
    Created on : 11-Mar-2013, 11:27:19
    Author     : saf3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="web.LecturerPage" %>
<%@page import="java.net.URLDecoder" %>
<%-- The lecturer name should be supplied by GET --%>
<% String name = URLDecoder.decode(request.getParameter("name"), "UTF-8"); %>
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
