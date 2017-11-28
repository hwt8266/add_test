/**
 * Controller of meter-distribution page
 */
// Define chart
var areaChart;
var selectedAreaChart;
var mapChart;
var customerType = 1;
var districtSelect = [];
var allData;

// Counter for all districts
var allCount12 = 0;
var allCount24 = 0;
var allCount36 = 0;
var allCount48 = 0;
var allCount60 = 0;

//Counter for selected districts
var selCount12 = 0;
var selCount24 = 0;
var selCount36 = 0;
var selCount48 = 0;
var selCount60 = 0;

// GeoCoord information
var geoCoordMap = [];

// Update selected district array
var updateSelectedDistrictArray = function(districtID, selectFlag){
  if(selectFlag){
    districtSelect.push(districtID);
  }else{
    var index = 0;
    
    for(;index<districtSelect.length;index++){
      if(districtSelect[index] == districtID){
        break;
      }
    }
    
    districtSelect.splice(index,1);
  }
}

// Draw top line
var drawTopLine = function (canvas, winHeight, radioWidth){
  if(null == canvas){
    return;
  }

  var context = canvas.getContext("2d");

  context.lineWidth = 1;

  context.moveTo(0, 10);
  context.lineTo(canvas.width - radioWidth, 10);
  context.lineTo(canvas.width - radioWidth, canvas.height - 5);

  context.stroke();
};

//Draw bottom line
var drawBottomLine = function (canvas, winHeight, radioWidth){
  if(null == canvas){
    return;
  }

  var context = canvas.getContext("2d");

  context.lineWidth = 1;

  context.moveTo(canvas.width - radioWidth, 0);
  context.lineTo(canvas.width - radioWidth, canvas.height - 5);
  context.lineTo(0, canvas.height - 5);

  context.stroke();
}

// Handle radio button select change
var customerTypeChange = function(e){
  if(customerType != e.currentTarget.defaultValue){
    customerType = e.currentTarget.defaultValue;
    
    refreshCachedData();
    
    updateAreaChart();
    updateSelectedAreaChart();
    updateScatterMapChart();
    updateTable(1);
  }
}


// If array contains value
var arrayContain = function(array, value){
  for(var i = 0; i < array.length; i++){
    if(array[i] == value){
      return true;
    }
  }
  
  return false;
}

// Update UI by query data
var updateUI = function(data){
  allData = data;
  
  // Sort data as descending by remain up time
  allData.sort(function(a,b){
    return a.remainUpTime > b.remainUpTime ? 1 : -1;
  })
  
  refreshCachedData();
  
  updateAreaChart();
  updateSelectedAreaChart();
  updateScatterMapChart();
  updateTable(1);
}

// Refresh cached data by customer type
var refreshCachedData = function(){
  allCount12 = 0;
  allCount24 = 0;
  allCount36 = 0;
  allCount48 = 0;
  allCount60 = 0;
  geoCoordMap = [];
  
  for(var i = 0; i < allData.length; i++){
    var item = allData[i];
    
    if(item.customerType == customerType){
      geoCoordMap.push({
        name: item.meterName,
        value: [item.latitude, item.longitude,item.usedTime,item.remainUpTime]
      });

      if(item.remainUpTime <= 12){
        allCount12++;
      }else if(item.remainUpTime > 12 && item.remainUpTime <= 24){
        allCount24++;
      }else if(item.remainUpTime > 24 && item.remainUpTime <= 36){
        allCount36++;
      }else if(item.remainUpTime > 36 && item.remainUpTime <= 48){
        allCount48++;
      }else {
        allCount60++;
      }
    }
  }
}

// Update area chart
var updateAreaChart = function(){
  // Set data for charts
  // Options for area
  optArea.series[0].data = [
    {
      value:allCount12, 
      name:'0~12个月',
      itemStyle: {
        normal: {
          color: '#000080'
        },
        emphasis:{
          color: '#000080'
        }
      }
    },
    {
      value:allCount24, 
      name:'13~24个月',
      itemStyle: {
        normal: {
          color: '#4169E1'
        },
        emphasis:{
          color: '#4169E1'
        }
      }
    },
    {
      value:allCount36, 
      name:'25~36个月',
      itemStyle: {
        normal: {
          color: '#6495ED'
        },
        emphasis:{
          color: '#6495ED'
        }
      }
    },
    {
      value:allCount48, 
      name:'37~48个月',
      itemStyle: {
        normal: {
          color: '#87CEFA'
          },
          emphasis:{
            color: '#87CEFA'
          }
        }
      },
    {
      value:allCount60, 
      name:'49~60个月',
      itemStyle: {
        normal: {
          color: '#E1FFFF'
        },
        emphasis:{
          color: '#E1FFFF'
        }
      }
    }
  ];
  
  //Set options and data to chart。
  areaChart.setOption(optArea);
}

// Update selected area chart, if selected districts is undefined
// return all data
var updateSelectedAreaChart = function(){
  selCount12 = 0;
  selCount24 = 0;
  selCount36 = 0;
  selCount48 = 0;
  selCount60 = 0;
  
  for(var i = 0; i < allData.length; i++){
    var item = allData[i];

    if(item.customerType == customerType && (districtSelect.length == 0 || arrayContain(districtSelect, item.districtID))){
      if(item.remainUpTime <= 12){
        selCount12++;
      }else if(item.remainUpTime > 12 && item.remainUpTime <= 24){
        selCount24++;
      }else if(item.remainUpTime > 24 && item.remainUpTime <= 36){
        selCount36++;
      }else if(item.remainUpTime > 36 && item.remainUpTime <= 48){
        selCount48++;
      }else {
        selCount60++;
      }
    }
  }
	
  // Options for sub-area
  optSelectedArea.series[0].data = [
    {
      value:selCount12, 
      name:'0~12个月',
      itemStyle: {
        normal: {
          color: '#000080'
        },
        emphasis:{
          color: '#000080'
        }
      }
    },
    {
      value:selCount24, 
      name:'13~24个月',
      itemStyle: {
        normal: {
          color: '#4169E1'
        },
        emphasis:{
          color: '#4169E1'
        }
      }
    },
    {
      value:selCount36, 
      name:'25~36个月',
      itemStyle: {
        normal: {
          color: '#6495ED'
        },
        emphasis:{
          color: '#6495ED'
        }
      }
    },
    {
      value:selCount48, 
      name:'37~48个月',
      itemStyle: {
        normal: {
          color: '#87CEFA'
        },
        emphasis:{
          color: '#87CEFA'
        }
      }
    },
    {
      value:selCount60, 
      name:'49~60个月',
      itemStyle: {
        normal: {
          color: '#E1FFFF'
        },
        emphasis:{
          color: '#E1FFFF'
        }
      }
    }
  ];
  
  selectedAreaChart.setOption(optSelectedArea);
}

// Update map scatter chart
var updateScatterMapChart = function(){
  //Options for map chart
  optMapScatter.series[0].data = geoCoordMap;
  mapChart.setOption(optMapScatter);
}

// Update table
var updateTable = function(pageNum){
  $("#meter-table tr:gt(0)").remove();
  var startIndex = 10*(pageNum - 1); 
  var rowCounter = 0;
  var dispalyRowCounter = 0;
  
  for(var i = 0; i < allData.length; i++){
    if(allData[i].customerType == customerType){
      // Only 10 rows are showing in table per page
      if(dispalyRowCounter < 10 && rowCounter >= startIndex){
        var newRow = "<tr><td>" + allData[i].meterName + "</td><td>" + allData[i].meterID + "</td><td>" + allData[i].remainUpTime + "</td></tr>";
        $("#meter-table tr:last").after(newRow);
        dispalyRowCounter++;
      }
      
      rowCounter++;
    }
  }
  
  // Remove all li object first
  $("#pagination li").remove();
  
  var totalPage = Math.ceil(rowCounter / 10);
  
  // Assemble page navigator
  var $li_pre = $('<li><a href="javascript:void(0)" onclick="updateTable('+ (pageNum - 1) +')">&laquo;</a></li>');
  $("#pagination").append($li_pre);
  
  if(pageNum - 1 > 1){
    $("#pagination").append('<li><a href="javascript:void(0)">...</a></li>');
  }

  if(pageNum - 1 > 0){
    $("#pagination").append('<li><a href="javascript:void(0)" onclick="updateTable('+ (pageNum-1) + ')">' + (pageNum - 1) + '</a></li>');
  }
  
  $("#pagination").append('<li class="active"><a href="javascript:void(0)">' + pageNum + '</a></li>');
  
  if(pageNum + 1 <= totalPage){
    $("#pagination").append('<li><a href="javascript:void(0)" onclick="updateTable(' + (pageNum + 1) + ')">'+ (pageNum + 1) +'</a></li>');
  }
  
  if(pageNum + 1 < totalPage){
	  $("#pagination").append('<li><a href="javascript:void(0)">...</a></li>');
  }
  
  var $li_next = '<li><a href="javascript:void(0)" onclick="updateTable(' + (pageNum + 1) + ')">&raquo;</a></li>';
  $("#pagination").append($li_next);
  
  if(pageNum == 1){
    $("#pagination li:eq(0)").addClass("disabled");
    $("#pagination li:eq(0)").html('<a href="javascript:void(0)">&laquo;</a>');
    
  }
  
  if(pageNum == totalPage||totalPage==0){
    $("#pagination li:last").addClass("disabled");
    $("#pagination li:last").html('<a href="javascript:void(0)">&raquo;</a>');
  }
}

// Initialize echart instance by DOM
$(document).ready(function(){
  queryMeterRemainUpTimeInfo(-1, -1, updateUI);
  
  // Get current window height
  var winHeight = $(window).height() * 0.8;
  
  // Resize chart div and table div
  $("#chart-area").height(winHeight / 2);
  $("#chart-selectedarea").height(winHeight / 2);
  $("#chart-map").height(winHeight);
  $("#meter-table-div").height(winHeight - 220);
  $("#line-top").height(winHeight * 0.425);
  $("#line-bottom").height(winHeight * 0.425);
  
  var areaHeight = $("#chart-area").outerHeight(true);

  areaChart = echarts.init($("#chart-area")[0]);
  selectedAreaChart = echarts.init($("#chart-selectedarea")[0]);
  mapChart = echarts.init($("#chart-map")[0]);
  
  //Draw line
  drawTopLine($("#line-top")[0], winHeight, $("#radio").width());
  drawBottomLine($("#line-bottom")[0], winHeight, $("#radio").width());
  
  mapChart.on('click', function(event){
    updateSelectedDistrictArray(event.name, event.region.selected);
    updateSelectedAreaChart();
  });
  
  // Bind action event
  $("#optionsRadios1").bind("click", function(e){
	  customerTypeChange(e);
  });
  $("#optionsRadios2").bind("click", function(e){
	  customerTypeChange(e);
  });
  $("#optionsRadios3").bind("click",function(e){
	  customerTypeChange(e);
  });
});

// Resize chart div and chart when window is resized
$(window).resize(function() {
  // Get current window height
  var winHeight = $(window).height();
    winHeight = winHeight * 0.8;
    // Resize chart div
    $("#chart-area").height(winHeight/2);
    $("#chart-selectedarea").height(winHeight/2);
    $("#chart-map").height(winHeight);
    $("#meter-table").height(winHeight - 220);
    $("#line-top").height(winHeight * 0.425);
    $("#line-bottom").height(winHeight * 0.425);
    areaChart.resize();
    selectedAreaChart.resize();
    mapChart.resize();
});