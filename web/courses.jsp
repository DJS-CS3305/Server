<%-- 
    Document   : courses
    Created on : Feb 20, 2013, 10:01:00 AM
    Author     : Stephen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="web.CoursePage" %>
<%@page import="java.util.Date" %>
<%-- The course code should be supplied by GET --%>
<% String courseCode = request.getParameter("course"); %>
<% CoursePage pageContent = new CoursePage(courseCode); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%= pageContent.getTitle() %>
    </head>
    <body>
        <%= pageContent.getBody() %>
        <%
            if(pageContent.getStartDate().after(new Date())) {
                //print registering information
                out.print("<p>Stuff for registering goes here</p>");
            }
        %>
    </body>
</html>
<% pageContent.addHit(); %>