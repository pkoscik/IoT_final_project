var globalReturn1;
var globalMode;
var globalAddr = "0.0.0.0:0000";

function setMode(mode){
    globalMode = mode;
}

function setAddress(){
    ip = document.getElementById("ip").value;
    port = document.getElementById("port").value;
    globalAddr = "http://"+ip+":"+port+"/";
    alert(globalAddr);
}

async function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, true);
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            //alert(JSON.parse(xmlHttp.responseText).value.x);
            globalReturn1 = JSON.parse(xmlHttp.responseText).value;
        }
    };
    xmlHttp.onerror = function() {
        console.error("The request couldn't be completed.");
    };
    xmlHttp.send();
}

var updateInterval = 1000;
var dataLength = 20;
function displayChart(){
    //POMIARY
    var dps = []; // dataPoints
    var chart = new CanvasJS.Chart("chartContainer", {
        title: {
            text: "Sensors values"
        },
        data: [{
            type: "line",
            dataPoints: dps
        },]
    });

    var xVal = 0;
    var yVal = 100; 
     // number of dataPoints visible at any point

    var updateChart = function (count) {
        if(document.getElementById("units").value != ""){
            count = count || 1;

            for (var j = 0; j < count; j++) {
                sensor = document.getElementById("values").value;
                unit = document.getElementById("units").value;
                if(sensor == "temperature"){
                    httpGet(globalAddr+sensor+"?unit="+unit+"&&source=temperature");
                }
                else{
                    httpGet(globalAddr+sensor+"?unit="+unit);
                }
                yVal = globalReturn1;
                //alert(yVal);
                dps.push({
                    x: xVal,
                    y: yVal
                });
                xVal++;
            }

            if (dps.length > dataLength) {
                dps.shift();
            }

            chart.render();
        }
    };

    updateChart(dataLength);
    setInterval(function(){updateChart()}, updateInterval);
}

function displayImu(){
//ORIENTATION
if(document.getElementById("values2").value == "orientation"){
var dataPoints1 = [];
var dataPoints2 = [];
var dataPoints3 = [];

var chart2 = new CanvasJS.Chart("chartContainer2", {
	zoomEnabled: true,
	title: {
		text: "RPY"
	},
	axisY:{
        title: "Degrees/Radians"
    },
	data: [{ 
            type: "line",
            name: "Roll",
            showInLegend: true,
            dataPoints: dataPoints1
		},
		{				
			type: "line",
			name: "Pitch" ,
            showInLegend: true,
			dataPoints: dataPoints2
	    },
        {				
            type: "line",
            name: "Yaw" ,
            showInLegend: true,
            dataPoints: dataPoints3
        }]
});

// initial value
var yValue1 = 0; 
var yValue2 = 0;
var yValue3 = 0;
var xValue = 0;
function updateChart2(count) {
	count = count || 1;
	for (var i = 0; i < count; i++) {
        unit = document.getElementById("orient").value;
        httpGet(globalAddr+"orientation?unit="+unit);
        yValue1 = globalReturn1.roll;
        yValue2 = globalReturn1.pitch;
        yValue3 = globalReturn1.yaw;
        dataPoints1.push({
            x: xValue,
            y: yValue1
        });
        dataPoints2.push({
            x: xValue,
            y: yValue2
        });
        dataPoints3.push({
            x: xValue,
            y: yValue3
        });
        xValue++;
	}
    if (dataPoints1.length > dataLength) {
        dataPoints1.shift();
        dataPoints2.shift();
        dataPoints3.shift();
    }
	chart2.render();
}
// generates first set of dataPoints 
updateChart2(100);	
setInterval(function(){updateChart2()}, updateInterval);
}
//GYROSCOPE
if(document.getElementById("values2").value == "gyroscope"){
var dataPoints4 = [];
var dataPoints5 = [];
var dataPoints6 = [];

var chart3 = new CanvasJS.Chart("chartContainer2", {
	zoomEnabled: true,
	title: {
		text: "RPY"
	},
	axisY:{
        title: "Degrees"
    },
	data: [{ 
            type: "line",
            name: "Roll",
            showInLegend: true,
            dataPoints: dataPoints4
		},
		{				
			type: "line",
			name: "Pitch" ,
            showInLegend: true,
			dataPoints: dataPoints5
	    },
        {				
            type: "line",
            name: "Yaw" ,
            showInLegend: true,
            dataPoints: dataPoints6
        }]
});

// initial value
var yValue4 = 0; 
var yValue5 = 0;
var yValue6 = 0;
var xValue2 = 0;

function updateChart3(count) {
	count = count || 1;
	for (var i = 0; i < count; i++) {
        httpGet(globalAddr+"gyroscope");
        yValue4 = globalReturn1.roll;
        yValue5 = globalReturn1.pitch;
        yValue6 = globalReturn1.yaw;
        dataPoints4.push({
            x: xValue2,
            y: yValue4
        });
        dataPoints5.push({
            x: xValue2,
            y: yValue5
        });
        dataPoints6.push({
            x: xValue2,
            y: yValue6
        });
        xValue2++;
	}
    if (dataPoints4.length > dataLength) {
        dataPoints4.shift();
        dataPoints5.shift();
        dataPoints6.shift();
    }
	chart3.render();
}
// generates first set of dataPoints 
updateChart3(100);	
setInterval(function(){updateChart3()}, updateInterval);
}
if(document.getElementById("values2").value == "accelerometer"){
//ACCELEROMETER
var dataPoints7 = [];
var dataPoints8 = [];
var dataPoints9 = [];

var chart4 = new CanvasJS.Chart("chartContainer2", {
	zoomEnabled: true,
	title: {
		text: "RPY"
	},
	axisY:{
        title: "Degrees"
    },
	data: [{ 
            type: "line",
            name: "Roll",
            showInLegend: true,
            dataPoints: dataPoints7
		},
		{				
			type: "line",
			name: "Pitch" ,
            showInLegend: true,
			dataPoints: dataPoints8
	    },
        {				
            type: "line",
            name: "Yaw" ,
            showInLegend: true,
            dataPoints: dataPoints9
        }]
});

// initial value
var yValue7 = 0; 
var yValue8 = 0;
var yValue9 = 0;
var xValue3 = 0;

function updateChart4(count) {
	count = count || 1;
	for (var i = 0; i < count; i++) {
        httpGet(globalAddr+"accelerometer");
        yValue4 = globalReturn1.roll;
        yValue5 = globalReturn1.pitch;
        yValue6 = globalReturn1.yaw;
        dataPoints7.push({
            x: xValue3,
            y: yValue7
        });
        dataPoints8.push({
            x: xValue3,
            y: yValue8
        });
        dataPoints9.push({
            x: xValue3,
            y: yValue9
        });
        xValue3++;
	}
    if (dataPoints7.length > dataLength) {
        dataPoints7.shift();
        dataPoints8.shift();
        dataPoints9.shift();
    }
	chart4.render();
}
// generates first set of dataPoints 
updateChart4(100);	
setInterval(function(){updateChart4()}, updateInterval);
}
if(document.getElementById("values2").value == "compass"){
//COMPASS
var dps2 = []; // dataPoints
var chart5 = new CanvasJS.Chart("chartContainer2", {
    title: {
        text: "Dynamic Data"
    },
    axisY:{
        title: "Degrees"
    },
    data: [{
        type: "line",
        dataPoints: dps2
    },]
});

var xVal2 = 0;
var yVal2 = 100; 
var dataLength = 20; // number of dataPoints visible at any point

var updateChart5 = function (count) {

    count = count || 1;

    for (var j = 0; j < count; j++) {
        httpGet(globalAddr+"compass");
        yVal2 = globalReturn1;
        dps2.push({
            x: xVal2,
            y: yVal2
        });
        xVal2++;
    }

    if (dps2.length > dataLength) {
        dps2.shift();
    }

    chart5.render();
};

updateChart5(dataLength);
setInterval(function(){updateChart5()}, updateInterval);
}
}

//RAW --------------------------------------------

function displayImuRaw(){
    //GYROSCOPE
    if(document.getElementById("values3").value == "gyroscope"){
    var dataPoints4 = [];
    var dataPoints5 = [];
    var dataPoints6 = [];
    
    var chart3 = new CanvasJS.Chart("chartContainer3", {
        zoomEnabled: true,
        title: {
            text: "RPY"
        },
        axisY:{
            title: "Radians/Second"
        },
        data: [{ 
                type: "line",
                name: "Roll",
                showInLegend: true,
                dataPoints: dataPoints4
            },
            {				
                type: "line",
                name: "Pitch" ,
                showInLegend: true,
                dataPoints: dataPoints5
            },
            {				
                type: "line",
                name: "Yaw" ,
                showInLegend: true,
                dataPoints: dataPoints6
            }]
    });
    
    // initial value
    var yValue4 = 0; 
    var yValue5 = 0;
    var yValue6 = 0;
    var xValue2 = 0;
    
    function updateChart3(count) {
        count = count || 1;
        for (var i = 0; i < count; i++) {
            httpGet(globalAddr+"gyroscope-raw");
            yValue4 = globalReturn1.roll;
            yValue5 = globalReturn1.pitch;
            yValue6 = globalReturn1.yaw;
            dataPoints4.push({
                x: xValue2,
                y: yValue4
            });
            dataPoints5.push({
                x: xValue2,
                y: yValue5
            });
            dataPoints6.push({
                x: xValue2,
                y: yValue6
            });
            xValue2++;
        }
        if (dataPoints4.length > dataLength) {
            dataPoints4.shift();
            dataPoints5.shift();
            dataPoints6.shift();
        }
        chart3.render();
    }
    // generates first set of dataPoints 
    updateChart3(100);	
    setInterval(function(){updateChart3()}, updateInterval);
    }
    if(document.getElementById("values3").value == "accelerometer"){
    //ACCELEROMETER
    var dataPoints7 = [];
    var dataPoints8 = [];
    var dataPoints9 = [];
    
    var chart4 = new CanvasJS.Chart("chartContainer3", {
        zoomEnabled: true,
        title: {
            text: "RPY"
        },
        axisY:{
            title: "G"
        },
        data: [{ 
                type: "line",
                name: "Roll",
                showInLegend: true,
                dataPoints: dataPoints7
            },
            {				
                type: "line",
                name: "Pitch" ,
                showInLegend: true,
                dataPoints: dataPoints8
            },
            {				
                type: "line",
                name: "Yaw" ,
                showInLegend: true,
                dataPoints: dataPoints9
            }]
    });
    
    // initial value
    var yValue7 = 0; 
    var yValue8 = 0;
    var yValue9 = 0;
    var xValue3 = 0;
    
    function updateChart4(count) {
        count = count || 1;
        for (var i = 0; i < count; i++) {
            httpGet(globalAddr+"accelerometer-raw");
            yValue4 = globalReturn1.roll;
            yValue5 = globalReturn1.pitch;
            yValue6 = globalReturn1.yaw;
            dataPoints7.push({
                x: xValue3,
                y: yValue7
            });
            dataPoints8.push({
                x: xValue3,
                y: yValue8
            });
            dataPoints9.push({
                x: xValue3,
                y: yValue9
            });
            xValue3++;
        }
        if (dataPoints7.length > dataLength) {
            dataPoints7.shift();
            dataPoints8.shift();
            dataPoints9.shift();
        }
        chart4.render();
    }
    // generates first set of dataPoints 
    updateChart4(100);	
    setInterval(function(){updateChart4()}, updateInterval);
    }
    if(document.getElementById("values3").value == "compass"){
    //COMPASS
    var dps2 = []; // dataPoints
    var chart5 = new CanvasJS.Chart("chartContainer3", {
        title: {
            text: "Dynamic Data"
        },
        axisY:{
            title: "Microtesla"
        },
        data: [{
            type: "line",
            dataPoints: dps2
        },]
    });
    
    var xVal2 = 0;
    var yVal2 = 100; 
    var dataLength = 20; // number of dataPoints visible at any point
    
    var updateChart5 = function (count) {
    
        count = count || 1;
    
        for (var j = 0; j < count; j++) {
            httpGet(globalAddr+"compass-raw");
            yVal2 = globalReturn1;
            dps2.push({
                x: xVal2,
                y: yVal2
            });
            xVal2++;
        }
    
        if (dps2.length > dataLength) {
            dps2.shift();
        }
    
        chart5.render();
    };
    
    updateChart5(dataLength);
    setInterval(function(){updateChart5()}, 1000);
    }
    }

function putData(url){
    fetch(url, {
        method: "PUT"
    });
}
    
window.addEventListener("DOMContentLoaded", function() {
    let boxes = document.querySelectorAll(".box");

    Array.from(boxes, function(box) {
        box.addEventListener("click", function() {
        var r = document.getElementById("red").value;
        var g = document.getElementById("green").value;
        var b = document.getElementById("blue").value;
        document.getElementById(this.classList[1]).style.backgroundColor = "rgb("+r+","+g+","+b+")";
        x = this.classList[1][1];
        y = this.classList[1][0];
        putData(globalAddr+"led-matrix?x="+x+"&y="+y+"&r="+r+"&g="+g+"&b="+b);
        });
    });
});
    
function reset(){
    httpGet(globalAddr+"joystick?mode=reset");
}

var intervalId = setInterval(function() {
    httpGet(globalAddr+"joystick?mode=data");
    var clicks = globalReturn1.clicks;
    var x = globalReturn1.x;
    var y = globalReturn1.y;
    if(x < 0){
        document.getElementById("l").style.backgroundColor = "green";
        document.getElementById("p").style.backgroundColor = "red";
        document.getElementById("s").style.backgroundColor = "red";
        document.getElementById("d").style.backgroundColor = "red";
        document.getElementById("g").style.backgroundColor = "red";
    }
    if(x > 0){
        document.getElementById("p").style.backgroundColor = "green";
        document.getElementById("l").style.backgroundColor = "red";
        document.getElementById("s").style.backgroundColor = "red";
        document.getElementById("d").style.backgroundColor = "red";
        document.getElementById("g").style.backgroundColor = "red";
    }
    /*if(x == 0){
        document.getElementById("l").style.backgroundColor = "red";
        document.getElementById("p").style.backgroundColor = "red";
    }
    if(y == 0){
        document.getElementById("g").style.backgroundColor = "red";
        document.getElementById("d").style.backgroundColor = "red";
    }*/
    if(y > 0){
        document.getElementById("g").style.backgroundColor = "green";
        document.getElementById("d").style.backgroundColor = "red";
        document.getElementById("s").style.backgroundColor = "red";
        document.getElementById("p").style.backgroundColor = "red";
        document.getElementById("l").style.backgroundColor = "red";
    }
    if(y < 0){
        document.getElementById("d").style.backgroundColor = "green";
        document.getElementById("g").style.backgroundColor = "red";
        document.getElementById("s").style.backgroundColor = "red";
        document.getElementById("p").style.backgroundColor = "red";
        document.getElementById("l").style.backgroundColor = "red";
    }
    if(x == 0 && y == 0 && clicks != 0){
        document.getElementById("s").style.backgroundColor = "green";
        document.getElementById("g").style.backgroundColor = "red";
        document.getElementById("d").style.backgroundColor = "red";
        document.getElementById("p").style.backgroundColor = "red";
        document.getElementById("l").style.backgroundColor = "red";
    }
    reset();
}, 500);

function changeUnits(){
    var el = document.getElementById("values").value;
    var uni = document.getElementById("units");
    if(el == "temperature"){
        uni.innerHTML = "";
        var celcius = document.createElement('option');
        celcius.text = celcius.value = "celcius";
        uni.append(celcius);
        var kelvin = document.createElement('option');
        kelvin.text = kelvin.value = "kelvin";
        uni.append(kelvin);
        var farenhait = document.createElement('option');
        farenhait.text = farenhait.value = "farenhait";
        uni.append(farenhait);
    }
    else if(el == "humidity"){
        uni.innerHTML = "";
        var percent = document.createElement('option');
        percent.text = percent.value = "percent";
        uni.append(percent);
        var digital = document.createElement('option');
        digital.text = digital.value = "digital";
        uni.append(digital);
    }
    else if(el == "pressure"){
        uni.innerHTML = "";
        var mmhg = document.createElement('option');
        mmhg.text = mmhg.value = "mmhg";
        uni.append(mmhg);
        var hpa = document.createElement('option');
        hpa.text = hpa.value = "hpa";
        uni.append(hpa);
    }
}