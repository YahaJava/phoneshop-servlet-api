<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" class="com.es.phoneshop.model.product.Product" scope="request"/>

<tags:master pageTitle="Product Details">
    <div class="d-flex flex-row m-2">
    <div>
        <img class="img-fluid float-left"
             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"/>
    </div>
    <div  class="w-25" >
        <h4>${product.description}<br/></h4>
        <h4>Price:<fmt:formatNumber value="${product.price}" type="currency"
                                    currencySymbol="${product.currency.symbol}"/></h4>
        <form method="post" action=${pageContext.servletContext.contextPath}/products/${product.code}>
            <div class="input-group mb-3 ">
                <input type="text" class="form-control" placeholder="Quantity" name="quantity"
                       value="${not empty param.quantity ? param.quantity : 1}">
                <div class="input-group-append">
                    <input class="btn btn-outline-secondary" type="submit" value="add to cart">
                </div>
            </div>
                <c:if test="${not empty error}">
                    <p class="alert alert-danger">${error}</p>
                </c:if>
                <c:if test="${param.success}">
                    <p class="alert alert-success">Product added to card!</p>
                </c:if>

        </form>
    </div>
    </div>

</tags:master>
