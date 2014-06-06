/**
 * Created by les on 30/05/14.
 */


/**
 * JSON Structure for parameters
 *
 * [{"id":"id1", "value":"value1"},{"id":"id2", "value":"value2"}...]
 * **/
function fillForm(program, paramsJSON){

    //var arrjson = JSON.parse(paramsJSON);
    var toReplace = new Array();

    //All the Json objects are mapped into a dictionary of the form:<id, value>
    $(jQuery.parseJSON(JSON.stringify(paramsJSON))).each(function() {
       toReplace[this.id] = this.value;
    });

    var result = "";
    program.split(";").forEach(
        function(entry){
            var pattern =/\$(\w+)/g;
            if(getOption(entry) == "fill" && entry.indexOf("$dynamic") > -1){
                entry = entry.replace(pattern,toReplace[getIdentifier(entry)]);
            }
            if(entry != " ")
                result+=entry+";";

    });

    var server = "http://localhost:8080/web_war_exploded/Compile";

    $.post(server,{"program" : result}, function(data){
       console.log(data)
    });

    //The identifier is always in the last position
    function getIdentifier(command){
        var results = command.split(" ~");
        return results[results.length-1];
    }
    function getOption(command){
        var results = command.split(" ~");
        return results[0].replace(" ","");
    }


}