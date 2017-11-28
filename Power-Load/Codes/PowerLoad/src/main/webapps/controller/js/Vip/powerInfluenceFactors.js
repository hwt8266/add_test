$(document).ready(function() {
	$("#radar-chart").height(400);
  	var chart = echarts.init($("#radar-chart")[0]);
  	var option = {
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        x: 'left',
	        icon:'line',
	        data:['蝶岭站/500kV母线', '阳江站/220kV母线']
	    },
	    radar: [
	        {
	            indicator: [
	                {text: '降水', max: 100},
	                {text: '速度', max: 100},
	                {text: '相对湿度', max: 100},
	                {text: '风速', max: 100}
	            ],
	            radius: 140
	        }
	    ],
	    series: [
	        {
	            type: 'radar',
	             tooltip: {
	                trigger: 'item'
	            },
	            data: [
	                {
	                    value: [60,73,85,40],
	                    name: '蝶岭站/500kV母线',
	                },
	                {
	                    value: [50,63,75,48],
	                    name: '阳江站/220kV母线',
	                }
	            ]
	        }
	    ]
  		
		       	
	};
	
  	chart.setOption(option);
  	
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
});