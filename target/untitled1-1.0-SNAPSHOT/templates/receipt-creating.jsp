<%--
  Created by IntelliJ IDEA.
  User: yevheniikaliukh
  Date: 31.10.2021
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>This is page for the cashier</h1>
<% String ddd = request.getHeader("Authorization");
    System.out.println("ddd " + ddd);
    System.out.println("ddd1 " + response.getHeader("Authorization"));
    %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        var max_fields = 10;
        var wrapper = $(".container1");
        var add_button = $(".add_form_field");

        var x = 1;
        $(add_button).click(function (e) {
            e.preventDefault();
            if (x < max_fields) {
                x++;
                $(wrapper).append('<div><input type="text" name="product_code_and_quantity"/><input type="text" name="product_code_and_quantity"/><a href="#" class="delete">Delete</a></div>'); //add input box
            } else {
                alert('You Reached the limits')
            }
        });

        $(wrapper).on("click", ".delete", function (e) {
            e.preventDefault();
            $(this).parent('div').remove();
            x--;
        })
    });

    function submitData() {
        let name = document.forms["form"]["name"].value;
        let price = document.forms["form"]["price"].value;
        let quantity = document.forms["form"]["quantity"].value;
        fetch('http://localhost:8000/products', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({name, price, quantity})
        });
        return false;
    }
</script>
<form action="/receipts" method="post" >
    <div class="container1">
        <button class="add_form_field">Add New Field &nbsp;
            <span style="font-size:16px; font-weight:bold;">+ </span>
        </button>
        <div><input type="text" name="product_code_and_quantity"/><input type="number"
                                                                         name="product_code_and_quantity"/></div>
    </div>
    <input type="submit" value="SUBMIT"/>
</form>

</body>
</html>
