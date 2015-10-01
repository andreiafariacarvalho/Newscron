<%-- 
    Document   : NextPage
    Created on : Sep 25, 2015, 4:28:22 PM
    Author     : Din
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ch.newscron.encryption.Encryption"%>

<!DOCTYPE html>
<jsp:useBean id="user" class="ch.newscron.newscronjsp.UserData" scope="session"/> 
<jsp:setProperty name="user" property="*"/> 



You entered<br>
CustID: <%= user.getCustID() %><br>
Rew1: <%= user.getRew1() %><br>
Rew2: <%= user.getRew2() %><br>
Val: <%= user.getVal() %><br> <br>

<% user.setURLtoEncode(); %>
Data: <%= user.createJSON(user.getCustID(), user.getRew1(), user.getRew2(), user.getVal()).toString() %> <br>
Encoded Data: <a href="<%=user.getFullURL()%>"> <%=user.getFullURL()%></a> <br>
Encoded Data size: <%= user.getNumberBytes()%> <br>
<%--Decoded Data: <%= "http://localhost/invite/" + user.getURLDecoded()%>--%>

