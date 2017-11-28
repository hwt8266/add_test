lineChart = {	
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
