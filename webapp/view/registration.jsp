<%--
  Created by IntelliJ IDEA.
  User: Vladislav_Dankevich
  Date: 11/8/2019
  Time: 4:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Registration</title>
    <jsp:include page="config.jsp"/>
</head>
<body>
${error}
<form:form action="registration" method="post" modelAttribute="user">
    <tr>
        <td>Username: </td>
        <td><form:input path="username"/></td>
    </tr>
    <tr>
        <td>Password: </td>
        <td><form:password path="password"/></td>
    </tr>
    <tr>
        <td><input type="submit" value="Registration"></td>
    </tr>
</form:form>
</body>
</html>
