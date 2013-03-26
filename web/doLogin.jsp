<%-- 
    Document   : doLogin
    Created on : 22-Mar-2013, 19:09:13
    Author     : David
--%>

<%@ page language="java" import="java.sql.*" errorPage="" %>
<%

    Connection conn = null;
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    conn = DriverManager.getConnection("jdbc:derby://localhost:1527/summerCourses","david", "password");
    
    ResultSet rsdoLogin = null;
    PreparedStatement psdoLogin= null;
    
    String username=request.getParameter("sUserName");
    String password=request.getParameter("sPwd");

    String message="User login successfull";
    
    try{
    
    String sqlOption="select * from USERS where"
                     +" USERNAME=? and PASSWORD=?";

    psdoLogin=conn.prepareStatement(sqlOption);
    
    psdoLogin.setString(1, username);
    psdoLogin.setString(2, password);
    rsdoLogin=psdoLogin.executeQuery();
    
    if(rsdoLogin.next())  {
      String sUserName=rsdoLogin.getString("FIRSTNAME")+" "+rsdoLogin.getString("SURNAME");
      session.setAttribute("username",rsdoLogin.getString("USERNAME"));
      session.setAttribute("name",sUserName);
      response.sendRedirect("index.jsp");
    }
    else
    {

      message="Incorrect username/password. Please try again." ;
      response.sendRedirect("index.jsp?error="+message);
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
