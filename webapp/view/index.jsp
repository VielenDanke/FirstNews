<%--
  Created by IntelliJ IDEA.
  User: Vladislav_Dankevich
  Date: 11/8/2019
  Time: 10:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Main page</title>
    <jsp:include page="config.jsp"/>
</head>
<body>
<div class="container">
    <header class="blog-header py-3">
        <div class="row flex-nowrap justify-content-between align-items-center">
            <security:authorize access="!isAuthenticated()">
                <div class="col-4 pt-1">
                    <a class="text-muted" href="${pageContext.request.contextPath}/registration"><spring:message code="index.registration"/></a>
                </div>
            </security:authorize>
            <div class="col-4 text-center">
                <a class="blog-header-logo text-dark" href="${pageContext.request.contextPath}/"><spring:message code="index.logo.word"/></a>
            </div>
            <div class="col-4 d-flex justify-content-end align-items-center">
                <div class="btn-group">
                    <a class="nav-link active" href="#" role="button" id="dropdownMenuLink3" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="${pageContext.request.contextPath}/assets/img/switch-lang.png" class="img-thumbnail" alt="logo" width="70" height="30">
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink3">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/?lang=ru">Русский</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/?lang=en">English</a>
                    </div>
                </div>
                <security:authorize access="!isAuthenticated()">
                    <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/login"><spring:message code="index.login"/></a>
                </security:authorize>
                <security:authorize access="isAuthenticated()">
                    <form action="${pageContext.request.contextPath}/logout" method="post">
                        <input type="submit" value="<spring:message code="index.logout"/>" class="btn btn-outline-success my-2 my-sm-0"/>
                    </form>
                </security:authorize>
            </div>
        </div>
    </header>
    <div class="nav-scroller py-1 mb-2">
        <nav class="nav d-flex justify-content-between">
            <a class="p-2 text-muted" href="${pageContext.request.contextPath}/section?section=tech"><spring:message code="index.section.tech"/></a>
            <a class="p-2 text-muted" href="${pageContext.request.contextPath}/section?section=business"><spring:message code="index.section.business"/></a>
            <a class="p-2 text-muted" href="${pageContext.request.contextPath}/section?section=sport"><spring:message code="index.section.sport"/></a>
            <security:authorize access="hasRole('ADMIN')">
                <a class="p-2 text-muted" href="${pageContext.request.contextPath}/add"><spring:message code="index.add.news"/></a>
            </security:authorize>
            <security:authorize access="hasRole('SUPER_ADMIN')">
                <a class="p-2 text-muted" href="${pageContext.request.contextPath}/add_admin"><spring:message code="index.add.admin"/></a>
            </security:authorize>
        </nav>
    </div>
    <div class="jumbotron p-4 p-md-5 text-white rounded bg-dark">
        <div class="col-md-6 px-0">
            <h1 class="display-4 font-italic">${lastNews.topic}</h1>
            <p class="lead my-3">${lastNews.shortDescription}</p>
            <p class="lead mb-0"><a href="${pageContext.request.contextPath}/comments?id=${lastNews.id}&section=${lastNews.section}" class="text-white font-weight-bold"><spring:message code="index.read.comment"/></a></p>
        </div>
    </div>
    <div class="row mb-2">
        <c:forEach items="${allNews}" var="news">
        <div class="col-md-6">
            <div class="row no-gutters border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                <div class="col p-4 d-flex flex-column position-static">
                    <strong class="d-inline-block mb-2 text-primary">${news.section}</strong>
                    <h3 class="mb-0">${news.topic}</h3>
                    <div class="mb-1 text-muted">Nov 12</div>
                    <p class="card-text mb-auto">${news.shortDescription}</p>
                    <security:authorize access="isAuthenticated()">
                        <a href="${pageContext.request.contextPath}/comments?id=${news.id}&section=${news.section}" class="stretched-link"><spring:message code="index.read.comment"/></a>
                    </security:authorize>
                </div>
                <div class="col-auto d-none d-lg-block">
                    <img src="data:image/jpg;base64,${news.fileInputStreamName}" class="card-img" alt="image" width="50" height="120">
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
