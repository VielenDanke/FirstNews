<%--
  Created by IntelliJ IDEA.
  User: Danke
  Date: 012 12.11.19
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add admin</title>
    <jsp:include page="config.jsp"/>
</head>
<body>
    <form:form action="add_admin" method="post" modelAttribute="user">
        <tr>
            <td>Username: </td>
            <td><form:input path="username"/></td>
        </tr>
        <tr>
            <td>Password: </td>
            <td><form:input path="password"/></td>
        </tr>
        <tr>
            <td>Name: </td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td>Surname: </td>
            <td><form:input path="surname"/></td>
        </tr>
        <tr>
            <td>IIN: </td>
            <td><form:input path="iin"/></td>
        </tr>
        <input type="submit" value="Registration admin"/>
    </form:form>
</body>
</html>
