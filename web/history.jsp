<%-- 
    Document   : history
    Created on : Mar 7, 2021, 4:27:15 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
    </head>
    <body>
        <div class="hearder">
            <h2> Weclome , ${sessionScope.USER.name}</h2>
            <a href="LogOutController" style="text-decoration: none;margin-right: 20px">LogOut</a>
            <a href="cart.jsp" style="text-decoration: none;margin-right: 20px">Cart</a>
            <a href="SearchController" style="text-decoration: none;margin-right: 20px">Home</a>
        </div>
        <form action="SearchHistoryController">
            <table border="0">
                <tbody>
                    <tr>
                        <td> <input type="radio" name="typeSearchH" value="name"  checked=""/> Search By Name:</td>
                        <td> <input type="text" name="txtSearch" value="${sessionScope.dataSearch}" style="width: 200px"/> </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> <input type="radio" name="typeSearchH" value="date" <c:if test="${sessionScope.typeSearchH eq ('date')}"> checked=""</c:if> /> Search By Order Date</td>
                            <td>Order date</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td> <input type="date" name="txtOrderDate" value="${sessionScope.txtOrderDate}"/> </td>
                        <td> <input type="submit" value="Search" /> </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><font style="color: red"> ${requestScope.dateError} </font></td>
                        <td><font style="color: red"> </font></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </form>
        <c:if test="${not empty sessionScope.history}">
            <h2 style="color: green">${requestScope.Romove}</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Booking Date</th>
                        <th>Price</th>
                        <th>Discount Code</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cart" items="${sessionScope.history}" varStatus="counter">
                        <tr>
                    <form action="ViewDetailHController">
                        <td>${counter.count}</td>
                        <td>${pageScope.cart.bookingDate}</td>
                        <td>${pageScope.cart.price}</td>
                        <td>${pageScope.cart.discountCode} <c:if test="${empty pageScope.cart.discountCode}"> N/A</c:if></td>
                        <td><c:if test="${!pageScope.cart.status}">Inactivate</c:if> <c:if test="${pageScope.cart.status}">Activate</c:if></td>
                            <td> 
                                <input type="hidden" name="txtOrderID" value="${pageScope.cart.orderID}" />
                            <input type="submit" value="VIew More" /> 
                        </td>
                    </form>

                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
<h2> Result: ${sessionScope.result} </h2>
</body>
</html>
