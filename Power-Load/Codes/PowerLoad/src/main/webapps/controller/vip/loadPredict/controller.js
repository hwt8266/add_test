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
var powerLoadFeatruesShows = function(data){
	$("#accordion").html('');
	//初始化把html文件放入相应的地方
	for(var j=0;j<data.length;j++)
	{
		var date = "date-chart";
		var month = "month-chart";
		var pie = "pie-chart";
		var quarter = "quarter-chart";
		
		var dateIndex = "date-chart"+j;
		var monthIndex = "month-chart"+j;
		var pieIndex = "pie-chart"+j;
		var quarterIndex = "quarter-chart"+j;
		
		var tmp = chartItem;
		tmp = tmp.replace(date,dateIndex);
		tmp = tmp.replace(month,monthIndex);
		tmp = tmp.replace(quarter,quarterIndex);
		tmp = tmp.replace(pie,pieIndex);
		$("#accordion").append(tmp);
		$("#"+dateIndex).height(300);
		$("#"+monthIndex).height(300);
		$("#"+pieIndex).height(300);
		$("#"+quarterIndex).height(300);
		
	}
	
	//初始化把echarts图填入html文件中
	for(var j=0;j<data.length;j++)
	{
		featureDate(data,j);
		featureMonth(data,j);
		featureQuarter(data,j);
		featurePie(data,j);
	}
	
};

// Initialize echart instance by DOM
$(document).ready(function(){
  var winHeight = $(window).height() * 0.8;
  //显示左侧列表
  queryFirstList(updateList);
  //显示下拉列表和主内容
//  var mark=[];
  var a=0;
  queryDisplayItem(1,displayItem);
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
	getTransPowerLoadFeatrues(showId,powerLoadFeatruesShows);
});

//获取日特征
var featureDate = function(data,j)
{
	var dateIndex = "date-chart"+j;
	
	var datechar = echarts.init($("#"+dateIndex)[0]);
	var temp = data[j];
	
	$("#date-changeable").html();
	$("#date-changeable").html(temp.dynamicParam);
	$("#date-static").html();
	$("#date-static").html(temp.staticParam);
	
	var DataList = temp.featureD.featureDataList;
	var seriesdata= {
	      type: 'line',
	      data: [],
	      smooth: true,
	      lineStyle: {
	          normal: {
	          	color:'#00a6b5'
	          }	            
				
	      }
	};
	
	var tmpdataline = dateline;	
	tmpdataline.series=[];
	seriesdata.data=[];
	for(var i=0;i<DataList.length;i++)
	{
		
		seriesdata.data.push(DataList[i].value);
	}
	tmpdataline.series.push(seriesdata);
	datechar.setOption(tmpdataline);
};

//获取月份和年份特征
var featureMonth = function(data,j)
{
	var index = 'month-chart'+j;
	var tmpData = data[j];
	var DataList;
	var chart;
	var unit;
	
	var temp = data[j];
	
	$("#month-changeable").html();
	$("#month-changeable").html(temp.dynamicParam);
	$("#month-static").html();
	$("#month-static").html(temp.staticParam);
	console.log(temp);
	var DataList = temp.featureM;

	var chart = echarts.init($("#"+index)[0]);
	
	var tempLine =monthline;
	tempLine.series=[];
	
	for(var i=0;i<DataList.length;i++)
	{	
		var seriesdata= {
				name: '',
	            type: 'line',
	            data: [],
	            smooth: true,
	            lineStyle: {
	                normal: {opacity: 1}
	            }
		};
		seriesdata.name=(i+1)+"月";
		seriesdata.data=[];
		for(var k=0;k<DataList[i].featureDataList.length;k++)
		{
			
			seriesdata.data.push(DataList[i].featureDataList[k].value);
		}
		tempLine.series.push(seriesdata);
	}
	chart.setOption(tempLine);
	console.log(tempLine);
	
};

//获取月份和年份特征
var featureQuarter = function(data,j)
{
	var index = 'quarter-chart'+j;
	var tmpData = data[j];
	var DataList;
	var chart;
	var unit;
	
	var temp = data[j];
	
	$("#quarter-changeable").html();
	$("#quarter-changeable").html(temp.dynamicParam);
	$("#quarter-static").html();
	$("#quarter-static").html(temp.staticParam);
	console.log(temp);
	var DataList = temp.featureQ;

	var chart = echarts.init($("#"+index)[0]);
	
	var tempLine =quarterline;
	tempLine.series=[];
	
	
	for(var i=0;i<DataList.length;i++)
	{
		
		var seriesdata= {
	            name: '',
	            type: 'line',
	            data: [],
	            smooth: true,
	            lineStyle: {
	                normal: {opacity: 1}
	            }
	    };
		seriesdata.name=(i+1)+"季度";
		seriesdata.data=[];
		for(var k=0;k<DataList[i].featureDataList.length;k++)
		{
			
			seriesdata.data.push(DataList[i].featureDataList[k].value);
		}
		tempLine.series.push(seriesdata);
	}
	chart.setOption(tempLine);
	console.log(tempLine);
	
};

//获取饼图特征
var featurePie = function(data,j)
{
	var Index = "pie-chart"+j;
	var chart = echarts.init($("#"+Index)[0]);
	var temp = data[j];
	var tmpdataline = businesspie;	
	tmpdataline.series[0].data=[];
	
	var lowerThirty = {value: temp.lowerThirty,
					   name: '小于30%'
		};
	tmpdataline.series[0].data.push(lowerThirty);
	
	var thirtyToSixty = {value: temp.thirtyToSixty,
			   name: '30%~60%'
		};
	tmpdataline.series[0].data.push(thirtyToSixty);
	
	var sixtyToNinety = {value: temp.sixtyToNinety,
			   name: '60%~90%'
		};
	tmpdataline.series[0].data.push(sixtyToNinety);
	
	var greaterNinety = {value: temp.greaterNinety,
			   name: '大于90%'
		};
	tmpdataline.series[0].data.push(greaterNinety);

	chart.setOption(tmpdataline);
};