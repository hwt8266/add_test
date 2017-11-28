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

var linecharts=[];
var piecharts=[];

var chartItem = $("#accordion").html();

var itemcontent = '<div id=uid style="float:left;width:165x;" > \
	<input type="text" class="input0" value="place ..." style="color:white;font-family:微软雅黑;width: 145px;height: 22px;margin-top:10px;margin-bottom:0px;background:transparent;margin-left:15px;text-overflow:ellipsis;overflow:hidden;font-size:1.1em;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 0px;border-left-width: 0px;padding-right: 25px;" readonly  >\
	<img id="remove" src="/PowerLoad/img/close.png" style="height:10px;margin-left:-20px;margin-bottom:10px;margin-top:15px;cursor:pointer;" onclick="removeAttr(uid);" />\
   </div>';
var hangyeData='<div id="inid">hangyename　　　<input type="radio" value="1" name="code" checked>高　<input type="radio" value="2" name="code">中　<input type="radio" value="3" name="code">低<div>';
//这个地方就是显示图表的地方
var LoadPredictShows = function(data){
	$("#accordion").html('');
	//初始化把html文件放入相应的地方
	for(var j=0;j<data.length;j++)
	{
		var line = "line-chart";
		var pie = "pie-chart";
		var selectid ="selectid";
		var hangyeid ="hangyeid";
		var tabid="tabid";
		var contentid="contentid";
		var arrowid="arrowid";
		var collapseid="collapseid"; 
		var bianyaid="bianyaid";
		var tmp = chartItem;
		tmp = tmp.replace(/hangyeid/g,hangyeid+j);
		tmp = tmp.replace(/arrowid/g,arrowid+j);
		tmp = tmp.replace(/collapseid/g,collapseid+j);
		tmp = tmp.replace('加载中...',data[j].name);
		tmp = tmp.replace(line,line+j);
		tmp = tmp.replace(pie,pie+j);
		tmp = tmp.replace(/selectid/g,selectid+j);
		tmp = tmp.replace(/tabid/g,tabid+j);
		tmp = tmp.replace(/contentid/g,contentid+j);
		tmp = tmp.replace(bianyaid,data[j].id);
		tmp = tmp.replace("index",j);
		$("#accordion").append(tmp);
		$("#"+selectid+j).height(150);
		$("#"+line+j).height(320);
		$("#"+pie+j).height(150);
		var arrow='#'+arrowid+j;
		$('#'+collapseid+j).on('show.bs.collapse',function(){
			$('#arrowid'+this.id.substring(this.id.length-1)).attr('src','../../img/upicon.jpg');
		});
		$('#'+collapseid+j).on('hide.bs.collapse',function(){
			$('#arrowid'+this.id.substring(this.id.length-1)).attr('src','../../img/downicon.jpg');
		});
	}
	
	//初始化把echarts图填入html文件中
	for(var j=0;j<data.length;j++)
	{
		processSelect(data,j);
		processPie(data,j);
		processLine(data,j);
	}
	
};

// Initialize echart instance by DOM
$(document).ready(function(){
  //显示左侧列表
  queryFirstList(objType,updateList);
  //显示下拉列表和主内容
  queryDisplayItem(objType,displayItem);
  
  document.addEventListener("webkitfullscreenchange", function() {
	  if(document.webkitIsFullScreen){
		  for(var i=0;i<linecharts.length;i++){
			  $("#line-chart"+i).css('width','100%');
			  $("#line-chart"+i).css('height',($(window).height()-20)+'px');
			  $("#pie-chart"+i).css('width','100%');
			  $("#pie-chart"+i).css('height',(($(window).height()-40)/2)+'px');
			  $("#selectid"+i).css('width','100%');
			  $("#selectid"+i).css('height',(($(window).height()-40)/2)+'px');
			  $("#hangyeid"+i).css('height',(($(window).height()-40)/2-65)+'px');
			  $("#hangyeid"+i).css('width','100%');
		  }
	  }else{
		  for(var i=0;i<linecharts.length;i++){
			  $("#line-chart"+i).css('width','100%');
			  $("#line-chart"+i).height(320);
			  $("#pie-chart"+i).css('width','100%');
			  $("#pie-chart"+i).height(150);
			  $("#selectid"+i).css('width','100%');
			  $("#selectid"+i).height(150);
			  $("#hangyeid"+i).height(85);
		  }
	  }
	  resize();
  }, false);
})
var predict=function(index){
	var id=$("#contentid"+index+">input:eq(0)").val();
	var industryParam=[];
	var len=$("#selectid"+index+">div:eq(1)>div").length;
	for (var i=0;i<len;i++){
		var ids=$("#selectid"+index+">div:eq(1)>div")[i].id;
		var value=$("#selectid"+index+">div:eq(1)>div:eq("+i+") input[name='"+(index+"-"+ids)+"']:checked")[0].value;
		industryParam.push({
			industryID:ids,
			devLevel:value
		});
	}
	getLoadPredictCustomize(id,industryParam,addLine,index);
}

var addLine=function(data,index){
	var toption=linecharts[index].getOption();
	var seriesdata= {
            name: '预测曲线',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
    };
	for(var i=0;i<data.length;i++){
		seriesdata.data.push(data[i].value);
	}
	toption.series[3]=(seriesdata);
	toption.legend[0].data[3]='预测曲线';
	linecharts[index].setOption(toption);
}

var fresh=function(){
	$("#accordion").html(chartItem);
	getLoadPredictDefault(showId,LoadPredictShows);
}

var resize=function(){
	for(var i=0;i<linecharts.length;i++){
		linecharts[i].resize();
		piecharts[i].resize();
	}
}

// Resize chart div and chart when window is resized
$(window).resize(function() {
	resize();
});


var processSelect = function(data,j)
{
	var index = 'hangyeid'+j;
	$("#"+index).html('');
	var tdata=data[j].industryUserData;
	var content="";
	for(var i=0;i<tdata.length;i++){
		var t=hangyeData;
		content+=t.replace("inid",tdata[i].industryID).replace("hangyename",tdata[i].industryName).replace(/code/g,j+"-"+tdata[i].industryID);
	}
	$("#"+index).html(content);
	/*if(tdata.length==1){
		$("#selectid"+j+">div:eq(1)>button:eq(0)").attr("disabled",true);
		$("#selectid"+j+">div:eq(0)>ul:eq(0)>li:eq(0)>select:eq(0)").attr("disabled",true);
	}
	if(tdata.length==0){
		$("#selectid"+j+">div:eq(1)>button:eq(0)").attr("disabled",true);
	}*/
};

//获取月份和年份特征
var processLine = function(data,j)
{
	var index = 'line-chart'+j;
	var chart = echarts.init($("#"+index)[0]);
	linecharts.push(chart);
	
	var tempLine =lineoption;
	tempLine.series=[];
	
	var DataList=data[j].defaultFCLine;
	for(var i=0;i<DataList.length;i++)
	{
		var tname="";
		if(DataList[i].lineType==1){
			tname="高增长";
		}else if(DataList[i].lineType==2){
			tname="中增长";
		}else if(DataList[i].lineType==3){
			tname="低增长";
		}
		tempLine.legend.data.push(tname);
		var seriesdata= {
	            name: tname,
	            type: 'line',
	            data: [],
	            smooth: true,
	            lineStyle: {
	                normal: {opacity: 1}
	            }
	    };
		seriesdata.data=[];
		for(var k=0;k<DataList[i].points.length;k++)
		{
			seriesdata.data.push(DataList[i].points[k].value);
		}
		tempLine.series.push(seriesdata);
	}
	chart.setOption(tempLine);
};

//获取饼图特征
var processPie = function(data,j)
{
	var Index = "pie-chart"+j;
	var chart = echarts.init($("#"+Index)[0]);
	piecharts.push(chart);
	
	
	var temp = data[j].industryUserData;
	var tmpdataline = businesspie;	
	tmpdataline.series[0].data=[];
	
	for(var i=0;i<temp.length;i++){
		tmpdataline.series[0].data.push({value: temp[i].userCount,name: temp[i].industryName});
	}
	if(temp.length==0){
		tmpdataline.title.text="暂无统计数据";
	}
	chart.setOption(tmpdataline);
};