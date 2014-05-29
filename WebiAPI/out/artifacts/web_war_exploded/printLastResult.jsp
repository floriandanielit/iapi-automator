<%--
  Created by IntelliJ IDEA.
  User: les
  Date: 07/05/14
  Time: 16:54
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

<div class="i-result:lastResult" id="${sessionScope.id}">
    <c:out value="${sessionScope.result}" />
</div>

</body>
</html>