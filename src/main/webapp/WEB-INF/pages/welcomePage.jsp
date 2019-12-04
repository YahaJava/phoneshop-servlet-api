<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <h1 class="text-center">
        Welcome to SmartPhone Shop!
    </h1>

    <form action=${pageContext.servletContext.contextPath}/products>
        <div class="text-center">
        <input class="text-right btn btn-info" type="submit" value="Go!">
        </div>
    </form>

</tags:master>