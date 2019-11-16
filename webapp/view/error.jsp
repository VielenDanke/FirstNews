<%--
  Created by IntelliJ IDEA.
  User: Vladislav_Dankevich
  Date: 11/13/2019
  Time: 2:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Error page</title>
    <jsp:include page="config.jsp"/>
</head>
<body>
    ${sessionScope.error}
    <a href="${pageContext.request.contextPath}/">Go to main page</a>
</body>
</html>
