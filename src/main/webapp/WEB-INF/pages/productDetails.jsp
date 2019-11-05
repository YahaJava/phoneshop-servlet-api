<%@ page contentType="text/html;charset=UTF-8"  %>

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
</tags:master>
