<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" class="com.es.phoneshop.model.product.Product" scope="request"/>

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
    <form method="post" action=${pageContext.servletContext.contextPath}/products/${product.code}>
    Quantity: <input type="text" name="quantity" value="${not empty param.quantity ? param.quantity : 1}">
    <input type="submit" value="add to cart"><br/>
    <c:if test="${not empty error}">
        <p style="color: red">${error}</p>
    </c:if>
    <c:if test="${param.success}">
        <p style="color: green">Product added to card!</p>
    </c:if>
    </form>
    </p>
</tags:master>
