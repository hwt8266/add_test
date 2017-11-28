/**
 * Controller of fault-recognition page
 */
// Define chart

/**
 * 曲线图
 */
var linechart1
var linechart2
var linechart3
var linechart4
var linechart5
var linechart6

$(document).ready(function() {
	$(function () { 
	    /*蝶岭站500Kv母线*/
		$("#line-chart1").height(300);
		$("#line-chart2").height(300);
		$("#line-chart3").height(300);
		linechart1 = echarts.init($("#line-chart1")[0]);
		linechart2 = echarts.init($("#line-chart2")[0]);
		linechart3 = echarts.init($("#line-chart3")[0]);
  		optionline1 = {
  			color:['#00a6b5'],
			title: {
		        text: '日特征'
			},
	        tooltip: {
	            trigger: 'axis',
	            axisPointer: {
	                type: 'line'
	            }
	        },
	        grid: {
		        left: '0%',
		        right: '10%',
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
	            	/*name:'用电量/V',*/
	                type: 'value'
	            }
	        ],
	        series: [
	            {
	              /*  name: '用电量',*/
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {
	                    	color:'#00a6b5'
	                    }	            
						
	                }
	            },
	        ]
	   };
		optionline2 = {
			title: {
		        text: '月特征'
		  },
	        tooltip: {
	            trigger: 'axis',
	            axisPointer: {
	                type: 'line'
	            }
	        },
	        grid: {
		        left: '0%',
		        right: '10%',
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
	            	/*name:'用电量/V',*/
	                type: 'value'
	            }
	        ],
	        series: [
	            {
	                name: '1月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '2月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '3月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '4月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '5月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '6月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '7月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '8月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '9月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '10月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '11月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '12月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	        ]
	   };
		optionline3 = {
			title: {
		        text: '季特征'
		  },
	        tooltip: {
	            trigger: 'axis',
	            axisPointer: {
	                type: 'line'
	            }
	        },
	        grid: {
		        left: '0%',
		        right: '10%',
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
	            	/*name:'用电量/V',*/
	                type: 'value'
	            }
	        ],
	        series: [
	            {
	                name: '1季度',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '2季度',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '3季度',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '4季度',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            }
	        ]
	  };

	    linechart1.setOption(optionline1);
        linechart2.setOption(optionline2);
        linechart3.setOption(optionline3);
        
        /*蝶岭站500Kv母线*/
//		$("#line-chart4").height(300);
//		$("#line-chart5").height(300);
//		$("#line-chart6").height(300);
//		linechart4 = echarts.init($("#line-chart4")[0]);
//		linechart5 = echarts.init($("#line-chart5")[0]);
//		linechart6 = echarts.init($("#line-chart6")[0]);
  		optionline4 = {
  			color:['#00a6b5'],
			title: {
		        text: '日特征'
			},
	        tooltip: {
	            trigger: 'axis',
	            axisPointer: {
	                type: 'line'
	            }
	        },
	        grid: {
		        left: '0%',
		        right: '10%',
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
	                type: 'value'
	            }
	        ],
	        series: [
	            {
	                
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {
	                    	color:'#00a6b5'
	                    }	            
						
	                }
	            },
	        ]
	   };
		optionline5 = {
			title: {
		        text: '月特征'
		  },
	        tooltip: {
	            trigger: 'axis',
	            axisPointer: {
	                type: 'line'
	            }
	        },
	        grid: {
		        left: '0%',
		        right: '10%',
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
	            	
	                type: 'value'
	            }
	        ],
	        series: [
	            {
	                name: '1月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '2月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '3月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '4月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '5月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '6月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '7月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '8月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '9月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '10月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '11月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '12月',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	        ]
	   };
		optionline6 = {
			title: {
		        text: '季特征'
		  },
	        tooltip: {
	            trigger: 'axis',
	            axisPointer: {
	                type: 'line'
	            }
	        },
	        grid: {
		        left: '0%',
		        right: '10%',
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
	            	
	                type: 'value'
	            }
	        ],
	        series: [
	            {
	                name: '1季度',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '2季度',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '3季度',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            },
	            {
	                name: '4季度',
	                type: 'line',
	                data: randomData(96),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 1}
	                }
	            }
	        ]
	   };

//	    linechart4.setOption(optionline4);
//      linechart5.setOption(optionline5);
//      linechart6.setOption(optionline6);
	})
	
	$(function () { 
  		$('#collapseOne').on('show.bs.collapse', function () {
  			$("#arrow1").attr("src","../img/upicon.jpg");
  		});
    	$('#collapseOne').on('shown.bs.collapse', function () {
      		
      	});
      	$('#collapseOne').on('hide.bs.collapse', function () {
      		$("#arrow1").attr("src","../img/downicon.jpg");
      	});
   });
   $(function () { 
  		$('#collapseTwo').on('show.bs.collapse', function () {
  			$("#arrow2").attr("src","../img/upicon.jpg");
  		});
    	$('#collapseTwo').on('shown.bs.collapse', function () {
      		
      	});
      	$('#collapseTwo').on('hide.bs.collapse', function () {
      		$("#arrow2").attr("src","../img/downicon.jpg");
      	});
   });
   
});
