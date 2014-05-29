<%--
  Created by IntelliJ IDEA.
  User: les
  Date: 14/04/14
  Time: 23:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="js/istruction.js"></script>
    <script src="js/recordingEngine.js"></script>

    <title></title>
</head>
<body>

<div class="i-result:loginResult" id="${sessionScope.id}">
    <c:out value="${sessionScope.result}" />
</div>
<br />
<form class="h-iapi e-form:register" id="3" method="post" action="Register">
    <input class="i-text:realName" type="text" id="name" name="realName"/>
    <input class="i-text:home" type="text" id="residence" name="home" />
    <input class="i-checkbox:single" type="checkbox" id="single" name="single" />
    <input class="i-submit" type="submit" id="btn" value="submit">
</form>
</body>
</html>
