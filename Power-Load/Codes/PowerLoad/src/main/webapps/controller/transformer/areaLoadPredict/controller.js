/**
 * Controller of meter-distribution page
 */
// Define chart

var rowPerPage;
var selectedAreaId;
var selectedTranId;
var selectedTranName;
var tranInModal;
var chartDivCollapseHerf;
var chartDivFullHerf;
var isChartOpen;

// Initialize global var
var initVar = function(){
  rowPerPage = 5;
  selectedAreaId = 0;
  selectedTranId = 0;
  selectedTranName = "";
  tranInModal = {
    "areaId": "",
    "tranId": "",
    "increase": ""
  };
  isChartOpen = false;
  chartDivCollapseHerf = "<a id=\"ahref-chart-collapse\" data-toggle=\"collapse\" href=\"#div-chart\" data-parent=\"#div-chart-border\">"
                       + "<span id=\"icon-div-chart-collapse\" class=\"glyphicon glyphicon-menu-up\" style=\"color:#FFFFFF; float:right;\"></span></a>";
  chartDivFullHerf = "<a id=\"ahref-chart-full\" href=\"javascript:full(\'content-linechart\')\">"
                   + "<span id=\"icon-div-chart-full\" class=\"glyphicon glyphicon-resize-full\" style=\"color:#FFFFFF; float:right; margin-right:10px;\"></span></a>";
}

var chartItem = $("#accordion").html();

// Initialize echart instance by DOM
$(document).ready(function(){
	// Initial global var
  initVar();
  
  // Fill transformer type and v level filter
  queryTranType();
  queryVLevel();
  
  // Query area menu items
  queryAreaMenuItemList();
  
  document.addEventListener("webkitfullscreenchange", function() {
    if(document.webkitIsFullScreen){
      $("#line-chart").css('width','100%');
      $("#line-chart").css('height',($(window).height()-20)+'px');
    }else{
      $("#line-chart").css('width','100%');
      $("#line-chart").height(320);
    }
    
    resize();
  }, false);
  
  // Bind action event
  $("#div-filter").on('shown.bs.collapse',function(){
	  switchArrowUp($("#icon-div-filter"));
  });
  $("#div-filter").on('hidden.bs.collapse',function(){
	  switchArrowDown($("#icon-div-filter"));
  });
  $("#div-tranlist").on('shown.bs.collapse',function(){
	  switchArrowUp($("#icon-div-tranlist-collapse"));
  });
  $("#div-tranlist").on('hidden.bs.collapse',function(){
	  switchArrowDown($("#icon-div-tranlist-collapse"));
  });
  $("#div-chart").on('shown.bs.collapse',function(){
	  switchArrowUp($("#icon-div-chart-collapse"));
  });
  $("#div-chart").on('hidden.bs.collapse',function(){
	  switchArrowDown($("#icon-div-chart-collapse"));
  });
});

// Query transformer type
var queryTranType = function(){
  getAllTranType(callBackFillTranType);
}

// Call back for query transformer type
var callBackFillTranType = function(data){  
  for(var i = 0; i < data.length; i++){  	
    var opt = "<option value=\"" + data[i].type + "\">" + data[i].name + "</option>";
    
    $("#select-filter-trantype option:last").after(opt);
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
    
    $("#select-filter-vlevel option:last").after(opt);
  }
}

// Query area menu items
var queryAreaMenuItemList = function(){
  var areaName = $("#searchcontent").val();
	
  getAreaMenuItems(areaName, queryAreaItemCallBack);
};

// Call back for query areas
var queryAreaItemCallBack = function(data){
  if(isChartOpen){
  	$("#div-chart").removeClass("in");
    $("#ahref-chart-collapse").remove();
	  $("#ahref-chart-full").remove();
	  
	  isChartOpen = false;
  }
	
  $("#area-menu a:gt(0)").remove();
  
  var firstRow = "<a href=\"javascript:void(0)\" class=\"list-group-item active\" onclick=\"menuSelectAction(" + data[0].areaId + ", this)\"><span class=\"badge\">" + data[0].tranCount + "</span>" + data[0].areaName+ "</a>";
  
  $("#area-menu a:last").after(firstRow);
  
  for(var i = 1; i < data.length; i++){  	
    var row = "<a href=\"javascript:void(0)\" class=\"list-group-item\" onclick=\"menuSelectAction(" + data[i].areaId + ", this)\"><span class=\"badge\">" + data[i].tranCount + "</span>" + data[i].areaName + "</a>";
    $("#area-menu a:last").after(row);
  }
  
  selectedAreaId = data[0].areaId;
  queryTranInAreaList(1);
}

// Menu select action
var menuSelectAction = function(areaId, menuItem){
  $("#area-menu a:gt(0)").removeClass("active");
  $(menuItem).addClass("active");
  
  selectedAreaId = areaId;
  queryTranInAreaList(1);
}

// Clear all filter criterias of transformers table
var clearAllTranInAreaFilter = function(){
  $("#filter-tranname").val("");
  $("#filter-deptname").val("");
  $("#select-filter-trantype").val(0);
  $("#select-filter-vlevel").val(0);
  $("#filter-mincap").val("");
  $("#filter-maxcap").val("");
  $("#filter-minlow").val("");
  $("#filter-maxlow").val("");
  $("#filter-minhigh").val("");
  $("#filter-maxhigh").val("");
  $("#filter-minover").val("");
  $("#filter-maxover").val("");
}

// Query transformers in certain area
var queryTranInAreaList = function(pageNum){
  var tranName = $("#filter-tranname").val();
  var deptName = $("#filter-deptname").val();
  var tranType = $("#select-filter-trantype").val();
  var vLevel = $("#select-filter-vlevel").val();
  var minCap = $("#filter-mincap").val();
  var maxCap = $("#filter-maxcap").val();
  var minLow = $("#filter-minlow").val();
  var maxLow = $("#filter-maxlow").val();
  var minHigh = $("#filter-minhigh").val();
  var maxHigh = $("#filter-maxhigh").val();
  var minOver = $("#filter-minover").val();
  var maxOver = $("#filter-maxover").val();
	
  getTranInAreaList(selectedAreaId, tranName, tranType, vLevel, deptName, minCap, maxCap, minLow, maxLow, minHigh, maxHigh, minOver, maxOver, pageNum, queryTransInAreaCallBack);
};

// Call back for query transformers in certain area
var queryTransInAreaCallBack = function(data){
  if(isChartOpen){
  	$("#title-linechart").text("选择变压器查看负载率预测曲线");  
  	$("#div-chart").removeClass("in");
    $("#ahref-chart-collapse").remove();
	  $("#ahref-chart-full").remove();
	  
	  isChartOpen = false;
  }
  
  $("#tran-table tr:gt(0)").remove(); 
  
  for(var i = 0; i < data.rowData.length; i++){
    var newRow = "<tr onclick=\"tranTableSelectAction(" + data.rowData[i].id + ", \'" + data.rowData[i].name + "\')\"><td>"
                           + data.rowData[i].name + "</td><td>" 
                           + data.rowData[i].type + "</td><td>" 
                           + data.rowData[i].vLevel + "千伏</td><td>" 
                           + data.rowData[i].capacity + "千瓦</td><td>" 
                           + data.rowData[i].increase + "%</td><td>"
                           + data.rowData[i].lowPeriod + "分钟</td><td>"
                           + data.rowData[i].heavyPeriod + "分钟</td><td>"
                           + data.rowData[i].overPeriod + "分钟</td><td>"
                           + data.rowData[i].deptName  + "</td><td>"
                           + "<a class=\"glyphicon glyphicon-edit atip\" data-toggle=\"tooltip\" data-placement=\"top\" data-original-title=\"修改\" style=\"color:#00A6AD; font-size:20px;\" " 
               + "onclick=\"openTranParamModal(" + data.rowData[i].id + ", \'" + data.rowData[i].name + "\', " + data.rowData[i].increase + ")\"></a>" 
                           + "</td></tr>";
    $("#tran-table tr:last").after(newRow);
  }
  
  var pageNum = data.currentPage;
  var totalPage = data.totalPage;
  
  // Remove all li object first
  $("#pagination-tran li").remove();
  
  // Assemble page navigator
  $("#pagination-tran").append('<li><a href="javascript:void(0)" onclick="queryTranInAreaList('+ (pageNum - 1) + ')">&laquo;</a></li>');
  
  if(pageNum - 1 > 1){
    $("#pagination-tran").append('<li><a href="javascript:void(0)">...</a></li>');
  }

  if(pageNum - 1 > 0){
    $("#pagination-tran").append('<li><a href="javascript:void(0)" onclick="queryTranInAreaList('+ (pageNum-1) + ')">' + (pageNum - 1) + '</a></li>');
  }
  
  $("#pagination-tran").append('<li class="active"><a href="javascript:void(0)">' + pageNum + '</a></li>');
  
  if(pageNum + 1 <= totalPage){
    $("#pagination-tran").append('<li><a href="javascript:void(0)" onclick="queryTranInAreaList(' + (pageNum + 1) + ')">'+ (pageNum + 1) +'</a></li>');
  }
  
  if(pageNum + 1 < totalPage){
	  $("#pagination-tran").append('<li><a href="javascript:void(0)">...</a></li>');
  }
  
  $("#pagination-tran").append('<li><a href="javascript:void(0)" onclick="queryTranInAreaList(' + (pageNum + 1) + ')">&raquo;</a></li>');
  
  if(pageNum == 1){
    $("#pagination-tran li:eq(0)").addClass("disabled");
    $("#pagination-tran li:eq(0)").html('<a href="javascript:void(0)">&laquo;</a>');
  }
  
  if(pageNum == totalPage || totalPage==0){
    $("#pagination-tran li:last").addClass("disabled");
    $("#pagination-tran li:last").html('<a href="javascript:void(0)">&raquo;</a>');
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
    
    queryTranInAreaList(1);
  }
}

// Transformer table row select action
var tranTableSelectAction = function(tranId, tranName){
  selectedTranId = tranId;
  selectedTranName = tranName;
  $("#title-linechart").text("加载中...");
  processLine("");  
  queryPredictData(tranId);
}

// Query predict data
var queryPredictData = function(tranId){
  getPredictData(tranId, queryPredictDataCallback);
}

// Call back for query predict data
var queryPredictDataCallback = function(data){
  if(!isChartOpen){
    $("#div-chart").addClass("in");
    $("#h-chart span:last").after(chartDivCollapseHerf);
    $("#h-chart a:last").after(chartDivFullHerf);
    
    isChartOpen = true;
  }  
  
  $("#title-linechart").text("【" + selectedTranName + "】负载率预测曲线");
  
  processLine(data);
}

// handle power load predict line chart
var processLine = function(data){	
  var chart = echarts.init($("#line-chart")[0]);
  var tempLine = lineoption;
  tempLine.series = [];
  tempLine.legend.data.push("预测曲线");
  var seriesdata = {
    name: "预测曲线",
    type: 'line',
    data: [],
    smooth: true,
    lineStyle: {
      normal: {opacity: 1}
    }
  };
  
  seriesdata.data = [];
  
  for(var i = 0; i < data.length; i++){
    seriesdata.data.push(data[i].value);
  }

	tempLine.series.push(seriesdata);
  
  chart.setOption(tempLine);
};

var debug=function(){
  alert("1234");
}