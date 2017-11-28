/**
 * Controller of meter-distribution page
 */
// Define chart

var objType = 2;

//list里头显示的文本，即每次恢复时要显示的列表
var initialItem;

//开始时间
var startTime='';
//结束时间
var endTime='';

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
var historicalLoadShows = function(data){
	
	//初始化把echarts图填入html文件中
	var datechar = echarts.init($("#line-chart")[0]);
	var tmpline = lineChart;
	//legend置为空
	tmpline.legend.data = [];
	//横坐标置为空
	tmpline.xAxis[0].data=[];
	//数据置为空
	tmpline.series = [];
	
	var first=0;
	for(var j=0;j<data.length;j++)
	{
		//传legend
		tmpline.legend.data.push(data.name);
		
		var tmpData = data[j];
		var DataList = tmpData.featureD.featureDataList;
		var seriesdata= {
                name: '',
                type: 'line',
                data: [],
                smooth: false,
                lineStyle: {
                    normal: {
                    	color:'#fe8c00'
                    }	            
					
                }
        };
		
		for(var i=0;i<tmpData.points.length;i++)
		{
			//传数据
			seriesdata.data.push(tmpData.points[i].value);
			
			//第一次需要传横坐标
			if(first==0)
			{
				var cTime = changeTime(tmpData.points[i].tv);
				//横坐标
				tmpline.xAxis[0].data.push(cTime);
			}
		}
		first=first+1;
	}
	tmpline.series.push(seriesdata);
	datechar.setOption(tmpline)
	
};

// Initialize echart instance by DOM
$(document).ready(function(){
 
  endTime = moment().format('X');
  startTime = endTime-86400;
  var winHeight = $(window).height() * 0.8;
  //显示左侧列表
  queryFirstList(updateList);
  //显示下拉列表和主内容
  queryDisplayItem(objType,displayItem);
//  getHisPowerLoad(startTime,endTime);
});


// Resize chart div and chart when window is resized
$(window).resize(function() {
//	for(var i=0;i<showId.length;i++)
//	{
//		$("#date-chart"+i).height(winHeight/2);
//	}
  
});

/*上面的更新按钮*/
var fresh=function(){
	alert(unixToTime(startTime));
	alert(unixToTime(endTime));
	getHisPowerLoad(startTime,endTime,showId,historicalLoadShows);
}

//将时间戳变成普通时间
var unixToTime = function(time)
{
	var date = new Date(time*1000);
	Y = date.getFullYear() + '-';
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	D = date.getDate() + ' ';
	h = date.getHours() + ':';
	m = date.getMinutes() + ':';
	s = date.getSeconds(); 
	var tmp_time=Y+M+D+h+m+s;
	return tmp_time;
}

//将时间戳变成普通时间
var timeToUnix = function(time)
{
	var date = new Date(time.replace(/-/g, '/'));
	time3 = Date.parse(date);
	return time3/1000;
}

