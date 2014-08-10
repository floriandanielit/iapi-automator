/**
 * Created by les on 12/05/14.
 */
jQuery(function ($) {

    $('.basic').click(function (e) {
        $('#basic-modal-content').modal({onOpen:precompileScript, onClose:modalClose});
        return false;
    });
});

function modalClose(dialog){

    var newInputs = $("#basic-modal-content input:text");
    var inputIndex = 0;
    var text = $("#programContainer").val();
    var del = ";";

    var elements = text.split(del);
    var pattern =/\$(\w+)/g;
    var result ="";

    elements.forEach(function(entry){
        if(entry.indexOf("$dynamic") > -1 && inputIndex < newInputs.length){
            entry = entry.replace(pattern,newInputs[inputIndex].value);
            console.log(entry)
            inputIndex++;
        }
        if(entry != " ")
            result+=entry+";";
    });
    $.modal.close();
    $("#programContainer").val(result);
    $("#formCompiler").submit();
}
function precompileScript(dialog){
    var text = $("#programContainer").val();
    var del = ";";

    var elements = text.split(del);
    //var re = new RegExp("~(\\w+)", "g");
    var re = /~(\w+)/g;

    elements.forEach(function(entry){
        if(entry.indexOf("$dynamic") > -1){
            var item_id;
            var res = re.exec(entry);

            while(res != null){
                item_id=res[1];
                res = re.exec(entry);
            }
            $('#basic-modal-content').append("<p> "+item_id+" <input type=\"text\" ></input> </p>");
        }

    })
    dialog.data.show();
    dialog.container.show();
    dialog.overlay.fadeIn('fast');
}