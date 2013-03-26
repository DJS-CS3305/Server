<%-- 
    Document   : doRegister
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
    
    String username=request.getParameter("userName");
    String password=request.getParameter("Pwd");
    String fName=request.getParameter("fName");
    String lName=request.getParameter("lName");
    String sex=request.getParameter("sex");
    String stAddress=request.getParameter("stAddr");
    String townAddress=request.getParameter("townAddr");
    String stateAddress=request.getParameter("stateAddr");
    String countryAddress=request.getParameter("countryAddr");
    String email=request.getParameter("email");
    String telNo=request.getParameter("telNo");
    String age=request.getParameter("age");
    
    

    String message="You have sucessfully registered. View your profile now, by logging in below!";
    
    try{
    String sqlOption="INSERT INTO USERS(username, password, firstname, surname, streetAddr, townAddr, stateAddr, countryAddr, telNo, gender, age, email) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    psdoLogin=conn.prepareStatement(sqlOption);
    
    psdoLogin.setString(1, username);
    psdoLogin.setString(2, password);
    psdoLogin.setString(3, fName);
    psdoLogin.setString(4, lName);
    psdoLogin.setString(5, stAddress);
    psdoLogin.setString(6, townAddress);
    psdoLogin.setString(7, stateAddress);
    psdoLogin.setString(8, countryAddress);
    psdoLogin.setString(9, telNo);
    psdoLogin.setString(10, sex);
    psdoLogin.setString(11, age);
    psdoLogin.setString(12, email);

    psdoLogin.executeUpdate();
    response.sendRedirect("index.jsp?success="+message);
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

