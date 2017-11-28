/**
 * 这部分是左侧列表所要发送的请求的函数
 */
//一开始初始化的时候，先查询上来第一二级列表
var queryFirstList = function(type,callBack){
  var param = {"itemType":type};
  var url = "/PowerLoad/api/common/depttree";
  
  msgPOST(url, param, callBack);
}

var loginCallback = function (data){
  if(data=='false'){
    alert("用户名密码不匹配！");
  }else{
    window.location.href="/PowerLoad/views/transformer/historicalLoad.html";
  }
}

var login = function(username, password){
  var param = {"username": username,"password": password};
  var url = "/PowerLoad/api/auth/login";
  msgPOST(url, param, loginCallback);
}

var logoutCallback = function (data){
  window.location.href="/PowerLoad/views/login.html";
}

var logout = function(){
  var param = {};
  var url = "/PowerLoad/api/auth/logout";
  msgPOST(url, param, logoutCallback);
} 

/**
 * 这部分是下方列表所要发送的请求函数
 */

//初始化时查询数据库，获得下方的列表信息
var queryDisplayItem = function(objType, callBack){
  var url = "/PowerLoad/api/common/displayitem?type=" + objType;
  msgGET(url, callBack);
}

//点击保存时，获得的下方列表的更新
var updateDisplayItem = function(param, callBack){
  var url = "/PowerLoad/api/common/displayitem";

  //更新完后报保存要更新和保存的的id
  addIds =[];
  delIds=[];
  msgPOST(url, param, callBack);
}

// Query all transformer types
var getAllTranType = function(callBack){
  var url = "/PowerLoad/api/common/trantype";
  
  msgGET(url, callBack);
}

// Query all v levels
var getAllVLevel = function(callBack){
  var url = "/PowerLoad/api/common/vlevel";
  
  msgGET(url, callBack);
}