<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Danke
  Date: 019 19.11.19
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="index.word.users"/></title>
</head>
<body>
    <c:forEach var="userFromDB" items="${userList}">
        ${userFromDB.id}
        ${userFromDB.username}
        <form:form action="delete/user/${userFromDB.id}">
            <input type="submit" value="<spring:message code="index.word.delete"/>" class="btn btn-primary"/>
        </form:form>
    </c:forEach>
<a href="${pageContext.request.contextPath}/"><spring:message code="index.main.page"/></a>
</body>
</html>
