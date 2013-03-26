<%-- 
    Document   : courseList
    Created on : 26-Mar-2013, 15:37:14
    Author     : df8
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>UCC Summer Courses</title>
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
                    <li><a href="faqs.html">Help & FAQs</a></li>
                    <li><a href="contact.jsp">Contact</a></li>
                    <li><a href="about.html">About</a></li>
                    
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
                <div id="courseList">
                    <%
                    Connection conn = null;
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    conn = DriverManager.getConnection("jdbc:derby://localhost:1527/summerCourses","david", "password");

                    ResultSet rsdoLogin = null;
                    PreparedStatement psdoLogin= null;

                    try{

                        String sqlOption="select * from COURSES";

                        psdoLogin=conn.prepareStatement(sqlOption);
                        rsdoLogin=psdoLogin.executeQuery();

                        while(rsdoLogin.next())  {
                          String courseCode=rsdoLogin.getString("CODE");
                          String courseName=rsdoLogin.getString("NAME");
                          %>
                          <ul>
                              <li><%=courseCode%> : <a href="course.jsp?courses=<%=courseCode%>"><%=courseName%></a></li>
                          </ul>
                          <%
                        }
                    }
                    catch(Exception e)
                    {

                        e.printStackTrace();
                    }


                    /// close object and connection
                    try{
                         if(psdoLogin!=null){
                             psdoLogin.close();
                         }
                         if(rsdoLogin!=null){
                             rsdoLogin.close();
                         }

                         if(conn!=null){
                          conn.close();
                         }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    %>
                </div>
        </div>
    </body>
</html>