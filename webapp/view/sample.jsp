<%--
  Created by IntelliJ IDEA.
  User: Vladislav_Dankevich
  Date: 11/8/2019
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Sample page</title>
    <jsp:include page="config.jsp"/>
</head>
<body>
${errorInFile}
    <form:form action="save" method="post" modelAttribute="news" enctype="multipart/form-data" acceptCharset="UTF-8">
        <div class="card" style="width: 18rem;">
            <spring:message code="index.word.file"/>: <input type="file" name="file" required="required"/>
            <div class="card-body">
                <h5 class="card-title">Card title</h5>
                <p class="card-text"><spring:message code="index.word.section"/>: <form:select path="section" items="${sectionList}"/></p>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><spring:message code="index.word.topic"/>:
                    <form:input path="topic" onkeyup="return symbolChecker(this)" requaried="required"/></li>
                <li class="list-group-item"><spring:message code="index.word.short.description"/>:
                    <form:input path="shortDescription" onkeyup="return symbolChecker(this)" requaried="required"/></li>
                <li class="list-group-item"><spring:message code="index.word.description"/>:
                    <form:input path="description" onkeyup="return symbolChecker(this)" requaried="required"/></li>
            </ul>
            <div class="card-body">
                <input type="submit" value="<spring:message code="index.word.save"/>">
                <a href="${pageContext.request.contextPath}/" class="card-link"><spring:message code="index.main.page"/></a>
            </div>
        </div>
    </form:form>
</body>
</html>
