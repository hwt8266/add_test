/**
 * In charge of interactive with server
 */

// Query areas basic information from server
var getAreaList = function(areaName, remark, minIncrease, maxIncrease, pagenum, callBack){  
  var url = "/PowerLoad/api/areas/area?";
  
  if(areaName != ""){
    url = url + "areaname=" + areaName;
    
    if(remark != ""){
      url = url + "&";
    }
  }
  
  if(remark != ""){
    url = url + "remark=" + remark;
    
    if(minIncrease != ""){
      url = url + "&";
    }
  }
  
  if(minIncrease != ""){
    url = url + "minincrease=" + minIncrease;
    
    if(maxIncrease != ""){
      url = url + "&";
    }
  }
  
  if(maxIncrease != ""){
    url = url + "maxincrease=" + maxIncrease;
  }
  
  url = url + "&pagenum=" + pagenum;

  msgGET(url, callBack);
};


// submit area(add or modify)
var submitAreaInfo = function(area, callBack){
  var url = "/PowerLoad/api/areas/area";
    
  msgPOST(url, area, callBack);
};

// Remove area
var removeAreas = function(areaIds, callBack){
  var url = "/PowerLoad/api/areas/area/delete";
  
  var param = {"ids": areaIds}
  
  msgPOST(url, param, callBack);
};

// Query transformers
var getTrans = function(areaId, tranName, tranType, vLevel, deptName, minCap, maxCap, pageNum, callBack){
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
  
  url = url + "pagenum=" + pageNum;
  
  msgGET(url, callBack);
};

// Modify area's tranformer parameter
var submitTranIncreaseParam = function(tranInModal, callBack){
  var url = "/PowerLoad/api/areas/area/trans";
  
  msgPOST(url, tranInModal, callBack);
};

// Remove transformers from area
var removeTransFromArea = function(areaId, transIds, callBack){
  var url = "/PowerLoad/api/areas/area/trans/delete";
  
  var param = {
    "areaId": areaId,
    "tranIds": transIds
  };
  
  msgPOST(url, param, callBack);
};

// Query raw transformers for area
var getRawTrans = function(areaId, tranName, tranType, vLevel, deptName, minCap, maxCap, pageNum, callBack){
  var url = "/PowerLoad/api/areas/area/rawtrans?";
  
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
  
  url = url + "pagenum=" + pageNum;
  
  msgGET(url, callBack);
};

// Add transformers into area
var addTransToArea = function(areaId, transIds, callBack){
  var url = "/PowerLoad/api/areas/area/trans/add";
  
  var param = {
    "areaId": areaId,
    "tranIds": transIds
  };
  
  msgPOST(url, param, callBack);
};

// Export
var exportAreaForcast = function(areaIds, callback){
  var param = {
    "areaIds": areaIds
  }
  	
  var url = "/PowerLoad/api/areas/area/export";
  
  msgPOST(url, param, callback);
}