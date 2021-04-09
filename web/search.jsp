<%-- 
    Document   : search
    Created on : Mar 4, 2021, 8:06:37 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rental Car</title>
    </head>
    <body>
        <div class="hearder"> 

            <c:if test="${empty sessionScope.USER}">
                <a href="login.jsp" style="text-decoration: none;margin-right: 20px">Login</a>
                <a href="register.jsp">Register</a>
            </c:if>
            <c:if test="${not empty sessionScope.USER}">
                <h2> Weclome , ${sessionScope.USER.name}</h2>
                <a href="LogOutController" style="text-decoration: none;margin-right: 20px">LogOut</a>
                <c:if test="${sessionScope.USER.status}">
                    <a href="cart.jsp" style="text-decoration: none;margin-right: 20px">Cart</a>
                    <a href="SearchHistoryController" style="text-decoration: none;margin-right: 20px">History</a>
                </c:if>
                <c:if test="${!sessionScope.USER.status}">
                    <a href="verification.jsp" style="text-decoration: none;margin-right: 20px">Verify Account</a>
                </c:if>
            </c:if>
        </div>

        <h2 style="color: green"> ${requestScope.msg}</h2>

        <form action="SearchController">
            <table border="0">
                <tbody>
                    <tr>
                        <td> <input type="radio" name="typeSearch" value="name"  checked=""/> Seach By Name:</td>
                        <td> <input type="text" name="dataSearch" value="${sessionScope.dataSearch}" style="width: 200px"/> </td>
                    </tr>
                    <tr>
                        <td> <input type="radio" name="typeSearch" value="category" <c:if test="${sessionScope.typeSearch eq ('category')}"> checked=""</c:if> /> Seach By Category:</td>
                            <td>
                                <select name="dataSearchCbx" style="width: 200px">
                                <c:forEach var="category" items="${sessionScope.categorys}">
                                    <option value="${pageScope.category.categoryID}" <c:if test="${pageScope.category.categoryID eq sessionScope.dataSearchCbx}"> selected="selected"</c:if> >${pageScope.category.categoryName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>
            <table border="0">
                <tbody>
                    <tr>
                        <td>rental date</td>
                        <td>return date</td>
                        <td>amount</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> <input type="date" name="txtRentalDate" value="${sessionScope.txtRentalDate}" min="${sessionScope.dateNow}" /> </td>
                        <td> <input type="date" name="txtReturnDate" value="${sessionScope.txtReturnDate}" min="${sessionScope.dateNext}"/> </td>
                        <td> <input type="number" name="txtAmount" value="${sessionScope.txtAmount}" min="1" /> </td>
                        <td> <input type="submit" value="Search" /> </td>
                    </tr>
                    <tr>
                        <td><font style="color: red"> ${requestScope.dateError} </font></td>
                        <td><font style="color: red"> </font></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </form>
        <c:if test="${sessionScope.result>0}">
            <table border="1" style="text-align: center;width: 100%" >
                <tbody>
                    <tr>
                        <td>Image</td>
                        <td>Name</td>
                        <td>Category</td>
                        <td>Color</td>
                        <td>Year</td>
                        <td>Price</td>
                        <td>Amount</td>
                        <c:if test="${not empty sessionScope.USER}">
                            <c:if test="${sessionScope.USER.status}">
                            <td>

                            </td>
                            </c:if>
                        </c:if>
                    </tr>
                    <c:forEach var="car" items="${sessionScope.cars}">
                        <tr>
                            <td> <img src="${pageScope.car.img}" alt="Loading..." width="300px" height="200px"/> </td>
                            <td> ${pageScope.car.carName} </td>
                            <td> 
                                <c:forEach var="category" items="${sessionScope.categorys}">
                                    <c:if test="${pageScope.car.categoryID eq pageScope.category.categoryID}">${pageScope.category.categoryName}</c:if>
                                </c:forEach>
                            </td>
                            <td> ${pageScope.car.color} </td>
                            <td> ${pageScope.car.year} </td>
                            <td> ${pageScope.car.price} </td>
                            <td> ${pageScope.car.quantity} </td>
                            <c:if test="${not empty sessionScope.USER}">
                                <c:if test="${sessionScope.USER.status}">
                                <td>
                                    <c:url var="rental" value="AddToCartController">
                                        <c:param name="txtCarID" value="${pageScope.car.carID}"></c:param>
                                        <c:param name="txtPrice" value="${pageScope.car.price}"></c:param>
                                        <c:param name="txtimg" value="${pageScope.car.img}"></c:param>
                                        <c:param name="txtName" value="${pageScope.car.carName}"></c:param>
                                    </c:url>
                                    <a href="${rental}" >Rental</a>
                                </td>
                                </c:if>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </c:if>               
        <h3>Result: ${sessionScope.result}</h3>
        <div class="paging">
            <c:if test="${sessionScope.endPage>1}">
                <c:forEach begin="1" end="${sessionScope.endPage}" var="i">
                    <c:url var="paging" value="SearchController">
                        <c:param name="typeSearch" value="${sessionScope.typeSearch}"></c:param>
                        <c:param name="dataSearch" value="${sessionScope.dataSearch}"></c:param>
                        <c:param name="txtAmount" value="${sessionScope.txtAmount}"></c:param>
                        <c:param name="txtRentalDate" value="${sessionScope.txtRentalDate}"></c:param>
                        <c:param name="txtReturnDate" value="${sessionScope.txtReturnDate}"></c:param>
                        <c:param name="pageIndex" value="${i}"></c:param>
                    </c:url>
                    <a href="${paging}">${i}</a>
                </c:forEach>
            </c:if>
        </div>
    </body>
</html>
