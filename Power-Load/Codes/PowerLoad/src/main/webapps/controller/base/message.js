/**
 * Encapsulation Ajax GET and POST method using JSON parameter 
 */

// Ajax POST method
var msgPOST = function(url, param, successCallBack, index, errCallBack){
  $.ajax({
    "url": url,
    "contentType":"application/json;charset=utf8",
    "data":JSON.stringify(param),
    "type":"post",
    "success":function(result){
      successCallBack(result, index);
    },
    "error":errCallBack
  });
};

//Ajax GET method
var msgGET = function(url, successCallBack, errCallBack){
  $.ajax({
    "url": url,
    "contentType":"application/json;charset=utf8",
    "type":"get",
    "success":successCallBack,
    "error":errCallBack
  });
};

//Ajax DELETE method
var msgDELETE = function(url, param, successCallBack, errCallBack){
  $.ajax({
    "url": url,
    "contentType":"application/json;charset=utf8",
    "data":JSON.stringify(param),
    "type":"delete",
    "success":successCallBack,
    "error":errCallBack
  });
};