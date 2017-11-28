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


var itemcontent = '<div id=uid style="float:left;width:165x;" > \
	<input type="text" class="input0" value="place ..." style="color:white;font-family:微软雅黑;width: 145px;height: 22px;margin-top:10px;margin-bottom:0px;background:transparent;margin-left:15px;text-overflow:ellipsis;overflow:hidden;font-size:1.1em;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 0px;border-left-width: 0px;padding-right: 25px;" readonly  >\
	<img id="remove" src="/PowerLoad/img/close.png" style="height:10px;margin-left:-20px;margin-bottom:10px;margin-top:15px;cursor:pointer;" onclick="removeAttr(uid);" />\
   </div>';

//这个地方就是显示图表的地方
var influenceFactorsShows = function(data){
	var tmpdata = data.impacts;
	
	var char = echarts.init($("#radar-chart")[0]);
	var tmpLine = radarChart;
	tmpLine.data = [];
	
	for(var i=0;i<tmpdata.length;i++)
	{
		var seriesData = {
	            value : [],
	            name : '',
	            	
		};
		seriesData.name = tmpdata[i].name;
		
		seriesData.vaule.push(tmpdata[i].rainfall);
		seriesData.vaule.push(tmpdata[i].temperature);
		seriesData.vaule.push(tmpdata[i].windSpeed);
		seriesData.vaule.push(tmpdata[i].humidity);
		
		
		tmpLine.data.push(seriesData);
	}
	
	char.setOption(tmpLine);
	
};

// Initialize echart instance by DOM
$(document).ready(function(){
  var winHeight = $(window).height() * 0.8;
  //显示左侧列表
  queryFirstList(updateList);
  //显示下拉列表和主内容
//  var mark=[];
  var a=0;
  queryDisplayItem(objType,displayItem);
//  alert(mark.length);
//  while(true)
//  {
//	   if(a>0)
//		   break;
//  }
//  alert(mark.length);
  
  
  
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
	getInfluenceFactors(showId,influenceFactorsShows);
});

//获取日特征
var influenceFactorsData = function(data)
{
	var char = echarts.init($("#radar-chart")[0]);
	var tmpLine = radarChart;
	tmpLine.data = [];
	
	for(var i=0;i<data.length;i++)
	{
		var seriesData = {
	            value : [],
	            name : '',
	            	
		};
		seriesData.name = data.name;
		
		seriesData.vaule.push(data.rainfall);
		seriesData.vaule.push(data.temperature);
		seriesData.vaule.push(data.windSpeed);
		seriesData.vaule.push(data.humidity);
		
		
		tmpLine.data.push(seriesData);
	}
	
	tmpdataline.series.push(seriesdata);
	char.setOption(tmpLine);
};
