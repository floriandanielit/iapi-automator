<%--
  Created by IntelliJ IDEA.
  User: les
  Date: 07/05/14
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

    <title>Compile Results</title>
</head>
<body>


<div>
    <c:out value="${sessionScope.result}" />
</div>
<br />
<a href="http://localhost:8080/web_war_exploded/formExample.jsp">Go back</a>

</body>
</html>
