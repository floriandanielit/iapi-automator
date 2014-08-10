/**
 * Created by les on 10/07/14.
 */
/*
this is actually made for demo only
* */
/**
 * Created by les on 12/05/14.
 */
function init() {

    $('.basic').click(function (e) {
        $('#basic-modal-content').modal({onOpen:openModal, onClose:modalClose});
        return false;
    });
};

function modalClose(dialog){

    $.modal.close();
}
function openModal(dialog){

    var program = localStorage.getItem("program");
    console.log(program)
    var del=";";
    var elements = program.split(del);


    $('#basic-modal-content').append("<form action=''> <fieldset>");
    elements.forEach(function(entry){
        if(entry.indexOf("result") > -1 ){
            $('#basic-modal-content').append(""+entry+" <input type=\"radio\" value=\""+entry+"\"/> <br/>");
        }

    });
    $('#basic-modal-content').append("</form></fieldset>");

    dialog.data.show();
    dialog.container.show();
    dialog.overlay.fadeIn('fast');
}