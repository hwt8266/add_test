var dateline = {
	title: {
        text: '日特征'
	},
	color:colorpool,
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'line'
        },
        formatter: function (params) {    	
        	var res=params[0].name + ' </br> ';
        	for(var i=0;i<params.length;i++){
        		if(params[i].seriesName.split(' ')[0]=="30%"||params[i].seriesName.split(' ')[0]=="60%"||params[i].seriesName.split(' ')[0]=="90%")continue;
        		res=res+params[i].seriesName.split(' ')[0]+':'+params[i].value+'</br>';
        	}
        	res=res+"可变能耗参数："+params[0].seriesName.split(' ')[1]+'</br>';
        	res=res+"静态能耗参数："+params[0].seriesName.split(' ')[2];
        	return  res;
        }
    },
    grid: {
        left: '0%',
        right: '12%',
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
        	name:'负载率(%)',
            type: 'value'
        }
    ],
    series: [
    ]
};
var monthline = {
	title: {
        text: '月特征'
	},
	color:colorpool,
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'line'
        },
    },
    grid: {
        left: '0%',
        right: '12%',
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
        	name:'负载率(%)',
            type: 'value'
        }
    ],
    series: [
        {
            name: '1月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '2月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '3月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '4月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '5月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '6月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '7月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '8月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '9月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '10月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '11月',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '12月',
            type: 'line',
                data: [],
                smooth: true,
                lineStyle: {
                    normal: {opacity: 1}
                }
         },
     ]
};
var quarterline = {
	title: {
        text: '季特征'
	},
	color:colorpool,
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'line'
        },
    },
    grid: {
        left: '0%',
        right: '12%',
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
        	name:'负载率(%)',
            type: 'value'
        }
    ],
    series: [
        {
            name: '1季度',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '2季度',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '3季度',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        },
        {
            name: '4季度',
            type: 'line',
            data: [],
            smooth: true,
            lineStyle: {
                normal: {opacity: 1}
            }
        }
    ]
};
var businesspie = {	
	title : {
	   text: '负载率统计',
	   left : 'center',
	   top : 'middle'
    },
    color:colorpool,
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    series : [
 {
		name : '负载率统计',
		type : 'pie',
		radius : ['50%','70%'],
		center : [ '50%', '50%' ],
		data : [],
}
    ]
};

