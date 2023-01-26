<!DOCTYPE html>

<html>

<head>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="scripts.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>
    <div class="container"></div>
        <ul class="nav nav-tabs" id="tabs">
            <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
            <li><a data-toggle="tab" href="#menu1">Matrix</a></li>
            <li><a data-toggle="tab" href="#menu2" onclick="displayChart()">Live charts</a></li>
            <li><a data-toggle="tab" href="#menu3">Joystick</a></li>
            <li><a data-toggle="tab" href="#menu4" onclick="displayImu()">IMU</a></li>
            <li><a data-toggle="tab" href="#menu5" onclick="displayImuRaw()">IMU RAW</a></li>
        </ul>
        <div class="tab-content">
        <div id="home" class="tab-pane fade in active">
            IP: <input type="text" id="ip"><br>
            PORT: <input type="text" id="port"><br>
            <button type="button" onclick="setAddress()">SELECT</button>
        </div>
        <div id="menu1" class="tab-pane fade">
            RED: <input type="text" id="red"><br>
            GREEN: <input type="text" id="green"><br>
            BLUE: <input type="text" id="blue"><br>
            <div class="wrapper">
                <div class="box 00" id="00"></div>
                <div class="box 01" id="01"></div>
                <div class="box 02" id="02"></div>
                <div class="box 03" id="03"></div>
                <div class="box 04" id="04"></div>
                <div class="box 05" id="05"></div>
                <div class="box 06" id="06"></div>
                <div class="box 07" id="07"></div>

                <div class="box 10" id="10"></div>
                <div class="box 11" id="11"></div>
                <div class="box 12" id="12"></div>
                <div class="box 13" id="13"></div>
                <div class="box 14" id="14"></div>
                <div class="box 15" id="15"></div>
                <div class="box 16" id="16"></div>
                <div class="box 17" id="17"></div>

                <div class="box 20" id="20"></div>
                <div class="box 21" id="21"></div>
                <div class="box 22" id="22"></div>
                <div class="box 23" id="23"></div>
                <div class="box 24" id="24"></div>
                <div class="box 25" id="25"></div>
                <div class="box 26" id="26"></div>
                <div class="box 27" id="27"></div>

                <div class="box 30" id="30"></div>
                <div class="box 31" id="31"></div>
                <div class="box 32" id="32"></div>
                <div class="box 33" id="33"></div>
                <div class="box 34" id="34"></div>
                <div class="box 35" id="35"></div>
                <div class="box 36" id="36"></div>
                <div class="box 37" id="37"></div>

                <div class="box 40" id="40"></div>
                <div class="box 41" id="41"></div>
                <div class="box 42" id="42"></div>
                <div class="box 43" id="43"></div>
                <div class="box 44" id="44"></div>
                <div class="box 45" id="45"></div>
                <div class="box 46" id="46"></div>
                <div class="box 47" id="47"></div>

                <div class="box 50" id="50"></div>
                <div class="box 51" id="51"></div>
                <div class="box 52" id="52"></div>
                <div class="box 53" id="53"></div>
                <div class="box 54" id="54"></div>
                <div class="box 55" id="55"></div>
                <div class="box 56" id="56"></div>
                <div class="box 57" id="57"></div>

                <div class="box 60" id="60"></div>
                <div class="box 61" id="61"></div>
                <div class="box 62" id="62"></div>
                <div class="box 63" id="63"></div>
                <div class="box 64" id="64"></div>
                <div class="box 65" id="65"></div>
                <div class="box 66" id="66"></div>
                <div class="box 67" id="67"></div>

                <div class="box 70" id="70"></div>
                <div class="box 71" id="71"></div>
                <div class="box 72" id="72"></div>
                <div class="box 73" id="73"></div>
                <div class="box 74" id="74"></div>
                <div class="box 75" id="75"></div>
                <div class="box 76" id="76"></div>
                <div class="box 77" id="77"></div>
            </div>
        </div>
        <div id="menu2" class="tab-pane fade">
            <select name="values" id="values" onchange="changeUnits()">
                <option value="temperature">temperature</option>
                <option value="pressure">pressure</option>
                <option value="humidity">humidity</option>
            </select>
            <select name="units" id="units"></select>
            <div id="chartContainer" style="height: 370px; width:100%;"></div>
            <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
        </div>
        <div id="menu3" class="tab-pane fade">
            <button type="button" onclick="reset()">RESET</button>
            <div class="wrapper2">
                <div class="box n0" id="n0" style="background-color: white"></div>
                <div class="box g" id="g"style></div>
                <div class="box n1" id="n1" style="background-color: white"></div>
                <div class="box l" id="l"></div>
                <div class="box s" id="s"></div>
                <div class="box p" id="p"></div>
                <div class="box n2" id="n2" style="background-color: white"></div>
                <div class="box d" id="d"></div>
                <div class="box n3" id="n3" style="background-color: white"></div>
            </div>
        </div>
        <div id="menu4" class="tab-pane fade">
            <select name="values2" id="values2">
                <option value="orientation">orientation</option>
                <option value="gyroscope">gyroscope</option>
                <option value="accelerometer">accelerometer</option>
                <option value="compass">compass</option>
            </select>
            <select name="orient" id="orient">
                <option value="degrees">degrees</option>
                <option value="radians">radians</option>
            </select>
            <div id="chartContainer2" style="height: 370px; width:100%;"></div>
            <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> 
        </div>
        <div id="menu5" class="tab-pane fade">
            <select name="values3" id="values3">
                <option value="gyroscope">gyroscope</option>
                <option value="accelerometer">accelerometer</option>
                <option value="compass">compass</option>
            </select>
            <div id="chartContainer3" style="height: 370px; width:100%;"></div>
            <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script> 
        </div>
    </div>
</body>

</html>