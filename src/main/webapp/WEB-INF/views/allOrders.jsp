<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.model.Order>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Orders</title>
</head>
<body>
<h3>ALL ORDERS LIST:</h3>
<table border="1">
    <tr>
        <th>Order ID</th>
        <th>Items</th>
        <th>DELETE ORDER</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}" />
            </td>
            <td>
                <c:out value="${order.items}" />
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/deleteOrder?order_id=${order.id}"><p align="center">DELETE</p></a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
