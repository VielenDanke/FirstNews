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
<html>
<head>
    <title>Comments to news</title>
    <jsp:include page="config.jsp"/>
</head>
<body>
<img src="data:image/jpg;base64,${news_by_id.fileInputStreamName}" class="card-img" alt="image" width="50" height="120">
${news_by_id.topic}
${news_by_id.description}
${news_by_id.localDate}
    <c:forEach var="comment" items="${comments_to_news}">
        ${comment.authorName}
        ${comment.descriptionComment}
        <security:authorize access="hasAnyRole('ADMIN', 'SUPER_ADMIN')">
            <form:form action="delete/comment/${comment.id}" method="post">
                <input type="submit" value="Delete comment" class="btn btn-primary">
            </form:form>
        </security:authorize>
    </c:forEach>
    <form:form action="add_comment" method="post" modelAttribute="comment">
        <form:hidden path="newsID" value="${param.id}"/>
        <form:hidden path="authorName" value="${username}"/>
        <form:input path="descriptionComment" onkeyup="return symbolChecker(this)"/>
        <input type="submit" value="Add comment">
    </form:form>
<security:authorize access="hasAnyRole('ADMIN', 'SUPER_ADMIN')">
    <form:form action="${news_by_id.id}" method="post" modelAttribute="news_by_id">
        <form:input path="topic" onkeyup="return symbolChecker(this)"/>
        <form:input path="shortDescription" onkeyup="return symbolChecker(this)"/>
        <form:input path="description" onkeyup="return symbolChecker(this)"/>
        <input type="submit" value="Update" class="btn btn-primary">
    </form:form>
</security:authorize>
<security:authorize access="hasAnyRole('ADMIN', 'SUPER_ADMIN')">
    <form:form action="delete/${news_by_id.id}" method="post">
        <input type="submit" value="Delete news" class="btn btn-primary">
    </form:form>
</security:authorize>
</body>
</html>
