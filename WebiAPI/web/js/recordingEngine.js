/**
 * Created by les on 07/05/14.
 */
var instructions = new Array();
var forms = new Array(); //this array is used to prevent multiple additions of a form in the language due to the high frequency of the mouseenter event triggering.
var divs = new Array(); //same as before
var actualIstruction;
var isProgramEmpty;

function startRecorder() {

    localStorage.setItem("isRecording", "yes");

    $("#iapistoprecoridng").removeAttr("disabled");
    $("#iapistartrecoridng").attr("disabled","disabled");

    //this fixes multiple open in the language
    if(localStorage.getItem("program") == null)
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
            if(divs[current.id] == undefined){
                console.log("added result for "+current.id);

                divs[current.id] = 1;
                instructions.push(new Istruction("result", current.id));
            }
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



};
function doBeforeUnload(){

    var text = "";

    for(var i=0;i < instructions.length;i++){
        var  instruction = instructions[i];
        text+=instruction.action;

        for(var j=0; j< instruction.parametersList.length;j++)
            text+=" "+instruction.parametersList[j];

        text+=";";
    }

    alert("This is the final text of this page "+text);

    var allProgram = localStorage.getItem("program");

    if(allProgram == null || allProgram == undefined)
        allProgram = text;
    else
        allProgram+=text;

    localStorage.setItem("program",allProgram);
};

function stopRecording(){
    doBeforeUnload();
    localStorage.setItem("isRecording", "no");
    $("#iapistartrecoridng").removeAttr("disabled");
    $("#iapistoprecoridng").attr("disabled","disabled");

    //do unbind of all elements
    console.log("Doing unbind");
    $("form.iapi").unbind("mouseenter");
    $("div.iapi.result").unbind("mouseenter");
    $("form.iapi").children().unbind("click focusin focusout");

    var text = localStorage.getItem("program");

    if(text != undefined && text != null )
        localStorage.setItem("program", text+"resultJSON;");


}
$(document).ready(function(){
    initSidebar();

    function initSidebar(){

        $("body").append("<div id=\"iapisidebar\" style=\"border-width: 1px;border-color: red;border-style: solid;width: 220px;height: 225px;top: 100px;right: 40px;padding: 10px;position: fixed; \"> \
            <button id=\"iapistartrecoridng\" onclick='startRecorder();'>Start</button><br /> \
                <button id=\"iapistoprecoridng\" onclick='stopRecording();'>Stop</button> \
                <p id=\"iapirecordingstatus\"></p> \
            </div>");

        var status = localStorage.getItem("isRecording");
        if(status == undefined || status == "no" || status == null){
            $("#iapistoprecoridng").attr("disabled","disabled");
        }
        else{
            $("#iapistartrecoridng").attr("disabled","disabled");
            startRecorder();
        }

        var top = $('#sidebar').offset().top - parseFloat($('#sidebar').css('marginTop').replace(/auto/, 0));
        $(window).scroll(function (event) {
            var y = $(this).scrollTop();
            //if y > top, it means that if we scroll down any more, parts of our element will be outside the viewport
            //so we move the element down so that it remains in view.
            if (y >= top) {
                var difference = y - top;
                $("#iapisidebar").css("top",difference);
            }
        });

    };

});