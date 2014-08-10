<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- saved from url=(0050)http://getbootstrap.com/examples/starter-template/ -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Interactive API (iAPI) Demos">
    <meta name="author" content="Florian Daniel and contributors">
    <link rel="shortcut icon" href="logo.png">

    <title>Demo Form 1</title>

    <!-- Bootstrap core CSS -->
    <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="http://getbootstrap.com/examples/starter-template/starter-template.css" rel="stylesheet">
    <script src="./js/jquery.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
    <script src="js/istruction.js"></script>
    <script src="js/recordingEngine.js"></script>
    <script src="js/prototype.js"></script>
    <script src="js/linker.js"></script>
    <script src="js/jquery.simplemodal.js" ></script>
    <link type='text/css' href='css/demo.css' rel='stylesheet' media='screen' />
    <link type='text/css' href='css/basic.css' rel='stylesheet' media='screen' />


    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  <style type="text/css"></style></head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index.html"><img src="logo.png" height="25px">interactive APIs</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="index.html">Demos</a></li>
            <li><a href="https://github.com/floriandanielit/interactive-apis/" target="_blank">Chrome extension</a></li>
            <li><a href="http://www.w3.org/community/interative-apis/" target="_blank">iAPI markup specification</a></li>
            <li><a href="#" target="_blank">iAPI JS library</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
    <div id="wrap">
    <div class="container">
        <br />
        <c:choose>
            <c:when test="${sessionScope.result =='login eseguito con successo'}">
                <div class="alert alert-success" role="alert">
                    <strong>
                        Result:
                    </strong><br/>
                    <div class="i-result:loginResult" id="${sessionScope.id}">

                        <c:out value="${sessionScope.result}" />
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    <strong>
                        Result:
                    </strong><br/>
                    <div class="i-result:loginResult" id="${sessionScope.id}">
                        <c:out value="${sessionScope.result}" />

                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        <br />
        <div class="col-lg-12">
            <form style="width: 330px;margin-right: auto;margin-left: auto;" class="h-iapi e-form:register form-signin" id="3" method="post" action="Register">
                <h2>Insert your informations</h2><br/>
                Real name: <input class="i-text:realName form-control" type="text" id="name" name="realName"/>
                Address: <input class="i-text:home form-control" type="text" id="residence" name="home" />
                Single: <input class="i-checkbox:single form-control" type="checkbox" id="single" name="single" /><br/>
                <input class="i-submit btn btn-lg btn-primary btn-block" type="submit" id="btn" value="Send" />
            </form>
        </div>

        <div id="basic-modal-content">
            <h3>Result Linker</h3>
            <p>In this modal you can link previous results to the selected input</p>

        </div>
    </div><!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->

  

</body></html>