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
    "pitch": 134.99292494377005,
    "roll": 272.70787173355234,
    "source": "imu-orientation",
    "time": 1674514058,
    "unit": "degrees",
    "yaw": 270.0047167041533
}
```

### Compass
```
[GET]
/compass

Return:
{
    "north": 270.0047167041533,
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
    "magx": 9.646000057876,
    "magy": -2.128000012768,
    "magz": 40.950000245700004,
    "source": "imu-comapss-raw",
    "time": 1674514139,
    "unit": "microtesla"
}
```

### Gyroscope
```
[GET]
/gyroscope

Return:
{
    "pitch": 134.99292494377005,
    "roll": 272.70787173355234,
    "source": "imu-gyroscope",
    "time": 1674514211,
    "unit": "degrees",
    "yaw": 270.0047167041533
}
```

### Gyroscope RAW
```
[GET]
/gyroscope-raw

Return:
{
    "gyrox": 0.035000000087500004,
    "gyroy": -0.035000000087500004,
    "gyroz": -0.07000000017500001,
    "source": "imu-gyroscope-raw",
    "time": 1674514234,
    "unit": "radians-per-second"
}
```

### Accelerometer
```
[GET]
/accelerometer

Return:
{
    "pitch": 134.99292494377005,
    "roll": 272.70787173355234,
    "source": "imu-accelerometer",
    "time": 1674514280,
    "unit": "degrees",
    "yaw": 270.0047167041533
}
```

### Accelerometer RAW
```
[GET]
/accelerometer-raw

Return:
{
    "accx": -0.7095199918405201,
    "accy": 0.7078049918602426,
    "accz": -0.0350349995970975,
    "source": "imu-accelerometer-raw",
    "time": 1674514320,
    "unit": "G"
}
```

## Joystick Endpoint
```
[GET]
/joystick?
  mode={data,reset}

Return: {mode}
{
    "clicks": 14,
    "posx": -2,
    "posy": -3,
    "source": "joystick",
    "time": 1674514429,
    "unit": "position"
}
Returns an ammount of clicks since last request

Return: {reset}
{
    "clicks": 0,
    "posx": 0,
    "posy": 0,
    "source": "joystick",
    "time": 1674514439,
    "unit": "position"
}
Returns an zero ammount of clicks, resets the counter
```

## LED Matrix Endpoint
```
[GET]
/led-matrix?
  x={0...7}
  y={0...7}

Return:
{
    "rgb": [
        248,
        252,
        248
    ],
    "source": "led-matrix",
    "time": 1674514561,
    "unit": "rgb",
    "x": 1,
    "y": 1
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