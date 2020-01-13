<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bucket" scope="request" type="mate.academy.internetshop.model.Bucket"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bucket</title>
</head>
<body>
<h3>ITEMS IN YOUR BUCKET:</h3>
<table border="1">
    <tr>
        <th>Item Name</th>
        <th>Item Price</th>
        <th>DELETE ITEM</th>

    </tr>
    <c:forEach var="item" items="${bucket.items}">
        <tr>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/deleteItemFromBucket?item_id=${item.id}"><p align="center">DELETE</p></a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
