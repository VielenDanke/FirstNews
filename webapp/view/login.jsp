<%--
  Created by IntelliJ IDEA.
  User: Vladislav_Dankevich
  Date: 11/8/2019
  Time: 4:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Login</title>
    <jsp:include page="config.jsp"/>
    <link href="<c:url value="/assets/css/custom.css" />" rel="stylesheet">
</head>
<body class="text-center">
<div class="wrapper fadeInDown">
    <div id="formContent">

        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/assets/img/breaking_news.png" id="icon" alt="User Icon" />
        </div>

        <form:form action="login" method="post" modelAttribute="user">
            <form:input path="username" cssClass="fadeIn second" onkeyup="return symbolChecker(this)"/>
            <form:password path="password" cssClass="fadeIn third" onkeyup="return symbolChecker(this)"/>
            <input type="submit" class="fadeIn fourth" value="<spring:message code="index.login"/>">
        </form:form>

        <div id="formFooter">
            <a class="underlineHover" href="${pageContext.request.contextPath}/registration"><spring:message code="index.registration"/></a>
        </div>
    </div>
</div>
</body>
</html>
