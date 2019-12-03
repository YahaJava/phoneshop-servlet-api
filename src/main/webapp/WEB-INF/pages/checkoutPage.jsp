<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Checkout Page">
    <h2 class="text-center">Checkout Page</h2>>
    <table class="table container">
        <tr>
            <td>Product</td>
            <td>Quantity</td>
            <td>Price</td>
        </tr>
        <c:forEach var="item" items="${cart.cartItems}">
            <tr>
                <td><img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}"/><br/>
                    <a href="<c:url value="/products/${item.product.code}"/>">${item.product.description}</a>
                </td>
                <td>${item.quantity}</td>
                <td>${item.product.price*item.quantity}</td>
            </tr>
        </c:forEach>

        <tr>
            <td>Total:</td>
            <td>${cart.totalQuantity}</td>
            <td>${cart.totalPrice}</td>
        </tr>
    </table>
    <br><br>
    <c:if test="${not empty error}">
        <p class="text-center alert alert-danger m-2">${error}</p>
    </c:if>
    <form method="post" action="checkout" class="container w-25 text-left">

        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter name" value="${not empty param.name ? param.name:""}">
        </div>
        <div class="form-group">
            <label for="date">Delivery Date</label>
            <input type="date" id="date" name="date" class="form-control" placeholder="date" value="${not empty param.date ? param.date:""}"/>
        </div>
        <div class="form-group">
            <label for="address">Adress</label>
            <input type="text" id="address" name="address" class="form-control" placeholder="address" value="${not empty param.address ? param.address:""}"/>
        </div>
        <div class="form-group">
            <label for="payment">Payment</label>
            <select name="payment" id="payment" class="form-control">
                <option value="card">Credit card</option>
                <option value="money">Money</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Place order</button>
    </form>
</tags:master>