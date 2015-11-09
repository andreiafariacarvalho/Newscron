<%-- 
    Document   : NextPage
    Created on : Sep 25, 2015, 4:28:22 PM
    Author     : Din
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ch.newscron.encryption.Encryption"%>

<!DOCTYPE html>
<jsp:useBean id="user" class="ch.newscron.newscronjsp.UserData" scope="session"/> 
<jsp:useBean id="statistics" class="ch.newscron.newscronjsp.shortUrlStatistics" scope="session"/> 

<jsp:setProperty name="user" property="*"/> 

<html>
    <head></head>

    <body>
        
        <% if(request.getHeader("Referer").equals("http://localhost:8080/beansform.jsp")) {
                user.setURLtoEncode();
                statistics.saveURL(user.getCustID(), user.getFullURL());
        }%>
        
        <%= statistics.showStatisticsTable(request.getParameter("custID")) %>
    </body>
    <style>
        h1, h3 {
            font-size: 100px;
            text-align: center;
        }
        p {
            font-size: 40px;
            text-align: center;
        }
        table.center {
            font-size: 30px;
            margin-left: auto;
            margin-right: auto;
        }
        
        tr {
            text-align: center;
        }
    </style>

</html>



