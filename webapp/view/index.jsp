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
                    <a class="text-muted" href="${pageContext.request.contextPath}/registration">Sign up</a>
                </div>
            </security:authorize>
            <div class="col-4 text-center">
                <a class="blog-header-logo text-dark" href="${pageContext.request.contextPath}/">News</a>
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
                    <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/login">Sign in</a>
                </security:authorize>
                <security:authorize access="isAuthenticated()">
                    <form action="${pageContext.request.contextPath}/logout" method="post">
                        <input type="submit" value="Logout" class="btn btn-outline-success my-2 my-sm-0"/>
                    </form>
                </security:authorize>
            </div>
        </div>
    </header>
    <div class="nav-scroller py-1 mb-2">
        <nav class="nav d-flex justify-content-between">
            <a class="p-2 text-muted" href="${pageContext.request.contextPath}/section?section=tech">Technology</a>
            <a class="p-2 text-muted" href="${pageContext.request.contextPath}/section?section=business">Business</a>
            <a class="p-2 text-muted" href="${pageContext.request.contextPath}/section?section=sport">Sport</a>
            <security:authorize access="hasRole('ADMIN')">
                <a class="p-2 text-muted" href="${pageContext.request.contextPath}/add">Add news</a>
            </security:authorize>
            <security:authorize access="hasRole('SUPER_ADMIN')">
                <a class="p-2 text-muted" href="${pageContext.request.contextPath}/add_admin">Add a new admin</a>
            </security:authorize>
        </nav>
    </div>
    <div class="jumbotron p-4 p-md-5 text-white rounded bg-dark">
        <div class="col-md-6 px-0">
            <h1 class="display-4 font-italic">Title of a longer featured blog post</h1>
            <p class="lead my-3">Multiple lines of text that form the lede, informing new readers quickly and efficiently about what’s most interesting in this post’s contents.</p>
            <p class="lead mb-0"><a href="#" class="text-white font-weight-bold">Continue reading...</a></p>
        </div>
    </div>
    <h1><spring:message code="hello"/></h1>
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
                        <a href="${pageContext.request.contextPath}/comments?id=${news.id}&section=${news.section}" class="stretched-link">Continue reading with comments</a>
                    </security:authorize>
                </div>
                <div class="col-auto d-none d-lg-block">
                    <svg class="bd-placeholder-img" width="200" height="250" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"/><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
