<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="query" required="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>
<a href="<c:url value="/products?query=${query}&sort=${sort}&order=${order}"/>">
    <c:if test="${order == 'asc'}">
        <img src="https://image.freepik.com/free-icon/no-translate-detected_318-42165.jpg" width="15px" height="15px"/>
    </c:if>
    <c:if test="${order =='desc'}">
        <img src="https://image.flaticon.com/icons/png/512/36/36669.png" width="15px" height="15px"/>
    </c:if>
</a>