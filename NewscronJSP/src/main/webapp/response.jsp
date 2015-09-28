<%-- 
    Document   : response
    Created on : Sep 25, 2015, 3:05:04 PM
    Author     : Din
--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <li><p><b>First Name:</b>
       <%= request.getParameter("name") + request.getParameter("rew1") + request.getParameter("rew2") + request.getParameter("val")%>
    </p></li>
    </body>

</html>
