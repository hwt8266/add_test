/**
 * Controller of meter-distribution page
 */
// Define chart

var objType = 1;

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
var datecharts=[];
var monthcharts=[];
var piecharts=[];
var quartercharts=[];

var userid=[];

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
		
		var contentid="contentid";
		var tabid="tabid";
		var collapseLineid="collapseLineid";
		var arrowid="arrowid";
	
		var reportrange="reportrange";
		var searchDateRange="searchDateRange";
		
		var tmp = chartItem;
		tmp=tmp.replace('加载中...',data[j].name);
		tmp=tmp.replace(/contentid/g,contentid+j);
		tmp=tmp.replace(/tabid/g,tabid+j);
		tmp=tmp.replace(/collapseLineid/g,collapseLineid+j);
		tmp=tmp.replace(/reportrange/g,reportrange+j);
		tmp=tmp.replace(/searchDateRange/g,searchDateRange+j);
		tmp = tmp.replace(date,dateIndex);
		tmp = tmp.replace(month,monthIndex);
		tmp = tmp.replace(quarter,quarterIndex);
		tmp = tmp.replace(pie,pieIndex);
		tmp = tmp.replace(arrowid,arrowid+j);
		$("#accordion").append(tmp);
		$('#searchDateRange'+j).html('请选择统计时间范围');
		$('#reportrange'+j).daterangepicker(timeoption,function(start, end, label){
			queding(start,end,this.id);
		},data[j].id);
		userid.push(data[j].id);
		$("#"+dateIndex).height(300);
		$("#"+monthIndex).height(300);
		$("#"+pieIndex).height(280);
		$("#"+quarterIndex).height(300);
		$("#"+pieIndex).html('<img src="/PowerLoad/img/statistic_prompt.jpg" style="height:100%; width:100%" />');
		var arrow='#'+arrowid+j;
		$('#'+collapseLineid+j).on('show.bs.collapse',function(){
			$('#arrowid'+this.id.substring(this.id.length-1)).attr('src','../../img/upicon.jpg');
		});
		$('#'+collapseLineid+j).on('hide.bs.collapse',function(){
			$('#arrowid'+this.id.substring(this.id.length-1)).attr('src','../../img/downicon.jpg');
		});
	}
	
	//初始化把echarts图填入html文件中
	for(var j=0;j<data.length;j++)
	{
		featureDate(data,j);
		featureMonth(data,j);
		featureQuarter(data,j);
		featurePie(data,j,0);
	}
};

// Initialize echart instance by DOM
$(document).ready(function(){
  var winHeight = $(window).height() * 0.8;
  //显示左侧列表
  queryFirstList(objType,updateList);
  queryDisplayItem(objType,displayItem);
  
  document.addEventListener("webkitfullscreenchange", function() {
	  if(document.webkitIsFullScreen){
		  for(var i=0;i<datecharts.length;i++){
			  $("#date-chart"+i).css('width','100%');
			  $("#date-chart"+i).css('height',$(window).height()/2-40);
			  $("#month-chart"+i).css('width','100%');
			  $("#month-chart"+i).css('height',$(window).height()/2-40);
			  $("#quarter-chart"+i).css('width','100%');
			  $("#quarter-chart"+i).css('height',$(window).height()/2-40);
			  $("#pie-chart"+i).css('width','100%');
			  $("#pie-chart"+i).css('height',$(window).height()/2-40-20);
		  }
	  }else{
		  for(var i=0;i<datecharts.length;i++){
			  $("#date-chart"+i).css('width','100%');
			  $("#date-chart"+i).height(300);
			  $("#month-chart"+i).css('width','100%');
			  $("#month-chart"+i).height(300);
			  $("#quarter-chart"+i).css('width','100%');
			  $("#quarter-chart"+i).height(300);
			  $("#pie-chart"+i).css('width','100%');
			  $("#pie-chart"+i).height(280);
		  }
	  }
	  resize();
  }, false);
});

var resize=function(){
	for(var i=0;i<quartercharts.length;i++){
		quartercharts[i].resize();
		monthcharts[i].resize();
		datecharts[i].resize();
		if(typeof piecharts[i] != "undefined" ){
			piecharts[i].resize();
		}
	}
}

// Resize chart div and chart when window is resized
$(window).resize(function() {
	resize();
});

/*上面的更新按钮*/
var fresh=function (){	
	$("#accordion").html(chartItem);
	getTransPowerLoadFeatrues(showId,powerLoadFeatruesShows);
}

//获取日特征
var featureDate = function(data,j)
{
	var dateIndex = "date-chart"+j;
	
	var datechar = echarts.init($("#"+dateIndex)[0]);
	datecharts.push(datechar);
	var temp = data[j].featureD;
	var tmpdataline = dateline;
	tmpdataline.series=[];
	for(var i=0;i<temp.length;i++){
		var DataList = temp[i].featureDataList;
		var seriesdata= {
		      type: 'line',
		      name: '日特征'+(i+1)+' '+temp[i].dynamicParam+' '+temp[i].staticParam,
		      data: [],
		      smooth: true,
		};
		seriesdata.data=[];
		for(var i=0;i<DataList.length;i++)
		{
			seriesdata.data.push(DataList[i].value);
		}
		tmpdataline.series.push(seriesdata);
	}
	
	
	/*var seriesdata3= {
		      type: 'line',
		      name: '30%'+' 0 0',
		      lineStyle:{
		    	  normal:{
		    		  type:'dashed'
		    	  }
		      },
		      data: [],
		      smooth: true,
	};
	
	var seriesdata6= {
		      type: 'line',
		      name: '60%'+' 0 0',
		      data: [],
		      lineStyle:{
		    	  normal:{
		    		  type:'dashed'
		    	  }
		      },
		      smooth: true,
	};
	
	var seriesdata9= {
		      type: 'line',
		      name: '90%'+' 0 0',
		      data: [],
		      lineStyle:{
		    	  normal:{
		    		  type:'dashed'
		    	  }
		      },
		      smooth: true,
	};*/
	/*for(var i=0;i<DataList.length;i++)
	{
		seriesdata3.data.push(0.3);
		seriesdata6.data.push(0.6);
		seriesdata9.data.push(0.9);
	}
	
	tmpdataline.series.push(seriesdata3);
	tmpdataline.series.push(seriesdata6);
	tmpdataline.series.push(seriesdata9);*/
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
	
	var DataList = temp.featureM;

	var chart = echarts.init($("#"+index)[0]);
	
	monthcharts.push(chart);
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
	
	var DataList = temp.featureQ;

	var chart = echarts.init($("#"+index)[0]);
	quartercharts.push(chart);
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
var featurePie = function(data,j,flag)
{
	if(flag==1){
		var Index = "pie-chart"+j;
		$("#"+Index).html('');
		var chart = echarts.init($("#"+Index)[0]);
		piecharts[j]=chart;
		var temp = data;
		var tmpdataline = businesspie;	
		tmpdataline.title.text="负载率统计";
		tmpdataline.series[0].data=[];
		if((temp.lowerThirty==0&&temp.thirtyToSixty==0&&temp.sixtyToNinety==0&&temp.greaterNinety==0)){
			tmpdataline.title.text="无统计数据";
		}else{
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
		}
		chart.setOption(tmpdataline);
	}
};