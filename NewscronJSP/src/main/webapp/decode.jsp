<%-- 
    Document   : invite
    Created on : Sep 29, 2015, 4:03:49 PM
    Author     : Din
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="invite" class="ch.newscron.newscronjsp.DecodeDataUtils" scope="session"/> 
<jsp:useBean id="statistics" class="ch.newscron.newscronjsp.ReferralURLStatistics" scope="session"/> 
<%--<jsp:setProperty name="invite" property="*"/>--%> 


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>URL handling</h1>
        <% invite.setUrlString(request.getRequestURL().toString()); %>
        <p>Shorter URL: <br><a href=' <%= invite.getShorterUrl() %> '> <%= invite.getShorterUrl() %> </a> </p> 
        <p><u>Decoded data</u></p>
        <%= invite.showURLData() %>

        
        <%=  statistics.showStatisticsTable(invite.getUserId()) %>
    </body>
    <style>
        h1,h3 {
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
