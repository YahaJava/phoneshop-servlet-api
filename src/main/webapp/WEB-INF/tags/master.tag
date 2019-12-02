<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css">
  <script src="${pageContext.servletContext.contextPath}/js/bootstrap.min.js"></script>
</head>
<body class="product-list">
  <header>
    <a href="${pageContext.servletContext.contextPath}">
      <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
      PhoneShop
    </a>
    <c:import url="/cart/minicart" />
  </header>
  <main>
    <jsp:doBody/>
  </main>
</body>
</html>