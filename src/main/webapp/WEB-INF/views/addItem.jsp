<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Item</title>
</head>
<body>
<form action="/supershop_war_exploded/servlet/addItem" method="post">
    <div class="container">
        <h3>ITEM ADDING</h3>

        <p>
        <label for="item_name"><b>Item name</b></label>
        <input type="text" placeholder="Enter Name" name="item_name" required>
        </p>
        <p>
        <label for="item_price"><b>Item price</b></label>
        <input type="text" placeholder="Enter Price" name="item_price" required>
        </p>

        <button type="submit" class="registerbtn">Add item</button>
    </div>

</form>
</body>
</html>
