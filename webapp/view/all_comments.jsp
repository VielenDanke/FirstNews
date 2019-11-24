<%--
  Created by IntelliJ IDEA.
  User: Vladislav_Dankevich
  Date: 11/11/2019
  Time: 1:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Comments to news</title>
    <jsp:include page="config.jsp"/>
    <style>
        .responsive {
            max-height: 400px;
            max-width: 400px;
        }
    </style>
</head>
<body>
<div class="card mb-3" style="width: 100%;">
    <div class="row no-gutters">
        <div class="col-md-4">
            <img src="data:image/jpg;base64,${news_by_id.fileInputStreamName}" class="responsive" alt="...">
        </div>
        <div class="col-md-8">
            <div class="card-body">
                <h5 class="card-title">${news_by_id.topic}</h5>
                <p class="card-text">${news_by_id.description}</p>
                <p class="card-text"><small class="text-muted">${news_by_id.localDate}</small></p>
            </div>
        </div>
    </div>
</div>
<a href="${pageContext.request.contextPath}/" class="btn btn-primary"><spring:message code="index.main.page"/></a>
<table class="table">
    <thead>
    <tr>
        <th scope="col"><spring:message code="index.word.username"/></th>
        <th scope="col"><spring:message code="index.word.description"/></th>
        <security:authorize access="hasAnyRole('ADMIN', 'SUPER_ADMIN')">
            <th scope="col"><spring:message code="index.word.delete"/></th>
        </security:authorize>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="comment" items="${comments_to_news}">
    <tr>
        <th scope="row">${comment.authorName}</th>
        <td>${comment.descriptionComment}</td>
        <td>
            <security:authorize access="hasAnyRole('ADMIN', 'SUPER_ADMIN')">
                <form:form action="delete/comment/${comment.id}" method="post">
                    <input type="submit" value="<spring:message code="index.word.delete"/>" class="btn btn-primary">
                </form:form>
            </security:authorize>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
    <form:form action="add_comment" method="post" modelAttribute="comment">
        <form:hidden path="newsID" value="${param.id}"/>
        <form:hidden path="authorName" value="${username}"/>
        <form:input path="descriptionComment" onkeyup="return symbolChecker(this)"/>
        <input type="submit" value="<spring:message code="index.word.add.comment"/>" class="btn btn-primary">
    </form:form>
<security:authorize access="hasAnyRole('ADMIN', 'SUPER_ADMIN')">
    <form:form action="${news_by_id.id}" method="post" modelAttribute="news_by_id">
        <form:input path="topic" onkeyup="return symbolChecker(this)"/>
        <form:input path="shortDescription" onkeyup="return symbolChecker(this)"/>
        <form:input path="description" onkeyup="return symbolChecker(this)"/>
        <input type="submit" value="<spring:message code="index.word.update"/>" class="btn btn-primary">
    </form:form>
</security:authorize>
<security:authorize access="hasAnyRole('ADMIN', 'SUPER_ADMIN')">
    <form:form action="delete/${news_by_id.id}" method="post">
        <input type="submit" value="<spring:message code="index.word.delete.news"/>" class="btn btn-primary">
    </form:form>
</security:authorize>
</body>
</html>
