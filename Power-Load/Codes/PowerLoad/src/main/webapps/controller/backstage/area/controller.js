/**
 * Controller of areas list page
 */
var rowPerPage;
var areaCache;
var transInAreaCache;
var transForAddCache;
var selectedAreaId;
var selectedAreaName;
var areaInModal;
var tranInModal;
var checkedArea;
var checkedTranInArea;
var checkedTranForAdd;

// Initialize global var
var initVar = function(){
  rowPerPage = 5;
  areaCache = "";
  transInAreaCache = "";
  transForAddCache = "";
  selectedAreaId = 0;
  selectedAreaName = "";
  areaInModal = {
    "id": "",
    "name": "",
    "increase": "",
    "remark": ""
  };
  tranInModal = {
    "areaId": "",
    "tranId": "",
    "increase": ""
  };
  checkedArea = new Array();
  checkedTranInArea = new Array();
  checkedTranForAdd = new Array();
}

// Initialize page
$(document).ready(function(){
  // Initial global var
  initVar();
	
	// Fill transformer type and v level filter
  queryTranType();
  queryVLevel();
	
  // Query and fill the area tabe
  queryAreaList(1);
  
  // Bind action event
  $("#div-filter").on('shown.bs.collapse',function(){
	  switchArrowUp($("#icon-div-filter"));
  });
  $("#div-filter").on('hidden.bs.collapse',function(){
	  switchArrowDown($("#icon-div-filter"));
  });
  $("#div-arealist").on('shown.bs.collapse',function(){
	  switchArrowUp($("#icon-div-arealist"));
  });
  $("#div-arealist").on('hidden.bs.collapse',function(){
	  switchArrowDown($("#icon-div-arealist"));
  });
  $("#div-addtrans-collapse").on('shown.bs.collapse',function(){	  
	  // Adjust div height
	  $("#div-addtrans-collapse").height($("div-tranlist-collapse").height());
	  $("#div-addtrans-well").height($("div-tranlist-well").height());
  });
});

// Clear all filter criterias of area filter
var clearAllAreaFilter = function(){
  $("#filter-area-areaname").val("");
  $("#filter-area-remark").val("");
  $("#filter-area-minincrease").val("");
  $("#filter-area-maxincrease").val("");
};

// Clear all filter criterias of transformers in area
var clearAllTranListFilter = function(){
  $("#filter-transinarea-tranname").val("");
  $("#filter-transinarea-deptname").val("");
  $("#select-tranlist-filter-trantype").val(0);
  $("#select-tranlist-filter-vlevel").val(0);
  $("#filter-transinarea-mincap").val("");
  $("#filter-transinarea-maxcap").val("");
}

// Clear all filter criterias of raw transformers
var clearAllAddTranFilter = function(){
  $("#filter-addtrans-tranname").val("");
  $("#filter-addtrans-deptname").val("");
  $("#select-addtrans-filter-trantype").val(0);
  $("#select-addtrans-filter-vlevel").val(0);
  $("#filter-addtrans-mincap").val("");
  $("#filter-addtrans-maxcap").val("");
}

// Hide bottom div(transformers in area and transformers for area)
var hideBottomDiv = function(){
  $("#div-tranlist").addClass("hidden");
  $("#div-addtrans").addClass("hidden");
}

// Hide add transformer div
var hideAddDiv = function(){
  // Hide add transformer div
  $("#div-addtrans").addClass("hidden");
	
  // Resize transformer list div to full width
  $("#div-tranlist").attr("class", "col-xs-12");
};

// Area table check all action
var areaCheckAllAction = function(param){
  // Empty array
  checkedArea.splice(0,checkedArea.length);
  setCheck($("#area-table tr:gt(0) input"), false);
  
  if(param.checked){
    for(var i=0;i<areaCache.allIds.length;i++){
      checkedArea.push(areaCache.allIds[i]);
    }
    
    setCheck($("#area-table tr:gt(0) input"), true);
  }
  
  if(checkedArea.length > 0){
    $("#btn-area-delete").removeAttr("disabled");
  }else{
    $("#btn-area-delete").attr("disabled", "disabled");
  }
}

// Transformers in area table check all action
var transInAreaCheckAllAction = function(param){
  // Empty array
  checkedTranInArea.splice(0,checkedTranInArea.length);
  setCheck($("#transinarea-table tr:gt(0) input"), false);
  
  if(param.checked){
    for(var i = 0; i < transInAreaCache.allIds.length; i++){
      checkedTranInArea.push(transInAreaCache.allIds[i]);
    }
    
    setCheck($("#transinarea-table tr:gt(0) input"), true);
  }
  
  if(checkedTranInArea.length > 0){
    $("#btn-traninarea-delete").removeAttr("disabled");
  }else{
    $("#btn-traninarea-delete").attr("disabled", "disabled");
  }
}

// Transformers to be added to area table check all action
var transToBeAddCheckAllAction = function(param){
  // Empty array
  checkedTranForAdd.splice(0,checkedTranForAdd.length);
  setCheck($("#transforadd-table tr:gt(0) input"), false);
  
  if(param.checked){
    for(var i = 0; i < transForAddCache.allIds.length; i++){
      checkedTranForAdd.push(transForAddCache.allIds[i]);
    }
    
    setCheck($("#transforadd-table tr:gt(0) input"), true);
  }
  
  if(checkedTranForAdd.length > 0){
    $("#btn-tranforadd-add").removeAttr("disabled");
  }else{
    $("#btn-tranforadd-add").attr("disabled", "disabled");
  }
}

// Check or Uncheck all checkboxs in table
var setCheck = function(chkboxs, checkStatus){
  chkboxs.each(function(){
    this.checked = checkStatus;
  });
}

// Area table row checkbox select change action
var tableRowCheckAction = function(checkbox, cachedIds, rowId, delBtnId){
  if(checkbox.checked){
    cachedIds.push(rowId);
  } else {
    // Remove id from cache
    var index = 0;
    
    for(;index < cachedIds.length; index++){
      if(rowId == cachedIds[index]){
        break;
      }
    }
    
    cachedIds.splice(index, 1);
  }
  var a = "#" + delBtnId;
  
  if(cachedIds.length > 0){
    $("#" + delBtnId).removeAttr("disabled");
  }else{
    $("#" + delBtnId).attr("disabled", "disabled");
  }
}

// find ID in ID list
var findID = function(idInRow, idList){
  var result = false;
  
  for(var i = 0; i < idList.length; i++){
    if(idInRow == idList[i]){
      result = true;
      
      break;
    }
  }
  
  return result;
}

// Query areas basic information
var queryAreaList = function(pagenum){
  var areaName = $("#filter-area-areaname").val();
  var remark = $("#filter-area-remark").val();
  var minIncrease = $("#filter-area-minincrease").val();
  var maxIncrease = $("#filter-area-maxincrease").val();
  
  getAreaList(areaName, remark, minIncrease, maxIncrease, pagenum, cacheAreas);
};

// Cache area data by server data
var cacheAreas = function(data){
  areaCache = data;
  hideBottomDiv();
  updataAreaTable(areaCache.currentPage, areaCache.totalPage);
};

// Update area table by page number
var updataAreaTable = function(pageNum, totalPage){
  $("#area-table tr:gt(0)").remove();
  
  var areasInTable = areaCache.rowData;
  
  for(var i = 0; i < areasInTable.length; i++){  	
    var newRow = "<tr onclick=\"areaTableSelectAction(" + areasInTable[i].id + ", \'" + areasInTable[i].name 
               + "\')\"><td><input type=\"checkbox\"" + "onclick=\"tableRowCheckAction(this, checkedArea, " + areasInTable[i].id + ", 'btn-area-delete')\"";
    
    if(findID(areasInTable[i].id, checkedArea)){
      newRow = newRow + " checked = \"checked\"";
    }
    
    newRow = newRow + "/></td><td>" 
               + areasInTable[i].name + "</td><td>" + areasInTable[i].increase + "%</td><td>" 
               + areasInTable[i].remark + "</td><td>" 
               + "<a class=\"glyphicon glyphicon-edit atip\" data-toggle=\"tooltip\" data-placement=\"top\" data-original-title=\"修改\" style=\"color:#00A6AD; font-size:20px;\" " 
               + "onclick=\"openAreaModal(" + areasInTable[i].id + ", \'" + areasInTable[i].name + "\', " + areasInTable[i].increase + ", \'" + areasInTable[i].remark + "\')\"></a>" 
               + "</td></tr>";
    $("#area-table tr:last").after(newRow);
  }
  
  // Remove all li object first
  $("#pagination-area li").remove();
  
  // Assemble page navigator
  $("#pagination-area").append('<li><a href="javascript:void(0)" onclick="queryAreaList('+ (pageNum - 1) + ')">&laquo;</a></li>');
  
  if(pageNum - 1 > 1){
    $("#pagination-area").append('<li><a href="javascript:void(0)">...</a></li>');
  }

  if(pageNum - 1 > 0){
    $("#pagination-area").append('<li><a href="javascript:void(0)" onclick="queryAreaList('+ (pageNum-1) + ')">' + (pageNum - 1) + '</a></li>');
  }
  
  $("#pagination-area").append('<li class="active"><a href="javascript:void(0)">' + pageNum + '</a></li>');
  
  if(pageNum + 1 <= totalPage){
    $("#pagination-area").append('<li><a href="javascript:void(0)" onclick="queryAreaList(' + (pageNum + 1) + ')">'+ (pageNum + 1) +'</a></li>');
  }
  
  if(pageNum + 1 < totalPage){
	  $("#pagination-area").append('<li><a href="javascript:void(0)">...</a></li>');
  }
  
  $("#pagination-area").append('<li><a href="javascript:void(0)" onclick="queryAreaList(' + (pageNum + 1) + ')">&raquo;</a></li>');
  
  if(pageNum == 1){
    $("#pagination-area li:eq(0)").addClass("disabled");
    $("#pagination-area li:eq(0)").html('<a href="javascript:void(0)">&laquo;</a>');
  }
  
  if(pageNum == totalPage || totalPage==0){
    $("#pagination-area li:last").addClass("disabled");
    $("#pagination-area li:last").html('<a href="javascript:void(0)">&raquo;</a>');
  }
};

// Open area modal for new or  modify
var openAreaModal = function(areaId, areaName, areaIncrease, areaRemark){
  // Clear modal model
  areaInModal.id = "";
  areaInModal.name = "";
  areaInModal.increase = 0;
  areaInModal.remark = "";
  
  // Clear modal panel item
  $("#input-area-name").val("");
  $("#input-area-increase").val("");
  $("#input-area-remark").val("");
  
  $("#div-check-label").addClass("hidden");
  
  if (typeof(areaId) == "undefined"){
  	// Create new area should hidden transformers list div and add transformer div
  	selectedAreaId = "";
  	selectedAreaName = "";
  	
  	// Set modal title
    $("#title-areabasic").text("新建区域");
  } else{
    // Set modal title
    $("#title-areabasic").text("修改区域");
    
    // Update modal and modal's model with area information
    $("#input-area-name").val(areaName);
    $("#input-area-increase").val(areaIncrease);
    
    areaInModal.id = areaId;
    areaInModal.name = areaName;
    areaInModal.increase = areaIncrease;
    
    if(typeof(areaRemark) != "undefined"){
      $("#input-area-remark").val(areaRemark);
      areaInModal.remark = areaRemark;
    }
  }
  
  $('#modal-areabasic').modal("show");
}

// Submit new area or modify area
var submitArea = function(){
  if($("#input-area-increase").val() == ""){
    $("#div-check-label").removeClass("hidden");
  }else{
    // Collect area basic information from modal
    areaInModal.name = $("#input-area-name").val();
    areaInModal.increase = $("#input-area-increase").val();
    areaInModal.remark = $("#input-area-remark").val();
    areaInModal.id = selectedAreaId;
  
    // Dispatch message
    submitAreaInfo(areaInModal, submitAreaCallBack);
  }
}

// Call back of submit area(add or modify)
var submitAreaCallBack = function(operResult){  
  if(operResult != "succeed"){
    $("#operresult-areamodal").val(operResult);
  } else {
  	$('#modal-areabasic').modal("hide");
    
    if(selectedAreaId == ""){
      $("#div-tranlist").addClass("hidden");
      $("#div-addtrans").addClass("hidden");
    }
    
    queryAreaList(1);
  }
}

// Open area modal for new or  modify
var openTranParamModal = function(tranId, tranName, tranIncrease){
  // Clear modal model
  tranInModal.areaId = selectedAreaId;
  tranInModal.tranId = tranId;
  tranInModal.increase = tranIncrease;
  
  // Clear modal panel item
  $("#input-tran-increase").val("");
  
  $("#title-tran-increase").text("修改【" + tranName + "】增长率参数");
  
  // Update modal and modal's model with transformer information
  $("#input-tran-increase").val(tranIncrease);
  
  $("#modal-tran-increase").modal("show");
}

// Submit modification of transformer's increasement parameter
var submitTranParam = function(){
  tranInModal.increase = $("#input-tran-increase").val();
  
  // Dispatch message
  submitTranIncreaseParam(tranInModal, submitTranParamCallBack);
}

// Call back of submit transformer parameter
var submitTranParamCallBack = function(operResult){  
  if(operResult != "succeed"){
    $("#operresult-tranmodal").val(operResult);
  } else {
  	$("#modal-tran-increase").modal("hide");
    
    queryTransInArea(1);
  }
}

// Remove area
var deleteArea = function(){
  removeAreas(checkedArea, deleteAreaCallBack);
}

// Call back for delete area(s)
var deleteAreaCallBack = function(operResult){
  queryAreaList(1);
  hideBottomDiv();
}

// Export area transformer power load forecast report
var exportReport = function(){
  exportAreaForcast(checkedArea, exportCallback);
}

// Export call back
var exportCallback = function(data){
  if(data == "failed"){
    Alert("导出失败！");
  }else{
    window.open(data);
  }
}

// Area table row select action
var areaTableSelectAction = function(areaId, areaName){
  selectedAreaId = areaId;
  selectedAreaName = areaName;
  
  $("#title-tranlist").text("【" + selectedAreaName + "】下属变压器");
  $("#title-addtrans").text("选择加入【" + selectedAreaName + "】的变压器");
  
  // Hide add transformers div
  $("#div-addtrans").addClass("hidden");
  
  // Show transformer list div and resize it to full width
  $("#div-tranlist").removeClass("hidden");
  $("#div-tranlist").attr("class", "col-xs-12");
  
  queryTransInArea(1);
}

// Query transformer type
var queryTranType = function(){
  getAllTranType(callBackFillTranType);
}

// Call back for query transformer type
var callBackFillTranType = function(data){
  $("#select-tranlist-filter-trantype option:gt(0)").remove();
  $("#select-addtrans-filter-trantype option:gt(0)").remove();
  
  for(var i = 0; i < data.length; i++){  	
    var opt = "<option value=\"" + data[i].type + "\">" + data[i].name + "</option>";
    
    $("#select-tranlist-filter-trantype option:last").after(opt);
    $("#select-addtrans-filter-trantype option:last").after(opt);
  }
}

// Query v levels
var queryVLevel = function(){
  getAllVLevel(callBackFillVLevel);
}

// Call back for query transformer type
var callBackFillVLevel = function(data){  
  for(var i = 0; i < data.length; i++){  	
    var opt = "<option value=\"" + data[i].id + "\">" + data[i].descrb + "</option>";
    
    $("#select-tranlist-filter-vlevel option:last").after(opt);
    $("#select-addtrans-filter-vlevel option:last").after(opt);
  }
}

// List transformers included by selected area
var queryTransInArea = function(pageNum){
  var tranName = $("#filter-transinarea-tranname").val();
  var deptName = $("#filter-transinarea-deptname").val();
  var tranType = $("#select-tranlist-filter-trantype").val();
  var vLevel = $("#select-tranlist-filter-vlevel").val();
  var minCap = $("#filter-transinarea-mincap").val();
  var maxCap = $("#filter-transinarea-maxcap").val();
	
  getTrans(selectedAreaId, tranName, tranType, vLevel, deptName, minCap, maxCap, pageNum, queryTransInAreaCallBack);
}

// Call back of list transformers
var queryTransInAreaCallBack = function(data){
	transInAreaCache = data;
  $("#div-tranlist-collapse").collapse("show");
  updateTransInAreaTable(transInAreaCache.currentPage, transInAreaCache.totalPage);
}

// Update transformers in area table by page number
var updateTransInAreaTable = function(pageNum, totalPage){
  $("#transinarea-table tr:gt(0)").remove();
  
  var transInArea = transInAreaCache.rowData;  
  
  for(var i = 0; i < transInArea.length; i++){
    var newRow = "<tr><td><input type=\"checkbox\"" + "onclick=\"tableRowCheckAction(this, checkedTranInArea, " + transInArea[i].id + ", 'btn-traninarea-delete')\"";
    
    if(findID(transInArea[i].id, checkedTranInArea)){
      newRow = newRow + " checked = \"checked\"";
    }
    
    newRow = newRow + "/></td><td>" 
               + transInArea[i].name + "</td><td>" 
               + transInArea[i].type + "</td><td>" 
               + transInArea[i].vLevel + "</td><td>" 
               + transInArea[i].capacity + "千瓦</td><td>" 
               + transInArea[i].increase + "%</td><td>"
               + transInArea[i].deptName + "</td><td>"
               + "<a class=\"glyphicon glyphicon-edit atip\" data-toggle=\"tooltip\" data-placement=\"top\" data-original-title=\"修改\" style=\"color:#00A6AD; font-size:20px;\" " 
               + "onclick=\"openTranParamModal(" + transInArea[i].id + ", \'" + transInArea[i].name + "\', " + transInArea[i].increase + ")\"></a>" 
               + "</td></tr>";
    $("#transinarea-table tr:last").after(newRow);
  }
  
  // Remove all li object first
  $("#pagination-transinarea li").remove();
  
  // Assemble page navigator
  $("#pagination-transinarea").append('<li><a href="javascript:void(0)" onclick="queryTransInArea('+ (pageNum - 1) + ')">&laquo;</a></li>');
  
  if(pageNum - 1 > 1){
    $("#pagination-transinarea").append('<li><a href="javascript:void(0)">...</a></li>');
  }

  if(pageNum - 1 > 0){
    $("#pagination-transinarea").append('<li><a href="javascript:void(0)" onclick="queryTransInArea('+ (pageNum-1) + ')">' + (pageNum - 1) + '</a></li>');
  }
  
  $("#pagination-transinarea").append('<li class="active"><a href="javascript:void(0)">' + pageNum + '</a></li>');
  
  if(pageNum + 1 <= totalPage){
    $("#pagination-transinarea").append('<li><a href="javascript:void(0)" onclick="queryTransInArea(' + (pageNum + 1) + ')">'+ (pageNum + 1) +'</a></li>');
  }
  
  if(pageNum + 1 < totalPage){
	  $("#pagination-transinarea").append('<li><a href="javascript:void(0)">...</a></li>');
  }
  
  $("#pagination-transinarea").append('<li><a href="javascript:void(0)" onclick="queryTransInArea(' + (pageNum + 1) + ')">&raquo;</a></li>');
  
  if(pageNum == 1){
    $("#pagination-transinarea li:eq(0)").addClass("disabled");
    $("#pagination-transinarea li:eq(0)").html('<a href="javascript:void(0)">&laquo;</a>');
  }
  
  if(pageNum == totalPage || totalPage==0){
    $("#pagination-transinarea li:last").addClass("disabled");
    $("#pagination-transinarea li:last").html('<a href="javascript:void(0)">&raquo;</a>');
  }
};

// Show add transformer div
var showAddTransAction = function(){
  // Resize transformer list div to full width
  $("#div-tranlist").attr("class", "col-xs-6");
  
  // Show add transformer div
  $("#div-addtrans").removeClass("hidden");
  $("#div-addtrans-collapse").collapse("show");
  
  // Clear transformers for add cache
  transForAdd = "";
  
  // Clear transformers for add table
  $("#transforadd-table tr:gt(0)").remove();
  
  // Remove all li from pagination
  $("#pagination-transforadd li").remove();
}

// List transformers for adds
var queryTransForAdd = function(pageNum){
  var tranName = $("#filter-addtrans-tranname").val();
  var deptName = $("#filter-addtrans-deptname").val();
  var tranType = $("#select-addtrans-filter-trantype").val();
  var vLevel = $("#select-addtrans-filter-vlevel").val();
  var minCap = $("#filter-addtrans-mincap").val();
  var maxCap = $("#filter-addtrans-maxcap").val();
	
  getRawTrans(selectedAreaId, tranName, tranType, vLevel, deptName, minCap, maxCap, pageNum, queryTransForAddCallBack);
}

// Call back of transformers for add
var queryTransForAddCallBack = function(data){
	transForAddCache = data;
  updateTransForAddTable(transForAddCache.currentPage, transForAddCache.totalPage);
}

// Update transformers for add table by page number
var updateTransForAddTable = function(pageNum, totalPage){
  $("#transforadd-table tr:gt(0)").remove();
  
  for(var i = 0; i < transForAddCache.rowData.length; i++){
    var newRow = "<tr><td><input type=\"checkbox\"" + "onclick=\"tableRowCheckAction(this, checkedTranForAdd, " + transForAddCache.rowData[i].id + ", 'btn-tranforadd-add')\"";
    
    if(findID(transForAddCache.rowData[i].id, checkedTranForAdd)){
      newRow = newRow + " checked = \"checked\"";
    }
    
    newRow = newRow + "/></td><td>" 
               + transForAddCache.rowData[i].name + "</td><td>" 
               + transForAddCache.rowData[i].type + "</td><td>" 
               + transForAddCache.rowData[i].vLevel + "</td><td>" 
               + transForAddCache.rowData[i].capacity + "千瓦</td><td>" 
               + transForAddCache.rowData[i].deptName + "</td></tr>";
    $("#transforadd-table tr:last").after(newRow);
  }
  
  // Remove all li object first
  $("#pagination-transforadd li").remove();
  
  // Assemble page navigator
  $("#pagination-transforadd").append('<li><a href="javascript:void(0)" onclick="queryTransForAdd('+ (pageNum - 1) + ')">&laquo;</a></li>');
  
  if(pageNum - 1 > 1){
    $("#pagination-transforadd").append('<li><a href="javascript:void(0)">...</a></li>');
  }

  if(pageNum - 1 > 0){
    $("#pagination-transforadd").append('<li><a href="javascript:void(0)" onclick="queryTransForAdd('+ (pageNum-1) + ')">' + (pageNum - 1) + '</a></li>');
  }
  
  $("#pagination-transforadd").append('<li class="active"><a href="javascript:void(0)">' + pageNum + '</a></li>');
  
  if(pageNum + 1 <= totalPage){
    $("#pagination-transforadd").append('<li><a href="javascript:void(0)" onclick="queryTransForAdd(' + (pageNum + 1) + ')">'+ (pageNum + 1) +'</a></li>');
  }
  
  if(pageNum + 1 < totalPage){
	  $("#pagination-transforadd").append('<li><a href="javascript:void(0)">...</a></li>');
  }
  
  $("#pagination-transforadd").append('<li><a href="javascript:void(0)" onclick="queryTransForAdd(' + (pageNum + 1) + ')">&raquo;</a></li>');
  
  if(pageNum == 1){
    $("#pagination-transforadd li:eq(0)").addClass("disabled");
    $("#pagination-transforadd li:eq(0)").html('<a href="javascript:void(0)">&laquo;</a>');
  }
  
  if(pageNum == totalPage || totalPage==0){
    $("#pagination-transforadd li:last").addClass("disabled");
    $("#pagination-transforadd li:last").html('<a href="javascript:void(0)">&raquo;</a>');
  }
};

// Remove selected transformer from area
var removeTranFromArea = function(){
  removeTransFromArea(selectedAreaId, checkedTranInArea, callBackForRemoveTransFromArea);
}

// Call back for remove transformers from area
var callBackForRemoveTransFromArea = function(operResult){
  queryTransInArea(1);
}

// Add selected transformer(s) to area
var addTranToArea = function(){
  addTransToArea(selectedAreaId, checkedTranForAdd, addTransToAreaCallBack);
}

// Call back of add transformer(s) to area
var addTransToAreaCallBack = function(operResult){  
  // Refresh data of transformers in area table
  checkedTranInArea.splice(0,checkedTranInArea.length);
  queryTransInArea(1);
  $("#btn-traninarea-delete").attr("disabled", "disabled");
  
  // Refresh data of transformers for add
  checkedTranForAdd.splice(0,checkedTranForAdd.length);
  queryTransForAdd(1);
  $("#btn-transforadd-add").attr("disabled", "disabled");
}