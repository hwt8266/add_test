/**
 * Controller of fault-recognition page
 */
// Define chart

/**
 * 曲线图
 */
var linechart1
var linechart2


$(document).ready(function() {
	
	$(function () { 
	    
	    /*蝶岭站500Kv母线*/
	   $("#line-chart1").height(300);
	    linechart1 = echarts.init($("#line-chart1")[0]);

 		optionline1 = {	
 			color:['#fe8c00','#00a6b5','#9c1d5b','#89be11'],	
			/*title: {
		        text: '负载率曲线图'
			},*/
			legend: {
				icon:'line',
		        data:['模式1','模式2','模式3','模式4'],
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
	            	name:'用电量',
	                type: 'value'
	            }
	        ],
	        series: [
	                 
	            {
	                name: '模式1',
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
	                name: '模式2',
	                type: 'line',
	                data: randomData(96),
	                smooth: false,
	                lineStyle: {
	                    normal: {
	                    	color:'#00a6b5'
	                    }	            
						
	                }
	            },
	            {
	                name: '模式3',
	                type: 'line',
	                data: randomData(96),
	                smooth: false,
	                lineStyle: {
	                    normal: {
	                    	color:'#9c1d5b'
	                    }	            
						
	                }
	            },
	            {
	                name: '模式4',
	                type: 'line',
	                data: randomData(96),
	                smooth: false,
	                lineStyle: {
	                    normal: {
	                    	color:'#89be11'
	                    }	            
						
	                }
	            },
	        ]
		  
	   };
	   linechart1.setOption(optionline1); 
	   
	   /*漠南站500Kv母线*/
	   $("#line-chart2").height(300);
	    linechart2 = echarts.init($("#line-chart2")[0]);

 		optionline2 = {	
 			color:['#fe8c00','#00a6b5','#9c1d5b','#89be11'],	
			/*title: {
		        text: '负载率曲线图'
			},*/
			legend: {
				/*orient: 'vertical',
				right: 'right',*/
				icon:'line',
		        data:['模式1','模式2','模式3','模式4'],
		    },
	        tooltip: {
	            trigger: 'axis',
	            axisPointer: {
	                type: 'line'
	            }
	        },
	        grid: {
		        left: '0%',
		        right: '13%',
		        bottom: '3%',		       
		        containLabel: true
		    },
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
	            	name:'用电量',
	                type: 'value'
	            }
	        ],
	        series: [
	                 
	            {
	                name: '模式1',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {
	                    	color:'#fe8c00'
	                    }	            
	                }
	            	
	            },
	            {
	                name: '模式2',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {
	                    	color:'#00a6b5'
	                    }	            
						
	                }
	            },
	            {
	                name: '模式3',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {
	                    	color:'#9c1d5b'
	                    }	            
						
	                }
	            },
	            {
	                name: '模式4',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {
	                    	color:'#89be11'
	                    }	            
						
	                }
	            },
	        ]
		  
	   };
	   linechart2.setOption(optionline2); 
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


	$('#collapseLine2').on('show.bs.collapse', function () {
		$("#arrow1").attr("src","../../img/upicon.jpg");
	});

	$('#collapseLine2').on('hide.bs.collapse', function () {
		$("#arrow1").attr("src","../../img/downicon.jpg");
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
