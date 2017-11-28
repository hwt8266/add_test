$(document).ready(function() {
  $("#pie-chart1").height(800);
  forceChart = echarts.init($("#pie-chart1")[0]);
  forceChart.showLoading();
  
  var initializeChart = function(industryCluster){
    forceChart.hideLoading();
  	
	option = {
  	  tooltip : {
        trigger: 'item',
        formatter : "{b}"
      },
      toolbox: {
        show : true,
        feature : {
          restore : {show: true},
          saveAsImage : {show: true}
        }
      },
      legend : {
        data : industryCluster.categories,
        orient : 'vertical',
        x : 'left'
      },
      noDataEffect: 'none',
      series :[{
        type: 'force',
        name: '专变用户行业聚类',
        itemStyle: {
          normal : {
            linkStyle : {
              opacity : 0.2
            }
          }
        },
        categories: industryCluster.categories,
        nodes: industryCluster.nodes,
        links: industryCluster.links,
        center:['75%','50%'],
        minRadius: 5,
        maxRadius: 5,
        gravity: 1.1,
        scaling: 1,
        steps: 20,
        large: true,
        useWorker: true,
        coolDown: 0.995,
        ribbonType: false
      }],
    };

    forceChart.setOption(option);
  }
  	
  msgGET("/PowerLoad/api/vipmgr/industry/cluster", initializeChart);

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
    
    forceChart.resize();
    
  }, false);
});