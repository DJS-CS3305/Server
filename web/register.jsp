<%-- 
    Document   : register.jsp
    Created on : 05-Mar-2013, 00:30:04
    Author     : David
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*"%>
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
        if(trim(document.frmRegister.userName.value)=="")
        {
          alert("Username empty");
          document.frmRegister.userName.focus();
          return false;
        }
        else if(trim(document.frmRegister.Pwd.value)=="")
        {
          alert("Password empty");
          document.frmRegister.Pwd.focus();
          return false;
        }
        else if(trim(document.frmRegister.fName.value)=="")
        {
          alert("First name empty");
          document.frmRegister.fName.focus();
          return false;
        }
        else if(trim(document.frmRegister.lName.value)=="")
        {
          alert("Last name empty");
          document.frmRegister.lName.focus();
          return false;
        }
        else if(trim(document.frmRegister.age.value)=="")
        {
          alert("Age empty");
          document.frmRegister.age.focus();
          return false;
        }
        else if(trim(document.frmRegister.sex.value)=="")
        {
          alert("Sex empty");
          document.frmRegister.sex.focus();
          return false;
        }
        else if(trim(document.frmRegister.stAddr.value)=="")
        {
          alert("Street Address empty");
          document.frmRegister.stAddr.focus();
          return false;
        }
        else if(trim(document.frmRegister.townAddr.value)=="")
        {
          alert("Town Address empty");
          document.frmRegister.townAddr.focus();
          return false;
        }
        else if(trim(document.frmRegister.stateAddr.value)=="")
        {
          alert("State Address empty");
          document.frmRegister.stateAddr.focus();
          return false;
        }
        else if(trim(document.frmRegister.countryAddr.value)=="")
        {
          alert("Country Address empty");
          document.frmRegister.countryAddr.focus();
          return false;
        }
        else if(trim(document.frmRegister.telNo.value)=="")
        {
          alert("Telephone Number empty");
          document.frmRegister.telNo.focus();
          return false;
        }
        else if(trim(document.frmRegister.email.value)=="")
        {
          alert("Email empty");
          document.frmRegister.email.focus();
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
                </ul>
            </div>
            <div id="register">
                    <form name="frmRegister" onSubmit="return validate();" action="doRegister.jsp" method="POST">
                        <p><label for="name">Username: &nbsp;</lable><input type="text" name="userName"/>
                       
                        <label for="password">Password: &nbsp;</lable><input type="password" name="Pwd"/>
                       
                        <label for="firstName">First Name: &nbsp;</lable><input type="text" name="fName"/>
                       
                        <label for="lastName">Last Name: &nbsp;</lable><input type="text" name="lName"/>
                        
                        <label for="lastName">Age: &nbsp;</lable><SELECT NAME="age" SIZE="1">
                        <OPTION SELECTED>
                            <% for(int x=18; x<=100; x++) {%>
                                <OPTION><%out.print(x);%>
                            <% } %>
                        </SELECT>
                        </br>
                        <label for="sex">Sex: &nbsp;</lable>
                       
                        Male<input type="radio" name="sex" value="m">
                        Female<input type="radio" name="sex" value="f">
                        
                        <label for="stAddr">Street Address: &nbsp;</lable><input type="text" name="stAddr"/>
                      
                        <label for="stAddr">Town Address: &nbsp;</lable><input type="text" name="townAddr"/>
                       
                        <label for="stAddr">State Address: &nbsp;</lable><input type="text" name="stateAddr"/>
                       
                        <label for="stAddr">Country Address: &nbsp;</lable><input type="text" name="countryAddr"/>
                      
                        <label for="email">Email: &nbsp;</lable><input type="text" name="email"/>
                        
                        <label for="telNo">Telephone Number: &nbsp;</lable><input type="text" name="telNo"/>
                        
                        <input type="submit" name="sSubmit" value="Submit"/></p>                  
                    </form>  
            </div>   
        </div>
    </body>
</html>