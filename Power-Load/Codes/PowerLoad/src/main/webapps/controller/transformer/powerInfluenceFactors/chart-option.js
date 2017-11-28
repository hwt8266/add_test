var radarChart = {
	/* title : {
	     text: '预算 vs 开销（Budget vs spending）',
	     subtext: '纯属虚构'
	 },*/
	tooltip : {
		show : true,
		trigger : 'axis'
	},
	color:colorpool,
	legend : {
		data : []
	},

	polar : [ {
		indicator : [ {
			text : '降水',
			max:1
		}, {
			text : '温度',
			max:1
		}, {
			text : '风速',
			max:1
		}, {
			text : '相对湿度',
			max:0.1
		},

		],
		radius : 140
	} ],
	/* calculable : true,*/
	series : [ {
		type : 'radar',
		tooltip : {
			trigger : 'item'
		},
		data : [ ]

	} ]
};
