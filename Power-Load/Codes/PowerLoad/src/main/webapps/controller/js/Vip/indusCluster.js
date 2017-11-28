$(document).ready(function() {
	$("#pie-chart1").height(800);
  	Piechart1 = echarts.init($("#pie-chart1")[0]);
  	Piechart1.showLoading();
  	$.get('../map/indus.json', function (webkitDep) {
  		webkitDep=JSON.parse(webkitDep);
    	Piechart1.hideLoading();
	    option1 = {
	        legend: {
	            data: webkitDep.categories
	        },
	        color:colorpool,
	        tooltip: {
				trigger: 'item',
				formatter: "{b}"
			},
	        series: [{
	            type: 'graph',
	            layout: 'force',
	            animation: true,
	            label: false,
	            draggable: false,
	            data: webkitDep.nodes.map(function (node, idx) {
	                node.id = idx;
	                return node;
	            }),
	            categories: webkitDep.categories,
	            force: {
	                //initLayout: 'circular',
	                gravity: 0.1,
	                edgeLength: 5,
	                repulsion: 5
	            },
	            edges: webkitDep.links,
	            roam:true,
	            steps:1,
	            animationDuration:10,
	        }]
	    };
    	Piechart1.setOption(option1);
	});
  	$('#collapseOne').on('show.bs.collapse', function () {
			$("#arrow1").attr("src","../../img/upicon.jpg");
		});
	$('#collapseOne').on('shown.bs.collapse', function () {
  		
  	});
  	$('#collapseOne').on('hide.bs.collapse', function () {
  		$("#arrow1").attr("src","../../img/downicon.jpg");
  	});
  	document.addEventListener("webkitfullscreenchange", function() {
  	  if(document.webkitIsFullScreen){
  		  $("#pie-chart1").css('width','100%');
  		  $("#pie-chart1").css('height',$(window).height());
  	  }else{
  		  $("#pie-chart1").css('height',800);
  		  $("pie-chart1").css('width','100%');
  	  }
  	  Piechart1.resize();
    }, false);
});