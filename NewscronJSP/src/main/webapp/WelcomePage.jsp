<%-- 
    Document   : WelcomePage
    Created on : Nov 9, 2015, 3:28:00 PM
    Author     : Din
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="registration" class="ch.newscron.newscronjsp.RegistrationUtils" scope="session"/> 
<jsp:setProperty name="registration" property="*"/> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>--- Newscron ---</h1>
        <% registration.insertUser(request.getHeader("Referer"));%>
    </body>
</html>
