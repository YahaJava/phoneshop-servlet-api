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
        <div class="w-25">
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
    <c:if test="${param.added}">
        <p class="text-center alert alert-success m-2">Product review successfully added!</p>
    </c:if>
    <c:if test="${param.error=='incorrect'}">
        <p class="text-center alert alert-danger m-2">Incorrect rating fill</p>
    </c:if>
    <c:if test="${param.error=='empty'}">
        <p class="text-center alert alert-danger m-2">Fill all empty fields</p>
    </c:if>

    <form method="post" action=${pageContext.servletContext.contextPath}/addComment>
        Name:<input type="text" name="name" value="${not empty param.name ? param.name : ""}"><br>
        Rating(0-10):<input type="text" name="rating" value="${not empty param.rating ? param.rating : ""}"><br>
        Comment:<textarea name="comment"> </textarea><br>
        <input type="hidden" name="productCode" value="${product.code}"/><br>
        <input type="submit" value="Add comment">
        <c:if test="${not empty error1}">
            <p class="alert alert-danger">${error1}</p>
        </c:if>
    </form>

    <c:forEach var="review" items="${reviews}">
        <p> Name: ${review.name} </p>
        <p> Rating: ${review.rating} </p>
        <p> Comment: ${review.comment} </p>
        <br>
    </c:forEach>

</tags:master>
