<%-- 
    Document   : success
    Created on : 22-Mar-2013, 20:23:34
    Author     : David
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java"%>
<html>
<head>
<title>Successfully Login by JSP</title>
</head>

<body>
Successfully login by JSP<br />
Session Value<br />
<%
out.print("UserName :"+session.getAttribute("username")+"<br>");
out.print("First & Last Name :"+session.getAttribute("name"));
%>
</body>
</html>
