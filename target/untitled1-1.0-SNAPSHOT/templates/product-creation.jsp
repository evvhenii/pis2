<html>
<head>
    <title>Adding Form</title>
</head>
<body>
<h1>Add a new item</h1>
<form action="/products" method="post">
    <table style="width: 25%">
        <tr>
            <td>Name</td>
            <td><input type="text" name="name" /></td>
        </tr>
        <tr>
            <td>Quantity</td>
            <td><input type="number" name="quantity" /></td>
        </tr>
        <tr>
            <td>Price</td>
            <td><input type="number" name="price" /></td>
        </tr>
    </table>
    <input type="submit" value="Add" />
</form>
</body>
</html>