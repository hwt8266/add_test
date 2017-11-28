/**
 * Controller of meter-distribution page
 */
// Define chart

var objType = 2;

//list里头显示的文本，即每次恢复时要显示的列表
var initialItem;
//原来的id
var oriId=[];
//相比原来的新加的id
var addId=[];
//相比原来，要删除的id
var delId=[];
//现在显示的id
var showId=[];

var chartItem = $("#accordion").html();

var itemcontent = '<div id=uid style="float:left;width:165x;" > \
	<input type="text" class="input0" value="place ..." style="color:white;font-family:微软雅黑;width: 145px;height: 22px;margin-top:10px;margin-bottom:0px;background:transparent;margin-left:15px;text-overflow:ellipsis;overflow:hidden;font-size:1.1em;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 0px;border-left-width: 0px;padding-right: 25px;" readonly  >\
	<img id="remove" src="/PowerLoad/img/close.png" style="height:10px;margin-left:-20px;margin-bottom:10px;margin-top:15px;cursor:pointer;" onclick="removeAttr(uid);" />\
   </div>';

//这个地方就是显示图表的地方
var powerModeShows = function(data){
	$("#accordion").html('');
	//初始化把html文件放入相应的地方
	for(var j=0;j<data.length;j++)
	{
		var lid = "line-chart"+j;
		var tmpLine = chartItem;
		tmpLine = tmpLine.replace("line-chart",lid);
		var hid = "histogram-chart"+j;
		tmpLine = tmpLine.replace("histogram-chart",hid);
		
		$("#accordion").append(tmpLine);
		
		$("#"+lid).height(300);
		$("#"+hid).height(300);
		
	}
	
	//初始化把echarts图填入html文件中
	for(var j=0;j<data.length;j++)
	{
		lineData(data[j],j);
		hisData(data[j],j);
	}
	
};

// Initialize echart instance by DOM
$(document).ready(function(){
  var winHeight = $(window).height() * 0.8;
  //显示左侧列表
  queryFirstList(updateList);
  //显示下拉列表和主内容
  queryDisplayItem(objType,displayItem);

  
  
  
});


// Resize chart div and chart when window is resized
$(window).resize(function() {
//	for(var i=0;i<showId.length;i++)
//	{
//		$("#date-chart"+i).height(winHeight/2);
//	}
  
});

/*上面的更新按钮*/
$("#fresh").click(function(){	
	getLoadTrans(showId,powerModeShows);
});

//填入某个电站的模式表
var lineData = function(data,j)
{
	var index = 'line-chart'+j;
	var chart = echarts.init($("#"+Index)[0]);
	var tempLine =lineChart;
	tempLine.series=[];
	
	var temp = data;
	var legend = tempLine.legend;
	legend.data = [];
	legend.data.push
	tempLine.series = [];
	
	var last = temp.lastYearLine;
	var seriesLast= {
            name: '去年',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
    };
	legend.data.push('最大负载率');
	for(var i=0;i<last.length;i++)
	{
		
		seriesLast.data.push(last[i].ratio);
	}
	tempLine.series.push(seriesLast);
	
	var thisYear = temp.thisYearLine;
	var seriesThis= {
            name: '今年',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
    };
	
	for(var i=0;i<thisYear.length;i++)
	{
		
		seriesThis.data.push(thisYear[i].value);
	}
	tempLine.series.push(seriesThis);
	
	chart.setOption(tempLine);
	console.log(tempLine);
	
};

//填入某个电站的模式表
var hisData = function(data,j)
{
	var index = 'histogram-chart'+j;
	var chart = echarts.init($("#"+Index)[0]);
	var tempLine =histogram;
	
	var temp = data;
	var legend = tempLine.legend;
	legend.data = [];
	legend.data.push(temp.impactName);
	tempLine.series=[];
	
	var maxR = temp.maxRatio;
	
	var seriesData= {
        name: temp.impactName,
        type: 'bar',
        stack: '总量',
        width: '110%',
        label: {
            normal: {
                show: true,
                position: 'inside'
            }
        },
        itemStyle:{
        	normal:{
        		color:'#ff6213',
        	}
        },
        barWidth:'100',
        data: []
    };
	
	seriesData.data.push(temp.impactValue);
	tempLine.series.push(seriesData);
	
	chart.setOption(tempLine);
	console.log(tempLine);
	
};