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
    <title></title>
</head>
<body>


<div class="iapi" id="${sessionScope.id}">
    <c:out value="${sessionScope.result}" />
</div>
</body>
</html>
