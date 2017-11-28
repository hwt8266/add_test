/**
 * Controller of meter-distribution page
 */
// Define chart

var objType = 1

//list里头显示的文本，即每次恢复时要显示的列表
var initialItem;

//开始时间
var startTim;
//结束时间
var endTime;
//图
var datechar;

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
  $("#accordion").html('');
  $("#accordion").html(chartItem.replace('加载中...','历史负荷'));
  var winHeight = $(window).height()*0.6;
  $("#line-chart").height(winHeight);
  datechar = echarts.init($("#line-chart")[0]);
  var changeStart = unixToTime(startTime);
  var changeEnd = unixToTime(endTime);
  $('#searchDateRange').html(changeStart + ' - ' + changeEnd);
  //初始化把echarts图填入html文件中
  var tmpline = lineoption;
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
    tmpline.legend.data.push(data[j].name);

    var tmpData = data[j];
    var seriesdata= {
      name:data[j].name,
      type: 'line',
      data: [],
      smooth: false,
    };
  
    tmpline.series.push(seriesdata);
  
    for(var i=0;i<tmpData.points.length;i++)
    {
      //传数据
      seriesdata.data.push(tmpData.points[i].value);

      //第一次需要传横坐标
      if(first==0)
      {
        var cTime = unixToTime(tmpData.points[i].tv);
        //横坐标
        tmpline.xAxis[0].data.push(cTime);
      }
    }
    
    first=first+1;
  }
  
  datechar.setOption(tmpline);
  
  $('#collapseLine1').on('show.bs.collapse', function() {
    $('#arrow1').attr('src', '../../img/upicon.jpg');
  });
  
  $('#collapseLine1').on('hide.bs.collapse', function() {
    $('#arrow1').attr('src', '../../img/downicon.jpg');
  });
  
  dateinit();
};

// Initialize echart instance by DOM
$(document).ready(function(){
  endTime = moment().format('X');
  startTime = endTime-604800;
  //显示左侧列表
  queryFirstList(objType,updateList);
  //显示下拉列表和主内容
  queryDisplayItem(objType,displayItem);
  //全屏支持
  document.addEventListener("webkitfullscreenchange", function() {
    if(document.webkitIsFullScreen){
      $("#line-chart").css('width','100%');
      $("#line-chart").css('height','100%');
    }else{
      $("#line-chart").css('height',$(window).height()*0.6);
      $("#line-chart").css('width','100%');
    }
    
    datechar.resize();
  }, false);
});


// Resize chart div and chart when window is resized
$(window).resize(function() {
  datechar.resize();
});

/*上面的更新按钮*/
var fresh=function(){
  $("#accordion").html(chartItem);
  getHisPowerLoad(startTime,endTime,showId,historicalLoadShows);
}

//将时间戳变成普通时间
var unixToTime = function(time)
{
  var date = new Date(time*1000);
  Y = date.getFullYear() + '-';
  M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
  D = date.getDate();
  var tmp_time=Y+M+D;
  return tmp_time;
}

//将时间戳变成普通时间
var timeToUnix = function(time)
{
  var date = new Date(time.replace(/-/g, '/'));

  time3 = Date.parse(date);
  return time3/1000;
}