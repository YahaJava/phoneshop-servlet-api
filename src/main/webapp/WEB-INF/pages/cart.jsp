<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>

<tags:master pageTitle="Your cart">
    <c:if test="${param.delete}">
        <p class="text-center alert alert-success m-2">Product deleted!</p>
    </c:if>
    <c:if test="${param.update}">
        <p class="text-center alert alert-success m-2">Product quantity updated!</p>
    </c:if>
    <c:if test="${empty cart.cartItems}">
        <h3 class="text-center">Your cart is Empty!!!</h3>
    </c:if>
    <c:if test="${not empty cart.cartItems}">
        <h3 class="text-center">Your cart:</h3>
        <table class="table table-hover container">
            <tr>
                <td>Product</td>
                <td>Quantity</td>
                <td>Price</td>
                <td>Actions</td>
            </tr>
            <form method="post" id="update" action="${pageContext.servletContext.contextPath}/cart">
                <c:forEach var="item" items="${cart.cartItems}">
                    <tr>
                        <td><img class="product-tile"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}"/><br/>
                            <a href="<c:url value="/products/${item.product.code}"/>">${item.product.description}</a>
                        </td>
                        <td>${item.quantity}</td>
                        <td>${item.product.price*item.quantity}</td>
                        <td>
                            <div class="input-group mb-3 ">
                                <input form="update" class="form-control" type="text" name="quantity"
                                       value="${item.quantity}">
                                <input form="update" type="hidden" name="productCode" value="${item.product.code}"/>
                                    <button form="delete" class="btn btn-outline-secondary ml-1" name="productCode"
                                            value="${item.product.code}">delete
                                    </button>
                            </div>
                            <c:if test="${not empty error[item.product]}">
                                <p class="alert alert-danger m-1">${error[item.product]}</p>
                            </c:if>
                        </td>
                    </tr>

                </c:forEach>
                <tr>
                    <td>Total:</td>
                    <td>${cart.totalQuantity}</td>
                    <td>${cart.totalPrice}</td>
                    <td>
                        <button class="text-right btn btn-info" form="update">update</button>
                        <button class="text-right btn btn-info ml-1" form="checkout">Submit</button>
                    </td>
                </tr>
            </form>
            <form id="delete"
                  action="${pageContext.servletContext.contextPath}/cart/deleteCartItem"
                  method="post">
            </form>
            <form id="checkout"
                  action="${pageContext.servletContext.contextPath}/checkout"
                  method="get">
            </form>
        </table>
    </c:if>
</tags:master>

