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
<html>
<head>
    <title>Comments to news</title>
    <jsp:include page="config.jsp"/>
</head>
<body>
${news_by_id.topic}
${news_by_id.description}
    <c:forEach var="comment" items="${comments_to_news}">
        ${comment.authorName}
        ${comment.description}
    </c:forEach>
    <form:form action="add_comment" method="post" modelAttribute="comment">
        <form:hidden path="newsID" value="${param.id}"/>
        <form:input path="authorName"/>
        <form:input path="description"/>
        <input type="submit" value="Add comment">
    </form:form>
</body>
</html>
