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
<!--    <body>
        You entered<br>
        CustID: <%= user.getCustID() %><br>
        Rew1: <%= user.getRew1() %><br>
        Rew2: <%= user.getRew2() %><br>
        Val: <%= user.getVal() %><br> <br>

        <% user.setURLtoEncode(); %>
        Data: <%= user.createJSON(user.getCustID(), user.getRew1(), user.getRew2(), user.getVal()).toString() %> <br>
        <% statistics.saveURL(user.getCustID(), user.getFullURL()); %>
        
        <p>Invitation URL: <a href="<%=statistics.getShortURL()%>"> <%=statistics.getShortURL()%></a> </p>
    </body>-->
        
        
    <body>
        <% user.isLastPageBeansform(request.getHeader("Referer")); %>
        <% statistics.isLastPageBeansform(request.getHeader("Referer")); %>
        
        
        <% user.setURLtoEncode(); %>
        <% statistics.saveURL(user.getCustID(), user.getFullURL()); %>
        
        
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



