/**
 * Created by les on 07/05/14.
 */
var instructions = new Array();
var forms = new Array(); //this array is used to prevent multiple additions of a form in the language due to the high frequency of the mouseenter event triggering.
var divs = new Array(); //same as before
var actualIstruction;
var isProgramEmpty;


function sanitizeInput(input){
    return '~'+input;
}
function startRecorder() {

    localStorage.setItem("isRecording", "yes");

    $("#iapistoprecording").removeAttr("disabled");
    $("#iapistartrecording").attr("disabled","disabled");

    //this fixes multiple open in the language
    if(localStorage.getItem("program") == null)
        instructions.push(new Istruction("open", document.baseURI));

    $("form.h-iapi").each(function(index, item){
        var  current = this;
        $(item).mouseenter(function(){

            if(forms[current.id] == undefined){
                if(confirm("You have selected form with id: "+current.id+". Save it?")){
                    forms[current.id] = 1;
                    instructions.push(new Istruction("form", current.id));
                }
            }
        });
    });


    $("div[class*=i-result]").each(function(index, item){
        var  current = this;
        $(item).mouseenter(function(){
            if(divs[current.id] == undefined){
                if(confirm("You have selected div with id: "+current.id+". Save it?")){
                    divs[current.id] = 1;
                    instructions.push(new Istruction("result", current.id));
                }
            }
        });
    });


    $items = $("form.h-iapi").children();


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

       // actualIstruction = new Istruction("fill", sanitizeInput(getIAPIclass($(item).attr('class'))));
    }
    function handleMouseLeave(item){
        init();
        //if(confirm("Do you want this field to be dynamic?")){
          //  actualIstruction.parametersList.push("$dynamic$");
        //}
        //else
        actualIstruction = new Istruction("fill",sanitizeInput(item.value) );
        //actualIstruction.parametersList.push(sanitizeInput(item.value));
        actualIstruction.parametersList.push(sanitizeInput(item.id));

        //linker.js needed

        $("#iapisidebar").append("<input type=\"checkbox\" id=\""+instructions.length+"\"/>  "+"<a href='#' class='basic'>"+actualIstruction.action+" "+actualIstruction.parametersList[0]+" "+actualIstruction.parametersList[1]+"</a>"+'<br />')
        instructions.push(actualIstruction);


    }

};
function addDynamicFields(){
    var toChange = $("#iapisidebar input:checkbox");

    toChange.each(function(index, item){
        console.log(item.id)
        if(item.checked){
            var index = instructions[item.id].parametersList.length;
            instructions[item.id].parametersList[0] = "$dynamic";
        }

    });

}
function doBeforeUnload(){


    addDynamicFields();
    var text = "";

    for(var i=0;i < instructions.length;i++){
        var  instruction = instructions[i];
        text+=instruction.action;

        for(var j=0; j< instruction.parametersList.length;j++)
            text+=" "+instruction.parametersList[j];

        text+=";";
    }

    //alert("This is the final text of this page "+text);

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
    $("#iapistartrecording").removeAttr("disabled");
    $("#iapistoprecording").attr("disabled","disabled");

    //do unbind of all elements
    console.log("Doing unbind");
    $("form.h-iapi").unbind("mouseenter");
    $("div.iapi.result").unbind("mouseenter");
    $("form.h-iapi").children().unbind("click focusin focusout");

    var text = localStorage.getItem("program");

    if(text != undefined && text != null )
        localStorage.setItem("program", text+"resultJSON;");


}
$(document).ready(function(){
    initSidebar();

    function initSidebar(){

        $(".container").append("<div id=\"iapisidebar\" style=\"border-width: 1px;border-color: red;border-style: solid;width: 220px;height: 225px;top: 100px;right: 40px;padding: 10px;position: fixed; \"> \
            <button id=\"iapistartrecording\" type=\"button\" class=\"btn btn-default\" onclick='startRecorder();'>Start</button><br /> \
                <button id=\"iapistoprecording\" type=\"button\" class=\"btn btn-default\" onclick='stopRecording();'>Stop</button> \
                <p id=\"iapirecordingstatus\"> Click to link a result</p> \
            </div>");

        var status = localStorage.getItem("isRecording");
        if(status == undefined || status == "no" || status == null){
            $("#iapistoprecording").attr("disabled","disabled");
        }
        else{
            $("#iapistartrecording").attr("disabled","disabled");
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