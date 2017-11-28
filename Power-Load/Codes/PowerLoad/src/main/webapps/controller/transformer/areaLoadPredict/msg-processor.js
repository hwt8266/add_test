/**
 * In charge of interactive with server
 */

// Query area menu items
var getAreaMenuItems = function(areaName, callBack){  
  var url = "/PowerLoad/api/areas/areamenuitem";
  
  if(areaName != ""){
    url = url + "?areaname=" + areaName;
  }

  msgGET(url, callBack);
};
 
var getTranInAreaList = function(areaId, tranName, tranType, vLevel, deptName, minCap, maxCap, minLow, maxLow, minHigh, maxHigh, minOver, maxOver, pageNum, callBack){
  var url = "/PowerLoad/api/areas/area/trans?";
  
  if(areaId != ""){
    url = url + "areaid=" + areaId + "&";
  }
  
  if(tranName != ""){
    url = url + "tranname=" + tranName + "&";
  }
  
  if(tranType != ""){
    url = url + "trantype=" + tranType + "&";
  }
  
  if(vLevel != ""){
    url = url + "vlevel=" + vLevel + "&";
  }
  
  if(deptName != ""){
    url = url + "deptname=" + deptName + "&";
  }
  
  if(minCap != ""){
    url = url + "mincap=" + minCap + "&";
  }
  
  if(maxCap != ""){
    url = url + "maxcap=" + maxCap + "&";
  }
  
  if(minLow != ""){
    url = url + "minlow=" + minLow + "&";
  }
  
  if(maxLow != ""){
    url = url + "maxlow=" + maxLow + "&";
  }
  
  if(minHigh != ""){
    url = url + "minhigh=" + minHigh + "&";
  }
  
  if(maxHigh != ""){
    url = url + "maxhigh=" + maxHigh + "&";
  }
  
  if(minOver != ""){
    url = url + "minover=" + minOver + "&";
  }
  
  if(maxOver != ""){
    url = url + "maxover=" + maxOver + "&";
  }
    
  url = url + "pagenum=" + pageNum;
  
  msgGET(url, callBack);
};

// Modify area's tranformer parameter
var submitTranIncreaseParam = function(tranInModal, callBack){
  var url = "/PowerLoad/api/areas/area/trans";
  
  msgPOST(url, tranInModal, callBack);
};

// Get transformer's power load predict data(96 points)
var getPredictData = function(tranId, callBack){
  var url = "/PowerLoad/api/transmgr/powerload/forecast/area?tranid=" + tranId;
  msgGET(url, callBack);
}