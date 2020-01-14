<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show All Items</title>
</head>
<body>
<h3>ALL ITEMS LIST:</h3>
<table border="1">
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>ADD TO BUCKET</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/addItemToBucket?item_id=${item.id}"><p align="center">ADD</p></a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
