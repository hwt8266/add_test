/**
 * Drawer action controller
 */
$(function(){
  var panel = $(".drawerPanel-sc"); 
  var w = panel.outerWidth();
  var flag = false;

  $("#swicher").bind("click", function(){
    if(flag){
      panel.animate({"width" : "0px"},200);
      flag = false;
    }else{
      panel.css("width","0px").show();
      panel.animate({"width" : w + "px"},300);
      flag = true;
    }
  });
});

function deletetab(id){
  $('#'+id).remove();
}

function getdate(){
   var mydate = new Date();
   var str = "" + mydate.getFullYear() + ".";
   str += (mydate.getMonth()+1) + ".";
   str += mydate.getDate();
   return str;
}

$(document).ready(function() {
  $('#date').append(getdate());
});
function randomData(num) {
  var arrays = [];
  for(var i=0;i<num;i++){
    arrays.push(Math.random()*300);
  }
  var value=JSON.stringify(arrays);
  var obj=eval(value);
    return obj;
}