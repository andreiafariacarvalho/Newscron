<%-- 
    Document   : registration
    Created on : Nov 5, 2015, 3:22:50 PM
    Author     : Din
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="reg" class="ch.newscron.newscronjsp.RegistrationUtils" scope="session"/> 

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP page</title>
    </head>
    <body>
    
    <%= reg.checkURLValidity(request.getRequestURL().toString()) %>
        
    <h1> Registration / Sign up </h1>
    <form id="formRegister" method=POST action="/WelcomePage">
    First Name <input type=text name=firstName><br>
    Last Name <input type=text name=lastName><br>
    Email <input type=text name=emailAdd>
    <p><input type=submit name="registerUser">
    </form>
    </body>
</html>
