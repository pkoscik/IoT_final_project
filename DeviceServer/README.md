# RESTful SenseHAT server

## Usage

0. Install required packages
  - Flask
  - SenseEMU
1. Run `bootstrap_env.sh`
2. _Enjoy_

## Environmental Endpoints

### Temperature
```text
[GET]
/temperature?
  unit={celcius,kelvin,farenhait}
  source={temperature,humidity,pressure}

Return:
{
    "source": "temperature-sensor",
    "time": 1674513445,
    "unit": "celcius",
    "value": 41.4375
}
```

### Humidity
```text
[GET]
/humidity?
  unit={percent,digital}

Return:
{
    "source": "humidity-sensor",
    "time": 1674513653,
    "unit": "percent",
    "value": 45.015625
}
```

### Pressure
```text
[GET]
/pressure?
  unit={mmhg,hpa}

Return:
{
    "source": "pressure-sensor",
    "time": 1674513758,
    "unit": "mmhg",
    "value": 1350.3416921386718
}
```

## IMU Endpoints

### Orientation
```text
[GET]
/orientation?
  unit={degrees,radians}

{
    "source": "imu-orientation",
    "time": 1674514058,
    "unit": "degrees",
    "value" : {
      "roll": 272.70787173355234,
      "pitch": 134.99292494377005,
      "yaw": 270.0047167041533
    }
}
```

### Compass
```
[GET]
/compass

Return:
{
    "value": 270.0047167041533,
    "source": "imu-comapss",
    "time": 1674514088,
    "unit": "degrees"
}
```

### Compass RAW
```
[GET]
/compass-raw

Return:
{
    "source": "imu-comapss-raw",
    "time": 1674514139,
    "unit": "microtesla",
    "value" : {
      "x": 9.646000057876,
      "y": -2.128000012768,
      "z": 40.950000245700004
    }
}
```

### Gyroscope
```
[GET]
/gyroscope

Return:
{
    "source": "imu-gyroscope",
    "time": 1674514211,
    "unit": "degrees",
    "value" : {
      "roll": 272.70787173355234,
      "pitch": 134.99292494377005,
      "yaw": 270.0047167041533
    }
}
```

### Gyroscope RAW
```
[GET]
/gyroscope-raw

Return:
{
    "source": "imu-gyroscope-raw",
    "time": 1674514234,
    "unit": "radians-per-second"
    "value" : {
      "x": 0.035000000087500004,
      "y": 0.035000000087500004,
      "z": 0.07000000017500001
    }
}
```

### Accelerometer
```
[GET]
/accelerometer

Return:
{
    "source": "imu-accelerometer",
    "time": 1674514280,
    "unit": "degrees",
    "value" : {
      "roll": 272.70787173355234,
      "pitch": 134.99292494377005,
      "yaw": 270.0047167041533
    }
}
```

### Accelerometer RAW
```
[GET]
/accelerometer-raw

Return:
{
    "source": "imu-accelerometer-raw",
    "time": 1674514320,
    "unit": "G",
    "value" : {
      "x": 0.035000000087500004,
      "y": 0.035000000087500004,
      "z": 0.07000000017500001
    }
}
```

## Joystick Endpoint
```
[GET]
/joystick?
  mode={data,reset}

Return: {mode}
{
    "source": "joystick",
    "time": 1674514429,
    "unit": "position",
    "value" : {
      "clicks": 14,
      "x": -2,
      "y": -3
    }
}
Returns an ammount of clicks since last request

Return:
{
    "success": true
}
Resets the counter
```

## LED Matrix Endpoint
```
[GET]
/led-matrix?
  x={0...7}
  y={0...7}

Return:
{
    "source": "led-matrix",
    "time": 1674514561,
    "unit": "rgb",
    "value" : {
      "x": 1,
      "y": 1,
      "rgb": [
          248,
          252,
          248
      ]
    }
}

[PUT]
/led-matrix?
  x={0...7}
  y={0...7}
  r={0...255}
  g={0...255}
  b={0...255}

Return:
{
    "success": true
}
```