<%-- 
    Document   : cart
    Created on : Mar 7, 2021, 12:20:13 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Car Page</title>
    </head>
    <body>
        <div class="hearder"> 
            <h2> Weclome , ${sessionScope.USER.name}</h2>
            <a href="LogOutController" style="text-decoration: none;margin-right: 20px">LogOut</a>
            <a href="SearchController" style="text-decoration: none;margin-right: 20px">Home</a>
            <a href="SearchHistoryController" style="text-decoration: none;margin-right: 20px">History</a>
        </div>
        <h2 style="color: green"> ${requestScope.msg}</h2>
        <c:if test="${not empty sessionScope.CART.getCart().values()}">

            <table border="1" style="text-align: center;width: 100%">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Check In</th>
                        <th>Check Out</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:set scope="page" var="order" value="${sessionScope.CART.getCart().values()}"/>
                    <c:forEach var="orderDtail" items="${pageScope.order}" varStatus="counter">
                        <tr>
                    <form action="UpdateCartController">
                        <td>${counter.count}</td>
                        <td><img src="${pageScope.orderDtail.car.img}" alt="Loading..." width="300px" height="200px"/></td>
                        <td>${pageScope.orderDtail.car.carName}</td>
                        <td>
                            <input type="number" name="txtQuantity" value="${pageScope.orderDtail.quantity}" /> </br>
                            <c:if test="${requestScope.errorID eq pageScope.orderDtail.orderDetalID}"> ${requestScope.errorQuantity}</c:if>
                            </td>
                            <td>
                            ${pageScope.orderDtail.price}
                            <c:set scope="page" var="total" value="${pageScope.orderDtail.price +total}"/>
                        </td>
                        <td>
                            <input type="date" name="txtRentalDate" value="${pageScope.orderDtail.checkIn}" min="${sessionScope.dateNow}" /></br>
                            <c:if test="${requestScope.errorID eq pageScope.orderDtail.orderDetalID}"> ${requestScope.rentalError}</c:if>
                            </td>
                            <td> 
                                <input type="date" name="txtReturnDate" value="${pageScope.orderDtail.checkOut}" min="${sessionScope.dateNext}"/></br>
                            <c:if test="${requestScope.errorID eq pageScope.orderDtail.orderDetalID}"> ${requestScope.returnError}</c:if>
                            </td>
                            <td> 
                                <input type="hidden" name="txtOrderDetalID" value="${pageScope.orderDtail.orderDetalID}" />
                            <input type="hidden" name="txtCarID" value="${pageScope.orderDtail.car.carID}" />
                            <input type="submit" value="Update" /> 
                        </td>
                    </form>
                    <td>
                        <c:url var="Delete" value="DeleteCartController">
                            <c:param name="txtOrderDetalID" value="${pageScope.orderDtail.orderDetalID}"></c:param>
                        </c:url>
                        <a href="${Delete}" onclick="return confirm('Do you want to delete ?')" >Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="info">
        <font>Total price: ${pageScope.total}</font> </br>
        <font>Discount price:  ${pageScope.total*sessionScope.discount.value/100}</font></br>
        <font>Payment Price: ${pageScope.total-(pageScope.total*sessionScope.discount.value/100)} </font></br>
    </div>
    <form action="DiscountController">
        Discount Code: <input type="text" name="txtDiscountCode" value="${sessionScope.discount.discountCode}"/><font style="color: red">${requestScope.errorCode}</font></br>
        <input type="submit" value="Check" />
    </form>
        <form action="CheckOutController">
            <input type="submit" value="Check Out" />
        </form>
</c:if>
        <c:if test="${empty sessionScope.CART}"> Has any car in your cart!!! </c:if>
</body>
</html>
