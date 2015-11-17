<%-- 
    Document   : beansform
    Created on : Sep 25, 2015, 4:25:44 PM
    Author     : Din
--%>

<%@page import="ch.newscron.newscronjsp.ShortUrlUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<body>
    <h1>Invite data</h1>
    <form method=POST action=ShortUrlUtils>
        CustomerID <input type=text name="custID"><br>
        Reward1 <input type=text name="rew1"><br>
        Reward2 <input type=text name="rew2"><br>
        Validity <input type=text name="val">
        <p><input type=submit name="newURL">
    </form>
</body>

<style>
    body {
        font-size: 40px;
    }
    h1 {
        font-size: 100px;
        text-align: center;
    }
    form {
        width: 435px;
        margin: 0 auto;
        text-align: center;
    }
    input {
        font-size: 40px;
        margin-left: auto;
        margin-right: auto;
    }
</style>
</html>
