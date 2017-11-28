
var timeoption={
		maxDate : moment(), // 最大时间
		// dateLimit : {
		// days : 30
		// }, //起止时间的最大间隔
		showDropdowns : true,
		showWeekNumbers : false, // 是否显示第几周
		timePicker : true, // 是否显示小时和分钟
		timePickerIncrement : 60, // 时间的增量，单位为分钟
		timePicker12Hour : true, // 是否使用12小时制来显示时间
		ranges : {
			// '最近1小时': [moment().subtract('hours',1), moment()],
			'昨日' : [ moment().subtract('days', 1).startOf('day'),
					moment() ],
			'过去一周' : [ moment().subtract('days', 6), moment() ],
			'过去30天' : [ moment().subtract('days', 29), moment() ],
			'本月' : [ moment().startOf('months'), moment() ],
			'上个月' : [
					moment().subtract('months', 1)
							.startOf('months'),
					moment().subtract('months', 1).endOf('months') ],
		},
		opens : 'left', // 日期选择框的弹出位置
		buttonClasses : [ 'btn btn-default btn-pos' ],
		applyClass : 'btn-small btn-color',
		cancelClass : 'btn-small',
		format : 'YYYY-MM-DD', // 控件中from和to 显示的日期格式
		separator : ' to ',
		locale : {
			applyLabel : '确定',
			cancelLabel : '取消',
			fromLabel : '起始时间',
			toLabel : '结束时间',
			customRangeLabel : '自定义',
			daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
			monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
					'七月', '八月', '九月', '十月', '十一月', '十二月' ],
			firstDay : 1
		}
	};

function queding(start, end, id) {// 格式化日期显示框
	var changeStart = start.format('YYYY-MM-DD');
	var changeEnd = end.format('YYYY-MM-DD');
	startTime = timeToUnix(changeStart);
	endTime = timeToUnix(changeEnd);
	var j=0;
	for(j=0;j<userid.length;j++){
		if(userid[j]==id){
			break;
		}
	}
	$('#searchDateRange'+j).html(changeStart + ' - ' + changeEnd);
	var Index = "pie-chart"+j;
	$("#"+Index).html('');
	var chart = echarts.init($("#"+Index)[0]);
	piecharts[j]=chart;
	var tmpdataline = businesspie;	
	tmpdataline.series[0].data=[];
	tmpdataline.title.text="加载中...";
	chart.setOption(tmpdataline);
	getStatistic(startTime,endTime,id,function(data){
		var j=0;
		for(j=0;j<userid.length;j++){
			if(userid[j]==id){
				break;
			}
		}
		
		featurePie(data,j,1);
	});
}
var timeToUnix = function(time)
{
	var date = new Date(time.replace(/-/g, '/'));

	time3 = Date.parse(date);
	return time3/1000;
}