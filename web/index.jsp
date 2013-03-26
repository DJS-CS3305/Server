<%-- 
    Document   : index
    Created on : 05-Mar-2013, 00:30:04
    Author     : David
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*"%>
<%
String error=request.getParameter("error");
if(error==null || error=="null"){
 error="";
}
String success=request.getParameter("success");
if(success==null || success=="null"){
 success="";
}
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>UCC Summer Courses</title>
    </head>
    <script>
    function trim(s) 
    {
        return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
    }

    function validate()
    {
        if(trim(document.frmLogin.sUserName.value)=="")
        {
          alert("The usename filed is empty!");
          document.frmLogin.sUserName.focus();
          return false;
        }
        else if(trim(document.frmLogin.sPwd.value)=="")
        {
          alert("The password filed is empty!");
          document.frmLogin.sPwd.focus();
          return false;
        }
    }
    </script>
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
                <%
                    if(null != session.getAttribute("username")){
                        Connection conn = null;
                        Class.forName("com.mysql.jdbc.Driver").newInstance();
                        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/summerCourses","david", "password");

                        ResultSet rsdoLogin = null;
                        PreparedStatement psdoLogin= null;
                        
                        ResultSet rsdoLogin2 = null;
                        PreparedStatement psdoLogin2 = null;
                        
                        ResultSet rsdoLogin3 = null;
                        PreparedStatement psdoLogin3= null;
                        

                        try{
                            String sqlOption="select * from REGISTRATIONS where"
                                             +" USERNAME=?";
                            
                            String sqlOption2="select * from COURSES where"
                                             +" CODE=?";
                            
                            String sqlOption3="select * from USERS where"
                                             +" USERNAME=?";
                            
                            psdoLogin=conn.prepareStatement(sqlOption); 
                            psdoLogin2=conn.prepareStatement(sqlOption2);
                            psdoLogin3=conn.prepareStatement(sqlOption3);
                            
                            String username = (String)session.getAttribute("username");
                            psdoLogin.setString(1, username);
                            psdoLogin3.setString(1, username);
                            
                            rsdoLogin3=psdoLogin3.executeQuery();
                            %>
                            <div id="userInfo">
                                <%
                                while(rsdoLogin3.next()) {
                                    %><ul><li><%
                                    out.println("Name: " 
                                        + rsdoLogin3.getString("FIRSTNAME")
                                        + " " + rsdoLogin3.getString("SURNAME"));
                                    %></li><%
                                    %><li><%
                                    out.println("Age: " 
                                        + rsdoLogin3.getString("AGE"));
                                    %></li><%
                                    %><li><%
                                    out.println("Address: " 
                                        + rsdoLogin3.getString("STREETADDR")
                                        + ", " + rsdoLogin3.getString("TOWNADDR")
                                        + ", " + rsdoLogin3.getString("STATEADDR")
                                        + ", " + rsdoLogin3.getString("COUNTRYADDR"));
                                    %></li><%
                                    %><li><%
                                    out.println("E-mail: " 
                                        + rsdoLogin3.getString("EMAIL"));
                                    %></li><%
                                    %><li><%
                                    out.println("Telephone Number: " 
                                        + rsdoLogin3.getString("TELNO"));
                                    %></li></ul><%    
                                }
                                %>
                            </div>
                            
                            <div id="coursesBox">
                                <h3>Your Course Info</h3>
                            <p>
                            <%
                            rsdoLogin=psdoLogin.executeQuery();
                            while(rsdoLogin.next())  {
  
                                out.println("Code: " +
                                        rsdoLogin.getString("COURSECODE"));
                            String courseCode = rsdoLogin.getString("COURSECODE");
                            psdoLogin2.setString(1, courseCode);    
                            rsdoLogin2=psdoLogin2.executeQuery();
                                while(rsdoLogin2.next()) {
                                    
                                    %><ul><li><%
                                    out.println("Name: " +
                                        rsdoLogin2.getString("NAME"));
                                    %></li><%
                                    %><li><%
                                    out.println("Start Date: " +
                                        rsdoLogin2.getString("STARTDATE"));
                                    %></li><%
                                    %><li><%
                                    out.println("Location: " +
                                        rsdoLogin2.getString("LOCATION"));
                                    %></li></ul><%
                                    
                                    %></br><%
                                    %></br><%
                                    out.println("&nbsp&nbsp-------------------"
                                            + "----------------&nbsp&nbsp ");
                                    %></br><%
                                    %></br><%
                                }
                                
                            }
                            %>
                            </p>
                            </div>
                            <%
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                   }
                %>
                
            <% 
                    if(null == session.getAttribute("username")){
            %> 
            <img id="loginPicture" src="resources/studentCenter.jpg" alt="UCC Student Center">
            <div id="login">
                
                <form name="frmLogin" onSubmit="return validate();" action="doLogin.jsp" method="POST">
                    <div><%=success%></div>
                    <p><label for="uName">Username: &nbsp;<lable><input type="text" name="sUserName"/>
                    <label for="password">Password: &nbsp;<lable><input type="password" name="sPwd"/>
                    <input type="submit" name="sSubmit" value="Submit"/></p>
                    <p><a href="register.jsp">Register?</a></p>
                    <div><font size="3" color="red"><%=error%></font></div>
                </form>
                
            </div>
            <%
                    }
            %>
        </div>
    </body>
</html>
