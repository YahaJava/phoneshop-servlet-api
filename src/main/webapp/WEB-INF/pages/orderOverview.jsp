<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Order Overview">
    <h2 class="text-center">Order Overview</h2>>
    <table class="table container">
        <tr>
            <td>Product</td>
            <td>Quantity</td>
            <td>Price</td>
        </tr>
        <c:forEach var="item" items="${order.cart.cartItems}">
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
            <td colspan="2">Subtotal:</td>
            <td>${order.cart.totalPrice}</td>
        </tr>
        <tr>
            <td colspan="2">Delivery cost:</td>
            <td>${order.deliveryCost}</td>
        </tr>
        <tr>
            <td colspan="2">Total:</td>
            <td>${order.cart.totalPrice+order.deliveryCost}</td>
        </tr>
    </table>
    <div class="container">
        <h5>Name: ${order.name}</h5>
        <h5>Date: ${order.date}</h5>
        <h5>Address: ${order.address}</h5>
        <h5>Payment: ${order.payment}</h5>
    </div>
</tags:master>