<%-- 
    Document   : courses
    Created on : Feb 20, 2013, 10:01:00 AM
    Author     : Stephen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="web.CoursePage" %>
<%@page import="java.util.Date" %>
<%@page import="java.net.URLDecoder" %>
<%-- The course code should be supplied by GET --%>
<% String courseCode = URLDecoder.decode(request.getParameter("course"), "UTF-8"); %>
<% CoursePage pageContent = new CoursePage(courseCode); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <%= pageContent.getTitle() %>
    </head>
    <body><div id="container">
        <div id="header">
            <img src="resources/logo.jpg" alt="UCC Crest" height="100" width="100">
            <h1>UCC Summer Courses</h1>
        </div>
        <div id="menu">
            <ul>
                <li><a href="index.jsp">Home</a></li>
                <li><a href="courseList.jsp">Courses</a></li>
                <li><a href="faqs.jsp">Help & FAQs</a></li>
                <li><a href="contact.jsp">Contact</a></li>
                <li><a href="about.jsp">About</a></li>

                <% 
                if(null != session.getAttribute("username")){
                %>

                <p id="hiUser">(Hi <a href="index.jsp">
                   <%out.print(session.getAttribute("username"));%>
                    </a>
                    <a href="doLogout.jsp"><u>sign out</u></a>)
                </p>
                <%
                }
                %>
            </ul>
        </div>
        <%= pageContent.getBody() %>
        <%
            if(pageContent.getStartDate().after(new Date())) {
                //print registering information
                //out.print("<p>Stuff for registering goes here</p>");
            }
        %>
    </div></body>
</html>
<% pageContent.addHit(); %>