// If array contains value
var arrayContain = function(array, value){
  for(var i = 0; i < array.length; i++){
    if(array[i] == value){
      return i;
    }
  }
  return -1;
}

var colorpool=['#2B5593','#F76B7C','#0074CC','#F9C463','#068CEF','#F9E25A','#04B5F2','#D46488','#24C5EE','#FA85C0','#43D5EA','#C880CE','#62E4E5','#9678DC','#82F4E1','#8D569F','#36C684','#92D232','#B6EASC','#D8E65B'];

// Swith the arrow icon of collapsable div
var switchArrowUp = function(dom){
  dom.removeClass("glyphicon-menu-down");
  dom.addClass("glyphicon-menu-up");
}

// Swith the arrow icon of collapsable div
var switchArrowDown = function(dom){
  dom.removeClass("glyphicon-menu-up");
  dom.addClass("glyphicon-menu-down");
}

function full(idpannel){
  $('#'+idpannel).css('width','100%');
  $('#'+idpannel).css('height','100%');
  var de = document.getElementById(idpannel);
  if (de.requestFullscreen) {
    de.requestFullscreen();
  } else if (de.mozRequestFullScreen) {
    de.mozRequestFullScreen();
  } else if (de.webkitRequestFullScreen) {
    de.webkitRequestFullScreen();
  }
}