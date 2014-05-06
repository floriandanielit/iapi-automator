<%--
  Created by IntelliJ IDEA.
  User: les
  Date: 14/04/14
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Form Example</title>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="js/istruction.js"></script>
</head>
<body>
<script type="text/javascript">
    var instructions = new Array();
    var forms = new Array(); //this array is used to prevent multiple additions of a form in the language due to the high frequency of the mouseenter event triggering.
    var actualIstruction;

    $(function() {

        instructions.push(new Istruction("open", document.baseURI));


        $("form.iapi").each(function(index, item){
            var  current = this;
            $(item).mouseenter(function(){

                if(forms[current.id] == undefined){
                    forms[current.id] = 1;
                    instructions.push(new Istruction("form", current.id));
                }
            });
        });


        $("div.iapi.result").each(function(index, item){
            var  current = this;
            $(item).mouseenter(function(){
                instructions.push(new Istruction("result", current.id));
            });
        });


        $items = $("form.iapi").children();


        $items.each(function(index, item){
            var current = this;
            if(item.type == "submit"){
                $(item).click(function(){
                   handleSubmit(current);
                })

            }
            else{
                $(item).focusin(function(){
                    handleMouseEnter(current);
                });
                $(item).focusout(function(){
                    handleMouseLeave(current);
                });
            }
        });
        function handleSubmit(item){

            actualIstruction = new Istruction("submit",item.id);
            instructions.push(actualIstruction);
            doBeforeUnload();
        }
        function handleMouseEnter(item){

            actualIstruction = new Istruction("fill",getIAPIclass($(item).attr('class')));
        }
        function handleMouseLeave(item){

            actualIstruction.parametersList.push(item.value);
            actualIstruction.parametersList.push(item.id);
            instructions.push(actualIstruction);


        }
        function getIAPIclass(inputClass){

            var classes = inputClass.split(" ");

            for(var i=0; i< classes.length; i++){

                if(classes[i].indexOf("inputType:") > -1)
                    return classes[i];
            }
            return "";
        }
        function doBeforeUnload(){
            console.log("Now exiting...");

            var text = "";

            for(var i=0;i < instructions.length;i++){
                var  instruction = instructions[i];
                text+=instruction.action+" ";

                for(var j=0; j< instruction.parametersList.length;j++)
                    text+=instruction.parametersList[j]+" ";

                text+=";";
            }

            alert("This is the final text of this page "+text);

            var allProgram = localStorage.getItem("program");
            alert("allProgram "+allProgram);

            if(allProgram == null || allProgram == undefined)
                allProgram = text;
            else
                allProgram+=text;

            localStorage.setItem("program",allProgram);
        }


    });
</script>
<form class="iapi" id="1" method="post" action="Login">
    <input class="inputType:Text" type="text" id="username" name="username"/>
    <input class="inputType:Password" type="password" id="password" name="password" />
    <input class="inputType:Submit" type="submit" id="btnSubmit" value="submit">
</form>
</body>
</html>
