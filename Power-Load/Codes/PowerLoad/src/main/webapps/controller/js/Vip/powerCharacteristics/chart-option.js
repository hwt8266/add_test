/**
 * Options for chart
 */
//Options for area
var optArea = {
  title: {
    text: '区域'
  },
  tooltip: {
    trigger: 'item',
    formatter: "{a} <br/>{b}: {c} ({d}%)"
  },
  series: [
    {
      name:'剩余工作时间',
      type:'pie',
      radius: ['50%', '70%'],
      avoidLabelOverlap: false,
      label: {
        normal: {
          show: false,
          position: 'center'
        },
        emphasis: {
          show: true,
          textStyle: {
            fontSize: '12',
            fontWeight: 'bold'
          }
        }
      },
      labelLine: {
        normal: {
          show: false
        }
      },
      data:[]
    }
  ]
};

// Options for sub-area
var optSelectedArea = {
  title: {
    text: '已选子区域'
  },
  tooltip: {
    trigger: 'item',
    formatter: "{a} <br/>{b}: {c} ({d}%)"
  },
  series: [
    {
      name:'剩余工作时间',
      type:'pie',
      radius: ['50%', '70%'],
      avoidLabelOverlap: false,
      label: {
        normal: {
          show: false,
          position: 'center'
        },
        emphasis: {
          show: true,
          textStyle: {
            fontSize: '12',
            fontWeight: 'bold'
          }
        }
      },
      labelLine: {
        normal: {
          show: false
        }
      },
      data:[]
    }
  ]
};

// Options for map chart
var optMapScatter = {
  tooltip: {
    trigger: 'item',
    formatter: function (params) {
      return  '已工作时间: '+params.value[2]+'(月)</br>剩余工作时间: ' + params.value[3]+'(月)</br>生产厂商: '+params.name;
    }
  },
  dataRange: {
    min: 0,
    max: 60,
    x: 'right',
    y: 'bottom',
    calculable: true,
    color: ['#E1FFFF','#6495ED','#000080'],
    text: ['长', '短'],
    textStyle: {
      color: '#000'
    }
  },
  geo: {
    map: '天津',
    selectedMode: 'multiple',
    roam: true,
    scaleLimit: {
      min:1,
      max:3
    },
    label: {
      emphasis: {
        show: false
      }
    },
    itemStyle: {
      normal: {
        areaColor: '#F5F5F5',
        borderColor: '#222'
      },
      emphasis: {
        areaColor: '#DCDCDC'
      }
    }
  },
  series: [
    {
      name: 'Meter',
      type: 'scatter',
      coordinateSystem: 'geo',
      data: [],
      symbolSize: 8,
      label: {
        normal: {
          show: false
        },
        emphasis: {
          show: false
        }
      },
      itemStyle: {
        emphasis: {
          borderColor: '#fff',
          borderWidth: 1
        }
      }
    }
  ]
};