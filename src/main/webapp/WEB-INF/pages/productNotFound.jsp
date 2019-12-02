<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="product" type="java.lang.String" scope="request"/>
<tags:master pageTitle="Product Not Found">
    <h1 class="text-center" >Sorry! Product with code "${product}" not found!</h1>
</tags:master>