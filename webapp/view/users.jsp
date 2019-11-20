<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
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
    <jsp:include page="config.jsp"/>
</head>
<body>
<h3>Searching user</h3>
${error}
<form method="get" action="${pageContext.request.contextPath}/user/searching_by">
    <input type="text" name="search" placeholder="<spring:message code="index.type.text.placeholder"/>" maxlength="50" onkeyup="return symbolChecker(this)">
    <input type="radio" name="radio" value="ID" checked>Searching by id
    <input type="radio" name="radio" value="Username">Searching by username
    <input type="submit" value="<spring:message code="index.searching"/>" class="btn btn-primary">
</form>
<table class="table">
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col"><spring:message code="index.word.username"/></th>
        <th scope="col"><spring:message code="index.word.delete"/></th>
    </tr>
    </thead>
    <tbody>
<c:forEach var="userFromDB" items="${userList}">
    <tr>
        <th scope="row">${userFromDB.id}</th>
        <td>${userFromDB.username}</td>
        <td><form:form action="delete/user/${userFromDB.id}">
            <input type="submit" value="<spring:message code="index.word.delete"/>" class="btn btn-primary"/>
        </form:form></td>
    </tr>
</c:forEach>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/"><spring:message code="index.main.page"/></a>
</body>
</html>