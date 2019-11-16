<%--
  Created by IntelliJ IDEA.
  User: Vladislav_Dankevich
  Date: 11/8/2019
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Sample page</title>
    <jsp:include page="config.jsp"/>
</head>
<body>
${errorInFile}
    <form:form action="save" method="post" modelAttribute="news" enctype="multipart/form-data" acceptCharset="UTF-8">
        <tr>
            <td>File: </td>
            <td><input type="file" name="file"/></td>
        </tr>
        <tr>
            <td>Section: </td>
            <td><form:input path="section"/></td>
        </tr>
        <tr>
            <td>Topic: </td>
            <td><form:input path="topic"/></td>
        </tr>
        <tr>
            <td>Short description: </td>
            <td><form:input path="shortDescription"/></td>
        </tr>
        <tr>
            <td>Description: </td>
            <td><form:input path="description"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Save"></td>
        </tr>
    </form:form>
</body>
</html>
