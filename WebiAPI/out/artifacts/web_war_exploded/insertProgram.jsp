<%--
  Created by IntelliJ IDEA.
  User: les
  Date: 15/04/14
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Insert your program there</title>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="js/precompiler.js" ></script>
    <script src="js/jquery.simplemodal.js" ></script>
    <link type='text/css' href='css/demo.css' rel='stylesheet' media='screen' />
    <link type='text/css' href='css/basic.css' rel='stylesheet' media='screen' />

</head>
<body>
    <form id="formCompiler" name="compiler" action="Compile" method="post">
        <input type="text" name="program" id="programContainer" />
        <input type="text" name="delimiter" id="del" />
        <input type="submit" class="basic" name="btn" value="submit program" />
    </form>

    <div id="basic-modal-content">
        <h3>Dynamic field complete</h3>
        <p>In this modal you can fill all the input you previously set as dynamic</p>

    </div>
</body>
</html>
