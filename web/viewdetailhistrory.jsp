<%-- 
    Document   : viewdetailhistrory
    Created on : Mar 7, 2021, 11:42:48 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail Order</title>
    </head>
    <body>
        <h2> Weclome , ${sessionScope.USER.name}</h2>
        <a href="LogOutController" style="text-decoration: none;margin-right: 20px">LogOut</a>
        <a href="cart.jsp" style="text-decoration: none;margin-right: 20px">Cart</a>
        <a href="SearchHistoryController" style="text-decoration: none;margin-right: 20px">History</a>
        <c:if test="${not empty sessionScope.detail}">
            <h2>${requestScope.error}</h2>
            <h2>${requestScope.msg}</h2>
            <table border="1" style="text-align: center;width: 100%">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Image</th>
                        <th>Car Name</th>
                        <th>Category</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Check In</th>
                        <th>Check Out</th>
                        <c:if test="${sessionScope.detail.status}">
                        <th>Ranting</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="orderDetail" items="${sessionScope.detail.getCart().values()}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td><img src=" ${pageScope.orderDetail.car.img} " alt="Loading..." width="300px" height="200px"/></td>
                            <td>${pageScope.orderDetail.car.carName}</td>
                            <td>
                                <c:forEach var="category" items="${sessionScope.categorys}">
                                    <c:if test="${pageScope.orderDetail.car.categoryID eq pageScope.category.categoryID}">${pageScope.category.categoryName}</c:if>
                                </c:forEach>
                            </td>
                            <td>${pageScope.orderDetail.quantity}</td>
                            <td>${pageScope.orderDetail.price}</td>
                            <td>${pageScope.orderDetail.checkIn}</td>
                            <td>${pageScope.orderDetail.checkOut}</td>
                            <c:if test="${sessionScope.detail.status}">
                            <td>
                                <form action="SendFeedbackController">
                                    <input type="hidden" name="txtOrderDeltailID" value="${pageScope.orderDetail.orderDetalID}" />
                                    <input type="number" name="txtRanting" value="${pageScope.orderDetail.feedback.value}" min="1" max="10" <c:if test="${!pageScope.orderDetail.status}"> readonly="true"</c:if> /> /10 </br>
                                    <c:if test="${pageScope.orderDetail.status}">
                                        <input type="submit" value="FeedBack" />
                                    </c:if>

                                </form>
                            </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${sessionScope.detail.status}"> 
                <c:url var="remove" value="RemoveOrderController">
                    <c:param name="txtOrderID" value="${sessionScope.detail.orderID}"/>
                </c:url>
                <a href="${remove}" onclick="return confirm('Do you want to delete ?')"> Remove</a>
            </c:if>
        </c:if>
    </body>
</html>
