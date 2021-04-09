<%-- 
    Document   : verification
    Created on : Mar 4, 2021, 8:08:11 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verification Page</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.USER}">
            <a href="SearchController" style="text-decoration: none;margin-right: 20px">Home</a>
            <h2>Welcome, ${sessionScope.USER.name}</h2>
            <div style="color: green"> ${requestScope.msg} </div>
            <form action="SendGmailController" method="Post">
                <table border="0">
                    <tbody>
                        <tr>
                            <td>Email          :</td>
                            <td><input type="email" name="txtEmail" value="${sessionScope.USER.email}" /> </td>
                            <td> <font style="color: red"> ${requestScope.errorEmail} </font> </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="Send Gmail" /></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </form>

            <form action="VerificationController">
                <table border="0">
                    <tbody>
                        <tr>
                            <td>Verification Code:</td>
                            <td> <input type="text" name="txtVerificationCode" value="${param.txtVerificationCode}" /> </td>
                            <td> <font style="color: red"> ${requestScope.errorVerificationCode} </font></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="Verifi" /></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </c:if>
    </body>
</html>
