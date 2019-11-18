<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentProducts" type="java.util.List" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input type="text" name="query" value="${param.query}">
        <input type="submit" value="search">
    </form>

    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
                <tags:sort query="${param.query}" sort="description" order="asc"></tags:sort>
                <tags:sort query="${param.query}" sort="description" order="desc"></tags:sort>
            </td>
            <td class="price">Price
                <tags:sort query="${param.query}" sort="price" order="asc"></tags:sort>
                <tags:sort query="${param.query}" sort="price" order="desc"></tags:sort>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td><a href="<c:url value="/products/${product.code}"/>">${product.description}</a></td>
                <td class="price">
                    <a href="<c:url value="/products/priceHistory/${product.code}"/>">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${not empty recentProducts}">
        <h3>Recently viewed:</h3>
    </c:if>
    <table>
        <tr>
            <c:forEach var="item" items="${recentProducts}">
                <td><img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.imageUrl}"/><br/>
                    <a href="<c:url value="/products/${item.code}"/>">${item.description}</a><br/>
                    <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="${item.currency.symbol}"/></td>
                </td>
            </c:forEach>
        </tr>
    </table>
</tags:master>