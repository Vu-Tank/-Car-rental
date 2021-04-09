<%-- 
    Document   : login.jsp
    Created on : Mar 2, 2021, 7:45:25 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body>
        <h1>Car rental</h1>
        <form action="LoginController" method="POST">
            <table border="0">
                <tbody>
                    <tr>
                        <td>Email:</td>
                        <td> <input type="email" name="txtEmail" value="${param.txtEmail}" /> </td>
                        <td> <font style="color: red"> ${requestScope.errorEmail} </font> </td>
                    </tr>
                    <tr>
                        <td> Password: </td>
                        <td> <input type="password" name="txtPassword" value="" /> </td>
                        <td>  <font style="color: red"> ${requestScope.errorPassword}</td>
                    </tr>
                </tbody>
            </table>
            <div class="g-recaptcha" data-sitekey="6LdYdnEaAAAAAPXV6Av64CNf_Utyy0iM_oMMG0xP"></div>
            <div style="color: red"> ${requestScope.message}</div>
            <input type="submit" value="Login"/>
        </form>
        <a href="register.jsp" style="text-decoration: none">Register</a>
    </body>
</html>
