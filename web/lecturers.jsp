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
        <link rel="stylesheet" type="text/css" href="style.css">
        <%= pageContent.getTitle() %>
    </head>
    <body>
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
    </body>
</html>
