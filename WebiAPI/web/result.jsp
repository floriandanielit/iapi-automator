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

<div class="iapi result" id="${sessionScope.id}">
    <c:out value="${sessionScope.result}" />
</div>
<br />
<form class="iapi" id="3" method="post" action="Register">
    <input class="text:realName" type="text" id="name" name="realName"/>
    <input class="text:home" type="text" id="residence" name="home" />
    <input class="checkbox:single" type="checkbox" id="single" name="single" />
    <input class="submit" type="submit" id="btn" value="submit">
</form>
</body>
</html>
