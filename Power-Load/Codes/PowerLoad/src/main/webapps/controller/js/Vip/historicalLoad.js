$(document).ready(function() {
	$("#line-chart").height(400);
	$('#searchDateRange').html(moment().format('YYYY-MM-DD') + ' - ' + moment().format('YYYY-MM-DD'));
  	$('#reportrange').daterangepicker(
							{
								// startDate: moment().startOf('day'),
								//endDate: moment(),
								//minDate: '01/01/2012',	//最小时间
								maxDate : moment(), //最大时间 
//								dateLimit : {
//									days : 30
//								}, //起止时间的最大间隔
								showDropdowns : true,
								showWeekNumbers : false, //是否显示第几周
								timePicker : false, //是否显示小时和分钟
								timePickerIncrement : 60, //时间的增量，单位为分钟
								timePicker12Hour : false, //是否使用12小时制来显示时间
								ranges : {
									//'最近1小时': [moment().subtract('hours',1), moment()],
				                    '昨日': [moment().subtract('days', 1).startOf('day'), moment()],
				                    '过去一周': [moment().subtract('days', 6), moment()],
				                    '过去30天': [moment().subtract('days', 29), moment()],
				                    '本月': [moment().startOf('months'), moment()],
				                    '上个月': [moment().subtract('months', 1).startOf('months'), moment().subtract('months', 1).endOf('months')],
								},
								opens : 'left', //日期选择框的弹出位置
								buttonClasses : [ 'btn btn-default btn-pos' ],
								applyClass : 'btn-small btn-color',
								cancelClass : 'btn-small',
								format : 'YYYY-MM-DD', //控件中from和to 显示的日期格式
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
							}, function(start, end, label) {//格式化日期显示框
				                
			                 	$('#searchDateRange').html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
			               });

				//设置日期菜单被选项  --开始--
		      	  var dateOption ;
		      	  if("${riqi}"=='day') {
						dateOption = "今日";
		          }else if("${riqi}"=='yday') {
						dateOption = "昨日";
		          }else if("${riqi}"=='week'){
						dateOption ="最近7日";
		          }else if("${riqi}"=='month'){
						dateOption ="最近30日";
		          }else if("${riqi}"=='year'){
						dateOption ="最近一年";
		          }else{
						dateOption = "自定义";
		          }
		      	   $(".daterangepicker").find("li").each(function (){
						if($(this).hasClass("active")){
							$(this).removeClass("active");
						}
						if(dateOption==$(this).html()){
							$(this).addClass("active");
						}
					});

  	
  	$.get('../map/guangxi.json', function (chinaJson) {
	    echarts.registerMap('guangxi', chinaJson);
	    var chart = echarts.init($("#pie-chart1")[0]);
	    chart.setOption({
	        zoom: 100,
	        series: [{
	            type: 'map',
	            map: 'guangxi',
	            markPoint:{
	            	symbol:'circle',
	            	
	            },
	        }],
    	});
	});
  	var chart2 = echarts.init($("#line-chart")[0]);
  	var option2 = {
		       	tooltip: {
		            trigger: 'axis',
		            axisPointer: {
		                type: 'line'
		            }
		        },
		        legend: {
            		top: 0,
            		left: 'left',
            		data: ['蝶岭站/500kV母线', '阳江站/220kV母线']
        		},
		        grid: {
		        	top: '15%',
			        left: '0%',
			        right: '2%',
			        bottom: '10%',
			        
			        containLabel: true
			    },
		        xAxis: [
		            {
		                type: 'category',
		                data: ['0:00','0:15','0:30','0:45','1:00','1:15','1:30','1:45','2:00','2:15','2:30','2:45','3:00','3:15','3:30','3:45','4:00','4:15','4:30','4:45','5:00','5:15','5:30','5:45','6:00','6:15','6:30','6:45','7:00','7:15','7:30','7:45','8:00','8:15','8:30','8:45','9:00','9:15','9:30','9:45','10:00','10:15','10:30','10:45','11:00','11:15','11:30','11:45','12:00','12:15','12:30','12:45','13:00','13:15','13:30','13:45','14:00','14:15','14:30','14:45','15:00','15:15','15:30','15:45','16:00','16:15','16:30','16:45','17:00','17:15','17:30','17:45','18:00','18:15','18:30','18:45','19:00','19:15','19:30','19:45','20:00','20:15','20:30','20:45','21:00','21:15','21:30','21:45','22:00','22:15','22:30','22:45','23:00','23:15','23:30','23:45'],
		                boundaryGap: false
		            }
		        ],
		        animation: false,
		        yAxis: [
		            {
		                type: 'value'
		            },
		        ],
		        
		        series: [
		            {
		                name: '蝶岭站/500kV母线',
		                type: 'line',
		                data: randomData(96),
		                smooth: true,
		                lineStyle: {
		                    normal: {opacity: 1}
		                }
		            },
		            {
		                name: '阳江站/220kV母线',
		                type: 'line',
		                data: randomData(96),
		                smooth: true,
		                lineStyle: {
		                    normal: {opacity: 1}
		                }
		            }
		        ]
		   };
	
  	chart2.setOption(option2);
  	
  	$(function () { 
  		$('#collapseOne').on('show.bs.collapse', function () {
  			$("#arrow1").attr("src","../../img/upicon.jpg");
  		});
    	$('#collapseOne').on('shown.bs.collapse', function () {
      		
      	});
      	$('#collapseOne').on('hide.bs.collapse', function () {
      		$("#arrow1").attr("src","../../img/downicon.jpg");
      	});
   });
});