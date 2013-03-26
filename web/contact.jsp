<%-- 
    Document   : contact
    Created on : 19-Mar-2013, 20:52:52
    Author     : David
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Contact</title>
    </head>
    <body>
        <div id="container">
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
                </ul> 
            </div>
            <div id="contactBox">
                <form action="contactAction.jsp" method="POST">
                    <p><label>Send a message to our System Admins</label></p>
                    <p><textarea id="feedback" name="feedback"></textarea></p>
                    <input type="submit" name="send" value="Send">
                </form>
            </div>
            <div id="contactInfo">
                <ul>
                    <li id="email">
                        <img src="resources/email.jpg" alt="email logo">
                        Email: summer_courses@ucc.ie
                    </li>
                    <li id="phone">
                        <img src="resources/phone.png" alt="image of phone">
                        Phone: 021-4123456
                    </li>
                    <li id="facebook">
                        <img src="resources/facebook.png" alt="facebook logo">
                        Facebook: facebook.com/ucc_sc
                    </li>
                    <li id="twitter">
                    <img src="resources/twitter.jpg" alt="twitter logo">
                    Twitter: twitter.com/ucc_sc
                    </li>
                </ul>
            </div>
        </div>
    </body>
</html>
