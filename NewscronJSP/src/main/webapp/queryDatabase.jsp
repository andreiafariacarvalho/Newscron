<%-- 
    Document   : queryDatabase
    Created on : Nov 2, 2015, 1:53:18 PM
    Author     : Din
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2> Get unique shortURLs for specific user </h2>
        <form method=POST action="userShortUrlStats.jsp">
        Customer ID <input type=text name=customerId><br>
        <p><input type=submit name="queryDatabase">
        </form>        
        <br> <br> <br>
        <h2> Get USER database </h2>
        <form method=POST action="showAllUserData.jsp">
        <p><input type=submit name="queryUserDatabase">
        </form>
    </body>
</html>
