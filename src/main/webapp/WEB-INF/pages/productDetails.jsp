<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" class="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="cart" type="java.util.List" scope="request"/>
<tags:master pageTitle="Product Details">
    <table>
        <tr>
            <td><p>
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"/>
            </p>
            </td>
            <td>Description:${product.description}<br/></td>
            <td>Price:<fmt:formatNumber value="${product.price}" type="currency"
                                        currencySymbol="${product.currency.symbol}"/></td>
        </tr>
    </table>
    <p>
    <form method="post">
        <input type="text" name="quantity" value="1">
        <input type="submit" value="add to cart"><br/>
    <c:if test="${not empty error}">
        <p style="color: red">${error}</p>
    </c:if>
    <c:if test="${not empty sucses}">
        <p style="color: green">${sucses}</p>
    </c:if>
    </form>
    </p>
    <table>
        <tr>
            <td>Product</td>
            <td>Quantity</td>
        </tr>
        <c:forEach var="item" items="${cart}">
            <tr>
                <td>${item.product.description}</td>
                <td>${item.quantity}</td>
            </tr>
        </c:forEach>
    </table>

</tags:master>
