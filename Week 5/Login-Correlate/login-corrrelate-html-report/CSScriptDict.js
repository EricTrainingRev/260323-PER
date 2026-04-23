CSStopExecution=false;
CSAct = new Object;
var myEChart;
var option;
var seriesData = [];
var barCategories = [];
var yRightAxis;

$(document).ready(function () {
	var xAxisTitle = $('#xAxisTitle').html();
	var yAxisTitle = $('#yAxisTitle').html();
	var yRightAxisTitle = $('#yRightAxisTitle').html();
	var type = $('#Type').html();
	var notTimeBased = false;

	if ($('#timeBasedLinearGraph').html() == 'false')
		notTimeBased = true;
	
	getSeriesData(type, notTimeBased);
	
	var yAxisNameGap = 45;
	var barColumnWidth = document.getElementById('graphContainer').offsetWidth / barCategories.length / 1.25;
	var dom = document.getElementById('graphContainer');
	myEChart = echarts.init(dom, null, {
	  renderer: 'canvas',
	  useDirtyRect: false
	});
	if (type == 'no-points' || type == "line" || type == "area")
	{
		let xAxis;
		if(notTimeBased){ 
			xAxis = [{
				name: xAxisTitle,
				nameLocation: 'center',
				nameGap: 30,
				type: 'value',
			  },{}];
		} else {
			xAxis = {
				name: xAxisTitle,
				nameLocation: 'center',
				nameGap: 30,
				type: 'time',
				gridIndex: 0,
				axisLabel:{
					formatter: function(value, index){
						if (value < 100000000) {
							return getTimeFormat(value);
						}
						return echarts.format.formatTime('hh:mm:ss', value, true);
					}
				}
			};
		}
		option = {
			grid: {
				left: '5%',
				right: '5%',
			},
			xAxis: xAxis,
			yAxis: [
				{
					name: yAxisTitle,
					nameLocation: 'center',
					nameGap: yAxisNameGap,
					type: 'value',
					axisLabel: {
						formatter: function(value, index){
							if(value < 10000)
								return value
							if(value < 1000000)
								return value / 1000 + 'K';
							return value / 1000000 + 'M';
						}
					},
				},
				{
					name: yRightAxisTitle,
					nameLocation: 'center',
					nameRotate: 270,
					nameGap: yAxisNameGap,
					type: 'value',
				}
			],
			legend: {
				orient: 'horizontal',
				type: 'scroll',
				bottom: 0,
				itemStyle: {
					opacity: 0
				}
			},
			series: seriesData
		};
	} else if (type == 'column')
	{
		option = {
			grid: {
				left: '5%',
				right: '5%',
			  },
			xAxis: {
				type: 'category',
				data: barCategories,
				boundaryGap: true,
				axisLabel: {
					interval: 0,
					overflow: 'truncate',
					rotate: 0,
					width: barColumnWidth
				},
			},
			yAxis: {
				name: yAxisTitle,
				nameLocation: 'center',
				nameGap: yAxisNameGap,
				type: 'value'
			},
			tooltip: {
				trigger: 'item'
			  },
			legend: {
				orient: 'horizontal',
				type: 'scroll',
				bottom: 0,
			},
			series: seriesData
		  };
	} else if (type == 'pie')
	{
		option = {
			grid: {
				left: '5%',
				right: '5%',
			  },
			tooltip: {
				trigger: 'item'
			  },
			legend: {
				orient: 'horizontal',
				type: 'scroll',
				bottom: 0,
			},
			series: seriesData
			};
	}
	if (option && typeof option === 'object') {
	 	myEChart.setOption(option);
	}
	window.addEventListener('resize', myEChart.resize);
});

function getSeriesData(type, notTimeBased){
	var overlayType = $('#overlayType').html();
	var sLen = parseInt($("#SeriesCount").text());
	var timeBased = $("#Values_0").attr('timeBased');
	var timestampMultiplier = 1000;
	if(!(timeBased == 'true') || notTimeBased){
		timestampMultiplier = 1;
	}
	for (i = 0; i < sLen; i++) {
		var table = document.getElementById("Values_"+i);
		var axis = $("#Values_"+i).attr('axis'); 
		var data = [];

		for (var j = 0; j < table.rows.length; j++) {
		   var cells = table.rows[j].cells;
		   if(cells.length == 3){
			   data.push({
				name: cells[0].innerHTML,
				value: parseFloat(cells[1].innerHTML.replace(',', '.')),
				itemStyle: {
					color: cells[2].innerHTML
				}
			})
		   }
			else if(cells.length == 2 && type == 'column'){
				if(barCategories.indexOf(cells[0].innerHTML) == -1){
					barCategories.push(cells[0].innerHTML);
				}
				data.push(parseFloat(cells[1].innerHTML.replace(',', '.'))); 
			}
			else if(cells.length == 2) {
				data.push([parseFloat(cells[0].innerHTML.replace(',', '.'))*timestampMultiplier,parseFloat(cells[1].innerHTML.replace(',', '.'))]);
			}
			else 
				data.push(parseFloat(cells[0].innerHTML.replace(',', '.')));
		}
		var yAxisIndex = 0;
		if(axis == 1){
			yAxisIndex = 1;
		}
		if(type == 'line' || type == "area"){
			seriesData.push(
				{	
					name: $("#Values_"+i).attr('title'),
					data: data,
					type: 'line',
					symbol: 'none',
					areaStyle: type == "area" ? {
						color: $("#color_"+i).css('backgroundColor'),
					} : null,
					step: $("#Values_"+i).attr('stairs')=="true" ? 'end' : '',
					lineStyle: {
						color: $("#color_"+i).css('backgroundColor'),
					},
					yAxisIndex
				}
			);
		}
		if(type == 'column'){
			seriesData.push(
				{	
					name: $("#Values_"+i).attr('title'),
					data: data,
					type: 'bar',
					itemStyle: {
						color: $("#color_"+i).css('backgroundColor'),
					}
				}
			);
		}
		if(type == 'pie'){
			seriesData.push({
				name: $("#Values_"+i).attr('title'),
				data: data,
				type: 'pie',
				label: {
					show: false
				},
				lineStyle: {
					color: $("#color_"+i).css('backgroundColor'),
				}
			},false);
		}
	}
}

function getTimeFormat(value){
	var xAxisTitle = $('#xAxisTitle').html();
	value = value / 1000;
	var hString = "";
	var mString = "";
	var sString = "";
	
	var hours = Math.floor(value / 3600);
	if (hours < 10)
		hString = "0" + hours;
	else
		hString = hours;

	var remaining = value - hours * 3600;
	var minutes = Math.floor(remaining / 60);
	if (minutes < 10)
		mString = "0" + minutes;
	else
		mString = minutes;

	var remaining = remaining - minutes * 60;
	if(remaining < 10)
		sString = "0" + remaining;
	else
		sString = remaining;

	if(xAxisTitle.indexOf("hh:mm:ss") != -1){
		return hString + ':' + mString + ':' + sString;
	}
	else if(xAxisTitle.indexOf("hh:mm") != -1){
		return hString + ':' + mString;
	}
	else{
		return mString + ':' + sString;
	}
}