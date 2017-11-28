var lineChart = {
		       	tooltip: {
		            trigger: 'axis',
		            axisPointer: {
		                type: 'line'
		            }
		        },
		        grid: {
		        	top: '25%',
			        left: '0%',
			        right: '0%',
			        bottom: '3%',
			        
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
        		legend: {
            		top: 0,
            		left: 'left',
            		data: ['去年', '今年']
        		},
		        yAxis: [
		            {
		            	name:'负荷(Kw)',
		                type: 'value'
		            },
		            {
		            	name:'温度',
		                type: 'value'
		            }
		        ],
		        
		        series: [
		            {
		                name: '今天',
		                type: 'line',
		                data: randomData(96),
		                smooth: true,
		                lineStyle: {
		                    normal: {opacity: 1}
		                }
		            },
		            {
		                name: '上个月今天',
		                type: 'line',
		                data: randomData(96),
		                smooth: true,
		                lineStyle: {
		                    normal: {opacity: 1}
		                }
		            },
		            {
		                name: '去年今天',
		                type: 'line',
		                data: randomData(96),
		                smooth: true,
		                lineStyle: {
		                    normal: {opacity: 1}
		                }
		            },
		            {
		                name: '今天影响因素',
		                type: 'line',
		                data: randomData(96),
		                smooth: true,
		                lineStyle: {
		                    normal: {opacity: 1}
		                }
		            },
		            {
		                name: '上个月今天影响因素',
		                type: 'line',
		                data: randomData(96),
		                smooth: true,
		                lineStyle: {
		                    normal: {opacity: 1}
		                }
		            },
		            {
		                name: '去年今天影响因素',
		                type: 'line',
		                data: randomData(96),
		                smooth: true,
		                lineStyle: {
		                    normal: {opacity: 1}
		                }
		            },
		        ]
		   };

var histogram = {
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        },
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        data : ['相关性'],
	    },
	    yAxis: {
	        type : 'value',
	        min:'1',
	        max:'-1'
	    },
	    series: [
	        {
	            name: '温度',
	            type: 'bar',
	            stack: '总量',
	            width: '110%',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'inside'
	                }
	            },
	            itemStyle:{
	            	normal:{
	            		color:'#ff6213',
	            	}
	            },
	            barWidth:'100',
	            data: [0.34]
	        }
	    ]
	};