<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" class="com.es.phoneshop.model.product.Product" scope="request"/>

<tags:master pageTitle="Product Details">

    <p>
        <img class="img-fluid float-left"
             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"/>
    </p>
    <div>
    <h4>${product.description}<br/></h4>
    <h4>Price:<fmt:formatNumber value="${product.price}" type="currency"
                                currencySymbol="${product.currency.symbol}"/></h4>
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
    </div>

</tags:master>
