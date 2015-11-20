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
     <h1> ------ Newscron Registration / Sign up ------ </h1>
   
    <%= reg.checkURLValidity(request.getRequestURL().toString()) %>
    <br> <br>
        
    <form id="formRegister" method=POST action="/WelcomePage">
    First Name <input type=text name=firstName><br>
    Last Name <input type=text name=lastName><br>
    Email <input type=text name=emailAdd>
    <p><input type=submit name="registerUser">
    </form>
    </body>
    <style>
    body {
        font-size: 40px;
        margin-left: auto;
        margin-right: auto;
    }
    h1 {
        font-size: 60px;
        text-align: center;
    }
    form {
        width: 400px;
        margin: 0 auto;
        text-align: center;
    }
    p {
        width: 100%;
        margin: 0 auto;
        text-align: center;
    }
    input {
        font-size: 30px;
        margin-left: auto;
        margin-right: auto;
    }
</style>
</html>
