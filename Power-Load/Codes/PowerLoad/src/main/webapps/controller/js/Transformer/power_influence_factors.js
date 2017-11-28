/**
 * Controller of fault-recognition page
 */
// Define chart

/**
 * 曲线图
 */
var radarchart

$(document).ready(function() {
	
	$(function () { 
	    
	    /*蝶岭站500Kv母线*/
		$("#radar-chart").height(400);

		radarchart = echarts.init($("#radar-chart")[0]);
		option = {
			   /* title : {
			        text: '预算 vs 开销（Budget vs spending）',
			        subtext: '纯属虚构'
			    },*/
				
			    tooltip : {
			    	show:true,
			        trigger: 'axis'
			    },
			    legend: {			       
			        x : 'left',
			        icon:'line',
			        data:['蝶岭站500Kv母线','漠南站500Kv母线']
			    },
			    
			    polar : [
			       {
			           indicator : [
			               { text: '降水', max: 6000},
			               { text: '温度', max: 100},
			               { text: '相对湿度', max: 100},
			               { text: '风速', max: 200},
			             
			            ],
			            radius: 140
			        }
			    ],
			   /* calculable : true,*/
			    series : [
			        {
			            /*name: '预算 vs 开销（Budget vs spending）',*/
			            type: 'radar',
			          /*  itemStyle : {
			                normal : {	
			                	color:'#00a6b5'
			                } 
			            },*/
			            tooltip: {
			                trigger: 'item'
			            },
			            data : [
			                {
			                    value : [4300, 30, 69, 100],
			                    name : '蝶岭站500Kv母线',
			                    	
			                },
			                 {
			                    value : [5000, 20, 60, 60],
			                    name : '漠南站500Kv母线',
			                }
			            ]
			        	
			        }
			    ]
			};	                    
			                    
		radarchart.setOption(option);
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
