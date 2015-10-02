<%-- 
    Document   : invite
    Created on : Sep 29, 2015, 4:03:49 PM
    Author     : Din
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="invite" class="ch.newscron.newscronjsp.ReadInviteData" scope="session"/> 
<%--<jsp:setProperty name="invite" property="*"/>--%> 


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>URL handling</h1>
        Encoded Data: <%= invite.getDataFromURL(request.getRequestURL().toString()) %> <br>
        <%= invite.parseURL() %>
        
    </body>
</html>
