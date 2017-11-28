$(document).ready(function() {
	$("#pie-chart1").height(800);
	$("#pie-chart2").height(800);
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
  	var chart2 = echarts.init($("#pie-chart2")[0]);
  	var option2 = {
		       	tooltip: {
		            trigger: 'axis',
		            axisPointer: {
		                type: 'line'
		            }
		        },
		        grid: {
		        	top: '20%',
			        left: '0%',
			        right: '2%',
			        bottom: '20%',
			        
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
		                name: '本用户',
		                type: 'line',
		                data: randomData(96),
		                smooth: true,
		                lineStyle: {
		                    normal: {opacity: 1}
		                }
		            },
		        ]
		   };
	
  	chart2.setOption(option2);
  	
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