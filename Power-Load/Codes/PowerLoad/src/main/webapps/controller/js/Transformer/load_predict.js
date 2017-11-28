/**
 * Controller of fault-recognition page
 */
// Define chart

/**
 * 曲线图
 */
var linechart1
var piechart1


$(document).ready(function() {
	
	$(function () { 
		$("#pie-chart").height(150);
		piechart1 = echarts.init($("#pie-chart")[0]);

 		optionpie1 = {	
		 	title : {
		        text: '行业分布',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
//		    legend: {
//		        orient: 'vertical',
//		        left: 'left',
//		        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
//		    },
		    series : [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
		                
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
	   };
	   piechart1.setOption(optionpie1); 
	    
	    /*蝶岭站500Kv母线*/
	    $("#line-chart1").height(315);
	    linechart1 = echarts.init($("#line-chart1")[0]);

 		optionline1 = {	
 			color:['#fe8c00','#00a6b5'],	
			/*title: {
		        text: '负载率曲线图'
			},*/
			legend: {	
		        data:['蝶岭站/500Kv母线','漠南站/500Kv母线'],
		    },
	        tooltip: {
	            trigger: 'axis',
	           /* axisPointer: {
	                type: 'line'
	            }*/
	        },
	        grid: {
		        left: '0%',
		        right: '13%',
		        bottom: '3%',		       
		        containLabel: true
		    },
		    calculable : true,
	        xAxis: [
	            {
	                type: 'category',
	                data: ['0:00','0:15','0:30','0:45','1:00','1:15','1:30','1:45','2:00','2:15','2:30','2:45','3:00','3:15','3:30','3:45','4:00','4:15','4:30','4:45','5:00','5:15','5:30','5:45','6:00','6:15','6:30','6:45','7:00','7:15','7:30','7:45','8:00','8:15','8:30','8:45','9:00','9:15','9:30','9:45','10:00','10:15','10:30','10:45','11:00','11:15','11:30','11:45','12:00','12:15','12:30','12:45','13:00','13:15','13:30','13:45','14:00','14:15','14:30','14:45','15:00','15:15','15:30','15:45','16:00','16:15','16:30','16:45','17:00','17:15','17:30','17:45','18:00','18:15','18:30','18:45','19:00','19:15','19:30','19:45','20:00','20:15','20:30','20:45','21:00','21:15','21:30','21:45','22:00','22:15','22:30','22:45','23:00','23:15','23:30','23:45'],
	                name:'时间',
	                boundaryGap: false
	            }
	        ],
	        yAxis: [
	            {	            
	                type: 'value'
	            }
	        ],
	        series: [	                             
	            {
	                name: '蝶岭站/500Kv母线',
	                type: 'line',
	                data: randomData(96),
	                smooth: false,
	                lineStyle: {
	                    normal: {
	                    	color:'#fe8c00'
	                    }	            
						
	                }
	            },
	            {
	                name: '漠南站/500Kv母线',
	                type: 'line',
	                data: randomData(96),
	                smooth: false,
	                lineStyle: {
	                    normal: {
	                    	color:'#00a6b5'
	                    }	            
						
	                }
	            },
	        ]
		  
	   };
	   linechart1.setOption(optionline1); 
	   
	   
	   
	})
	
	/*放大*/
	$("#full").click(function(){
		$("#full").hide();
        $("#small").show();
		$('#div1').setAttribute('class','col-md-12');
	
	});
	
	/*恢复*/
	$("#small").click(function(){	
		$('#div1').setAttribute('class','col-md-6');
		$("#small").hide();
        $("#full").show();
	});
	
	/*移除*/
	$("#remove").click(function(){	
		
	});
	
	/*刷新*/
	$("#refresh").click(function(){	
		location.reload();
	});
	$('#collapseLine1').on('show.bs.collapse', function () {
		$("#arrow1").attr("src","../../img/upicon.jpg");
	});

	$('#collapseLine1').on('hide.bs.collapse', function () {
		$("#arrow1").attr("src","../../img/downicon.jpg");
	});

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
});

function randomData(num) {
	var arrays = [];
	for(var i=0;i<num;i++){
		arrays.push(Math.random()*300000);
	}
	var value=JSON.stringify(arrays);
	var obj=eval(value);
  	return obj;
}
