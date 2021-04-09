<%-- 
    Document   : register
    Created on : Mar 2, 2021, 8:08:11 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <body>
        <h1>Register</h1>
        <c:set scope="page" var="error" value="${requestScope.userError}"/>
        <form action="RegisterController" method="POST">
            <table border="0">
                <tbody>
                    <tr>
                        <td> Email: </td>
                        <td> <input type="email" name="txtEmail" value="${param.txtEmail}" /> </td>
                        <td> <font style="color: red"> ${pageScope.error.emailError}</font> </td>
                    </tr>
                    <tr>
                        <td> Password </td>
                        <td> <input type="password" name="txtPassword" value="" /> </td>
                        <td> <font style="color: red"> ${pageScope.error.passwordError}</font> </td>
                    </tr>
                    <tr>
                        <td> Confirm: </td>
                        <td> <input type="password" name="txtConfrim" value="" /> </td>
                        <td> <font style="color: red"> ${pageScope.error.confirmError}</font> </td>
                    </tr>
                    <tr>
                        <td> Name: </td>
                        <td> <input type="text" name="txtName" value="${param.txtName}" /> </td>
                        <td> <font style="color: red"> ${pageScope.error.nameError}</font> </td>
                    </tr>
                    <tr>
                        <td> Phone: </td>
                        <td> <input type="number" name="txtPhone" value="${param.txtPhone}" /> </td>
                        <td> <font style="color: red"> ${pageScope.error.nameError}</font> </td>
                    </tr>
                    <tr>
                        <td> Address: </td>
                        <td> <input type="text" name="txtAddress" value="${param.txtAddress}" /> </td>
                        <td> <font style="color: red"> ${pageScope.error.addressError}</font> </td>
                    </tr>
                </tbody>
            </table>
                    <input type="submit" value="Register" />
        </form>
        <a href="login.jsp" style="text-decoration: none"> Login</a>
    </body>
</html>
