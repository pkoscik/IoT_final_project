from flask import Flask, request, abort
from sense_emu import SenseHat, ACTION_PRESSED, ACTION_HELD, ACTION_RELEASED
import time
import json

app = Flask(__name__)

# Create an global instance of SenseEMU
sense = SenseHat()

class JsonWrapper:
    def __init__(self):
        self.time = round(time.time())

    time = 0
    source = ""
    unit = ""

#region Enviromental Sensors Templates
class TemperatureData(JsonWrapper):
    def __init__(self):
        super(TemperatureData, self).__init__()
    value = 0.0

class HumidityData(JsonWrapper):
    def __init__(self):
        super(HumidityData, self).__init__()
    value = 0.0

class PressureData(JsonWrapper):
    def __init__(self):
        super(PressureData, self).__init__()
    value = 0.0
#endregion

#region IMU Sensors Templates
class OrientationData(JsonWrapper):
    def __init__(self):
        super(OrientationData, self).__init__()
    roll = 0.0
    pitch = 0.0
    yaw = 0.0

class CompassData(JsonWrapper):
    def __init__(self):
        super(CompassData, self).__init__()
    north = 0.0

class CompassRawData(JsonWrapper):
    def __init__(self):
        super(CompassRawData, self).__init__()
    magx = 0.0
    magy = 0.0
    magz = 0.0

class GyroscopeData(JsonWrapper):
    def __init__(self):
        super(GyroscopeData, self).__init__()
    roll = 0.0
    pitch = 0.0
    yaw = 0.0

class GyroscopeRawData(JsonWrapper):
    def __init__(self):
        super(GyroscopeRawData, self).__init__()
    gyrox = 0.0
    gyroy = 0.0
    gyroz = 0.0

class AccelerometerData(JsonWrapper):
    def __init__(self):
        super(AccelerometerData, self).__init__()
    roll = 0.0
    pitch = 0.0
    yaw = 0.0

class AccelerometerRawData(JsonWrapper):
    def __init__(self):
        super(AccelerometerRawData, self).__init__()
    accx = 0.0
    accy = 0.0
    accz = 0.0
#endregion

#region Joystick Template
class JoystickData(JsonWrapper):
    def __init__(self):
        super(JoystickData, self).__init__()
    posx = 0
    posy = 0
    clicks = 0

#endregion

#region LED Matrix Template
class LedMatrixData(JsonWrapper):
    def __init__(self):
        super(LedMatrixData, self).__init__()
    x = 0
    y = 0
    rgb = [0,0,0]
#endregion

@app.route('/')
def hello():
    return '''
    <h1> REST-ful SenseHAT(EMU) Server</h1>
    <p> REST-ful, as in, you'll need a rest after reading this POC codebase<p>
    '''

''' Verify correct parameters
Checks if the `value` exists in a `supported_list`

Returns: nothing upon success, 501 on failure
'''
def check_supported(value, supported_list):
    if value not in supported_list:
        abort(501)

#region Enviromental Sensors Endpoints
''' Temperature

This endpoint returns a temperature reading,
from a selected source in a selected unit.

GET parameters:
  - source  {'temperature', 'humidity', 'pressure'}
  - unit    {'celcius', 'farenhait', 'kelvin'}

Returns: JSON temperature response upon success, 501 on failure
'''
@app.route('/temperature', methods=['GET'])
def temperature():
    source = request.args.get('source')
    unit = request.args.get('unit')
    check_supported(source, ['temperature', 'humidity', 'pressure'])
    check_supported(unit, ['celcius', 'kelvin', 'farenhait'])

    data = TemperatureData()
    multiplier = 0.0
    addend = 0.0
    temperature = 0.0

    if unit == 'celcius':
        multiplier = 1.0
        addend = 0.0
    if unit == 'kelvin':
        multiplier = 1.0
        addend = 273.15
    if unit == 'farenhait':
        multiplier = 1.8
        addend = 32
    data.unit = unit

    if source == 'temperature':
        temperature = sense.get_temperature()
        data.source = "temperature-sensor"
    if source == 'pressure':
        temperature = sense.get_temperature_from_pressure()
        data.source = "pressure-temperature-sensor"
    if source == 'humidity':
        temperature = sense.get_temperature_from_humidity()
        data.source = "humidity-temperature-sensor"
    data.value = (temperature * multiplier) + addend

    return json.dumps(data, default=vars)


''' Humidity

This endpoint returns a relative humidity (RH) reading.

GET parameters: NONE

Returns: JSON relative humidity response upon success, 501 on failure
'''
@app.route('/humidity', methods=['GET'])
def humidity():
    unit = request.args.get('unit')
    check_supported(unit, ['percent', 'digital'])

    data = HumidityData()
    data.source = 'humidity-sensor'

    multiplier = 1.0
    if unit == 'percent':
        multiplier = 1.0
    if unit == 'digital':
        multiplier = 0.01
    data.unit = unit

    data.value = sense.get_humidity() * multiplier
    return json.dumps(data, default=vars)


''' Pressure

This endpoint returns a pressure reading.

GET parameters:
  - unit {mbar, hPa}

Returns: JSON pressure response upon success, 501 on failure
'''
@app.route('/pressure', methods=['GET'])
def pressure():
    unit = request.args.get('unit')
    check_supported(unit, ['mmhg', 'hpa'])

    data = PressureData()
    data.source = 'pressure-sensor'

    multiplier = 1.0
    if unit == 'mmhg':
        multiplier = 1.333
    if unit == 'hpa':
        multiplier = 1
    data.unit = unit

    data.value = sense.get_pressure() * multiplier

    return json.dumps(data, default=vars)
#endregion

#region IMU Sensors Endpoints
'''Orientation

This endpoint returns a orientation reading
in a selected unit

GET parameters:
  - unit {degrees, radians}

Returns: JSON with orientation readings, 501 on failure
'''
@app.route('/orientation', methods=['GET'])
def orientation():
    unit = request.args.get('unit')
    check_supported(unit, ['degrees','radians'])

    sense.set_imu_config(False, True, False)
    data = OrientationData()
    data.source = 'imu-orientation'

    if unit == 'degrees':
        orientation_data = sense.get_orientation_degrees()
    if unit == 'radians':
        orientation_data = sense.get_orientation_radians()
    data.unit = unit

    data.roll = orientation_data['roll']
    data.pitch = orientation_data['pitch']
    data.yaw = orientation_data['yaw']

    return json.dumps(data, default=vars)


''' Compass

This endpoint returns a compass value (north direction).

GET parameters: NONE

Returns: JSON compass response upon success, 501 on failure
'''
@app.route('/compass', methods=['GET'])
def compass():
    sense.set_imu_config(False, True, False)
    data = CompassData()
    data.source = 'imu-comapss'
    data.unit = 'degrees'
    data.north = sense.get_compass()
    return json.dumps(data, default=vars)


''' Compass Raw

This endpoint returns a compass raw values
the magnetic intensity of the axis in microteslas.

GET parameters: NONE

Returns: JSON compass response upon success, 501 on failure
'''
@app.route('/compass-raw', methods=['GET'])
def compass_raw():
    sense.set_imu_config(False, True, False)
    data = CompassRawData()
    data.source = 'imu-comapss-raw'
    data.unit = 'microtesla'
    compass_raw_data = sense.get_compass_raw()
    data.magx = compass_raw_data['x']
    data.magy = compass_raw_data['y']
    data.magz = compass_raw_data['z']
    return json.dumps(data, default=vars)


''' Gyroscope

This endpoint returns a gyroscope value (r/p/y).

GET parameters: NONE

Returns: JSON gyroscope response upon success, 501 on failure
'''
@app.route('/gyroscope', methods=['GET'])
def gyroscope():
    sense.set_imu_config(False, True, False)
    data = GyroscopeData()
    data.source = 'imu-gyroscope'
    data.unit = 'degrees'
    gyroscope_data = sense.get_gyroscope()
    data.roll = gyroscope_data['roll']
    data.pitch = gyroscope_data['pitch']
    data.yaw = gyroscope_data['yaw']
    return json.dumps(data, default=vars)


''' Gyroscope Raw

This endpoint returns a gyroscope raw values
the magnetic intensity of the axis in microteslas.

GET parameters: NONE

Returns: JSON compass response upon success, 501 on failure
'''
@app.route('/gyroscope-raw', methods=['GET'])
def gyroscope_raw():
    sense.set_imu_config(False, True, False)
    data = GyroscopeRawData()
    data.source = 'imu-gyroscope-raw'
    data.unit = 'radians-per-second'
    gyroscope_raw_data = sense.get_gyroscope_raw()
    data.gyrox = gyroscope_raw_data['x']
    data.gyroy = gyroscope_raw_data['y']
    data.gyroz = gyroscope_raw_data['z']
    return json.dumps(data, default=vars)


''' Accelerometer

This endpoint returns a accelerometer value (r/p/y).

GET parameters: NONE

Returns: JSON accelerometer response upon success, 501 on failure
'''
@app.route('/accelerometer', methods=['GET'])
def accelerometer():
    sense.set_imu_config(False, False, True)
    data = AccelerometerData()
    data.source = 'imu-accelerometer'
    data.unit = 'degrees'
    gyroscope_data = sense.get_accelerometer()
    data.roll = gyroscope_data['roll']
    data.pitch = gyroscope_data['pitch']
    data.yaw = gyroscope_data['yaw']
    return json.dumps(data, default=vars)


''' Accelerometer Raw

This endpoint returns a accelerometer raw values
representing the acceleration intensity of the axis in Gs.

GET parameters: NONE

Returns: JSON raw accelerometer response upon success, 501 on failure
'''
@app.route('/accelerometer-raw', methods=['GET'])
def accelerometer_raw():
    sense.set_imu_config(False, False, True)
    data = GyroscopeRawData()
    data.source = 'imu-accelerometer-raw'
    data.unit = 'G'
    accelerometer_raw_data = sense.get_accelerometer_raw()
    data.accx = accelerometer_raw_data['x']
    data.accy = accelerometer_raw_data['y']
    data.accz = accelerometer_raw_data['z']
    return json.dumps(data, default=vars)
#endregion

#region Joystick Endpoint
joystick_x = 0
joystick_y = 0
clicks = 0

def pushed_up(event):
    global joystick_y
    if event.action != ACTION_RELEASED:
        joystick_y = joystick_y + 1

def pushed_down(event):
    global joystick_y
    if event.action != ACTION_RELEASED:
        joystick_y = joystick_y - 1

def pushed_left(event):
    global joystick_x
    if event.action != ACTION_RELEASED:
        joystick_x = joystick_x - 1

def pushed_right(event):
    global joystick_x
    if event.action != ACTION_RELEASED:
        joystick_x = joystick_x + 1

def pushed_any(event):
    global clicks
    if event.action != ACTION_RELEASED:
        clicks = clicks + 1

sense.stick.direction_up = pushed_up
sense.stick.direction_down = pushed_down
sense.stick.direction_left = pushed_left
sense.stick.direction_right = pushed_right
sense.stick.direction_any = pushed_any

@app.route('/joystick', methods=['GET'])
def joystick():
    mode = request.args.get('mode')
    check_supported(mode, ['data', 'reset'])

    global joystick_y
    global joystick_x
    global clicks

    if mode == 'reset':
        joystick_x = 0
        joystick_y = 0
        clicks = 0

    data = JoystickData()
    data.source = 'joystick'
    data.unit = 'position'

    data.posx = joystick_x
    data.posy = joystick_y
    data.clicks = clicks

    return json.dumps(data, default=vars)
#endregion

#region LED Matrix Endpoints
def try_parse_pos(xstr, ystr):
    x = 0
    y = 0

    try:
        x = int(xstr)
        y = int(ystr)
    except ValueError:
        abort(400)

    if not 0 <= x <= 7:
        abort(400)
    if not 0 <= y <= 7:
        abort(400)

    return (x,y)

def try_parse_rgb(r_str, g_str, b_str):
    r = 0
    g = 0
    b = 0

    try:
        r = int(r_str)
        g = int(g_str)
        b = int(b_str)
    except Exception as e:
        abort(400)

    rgb_list = [r,g,b]

    for color in rgb_list:
        if color > 255:
            abort(400)

    return rgb_list

@app.route('/led-matrix', methods=['GET', 'PUT'])
def matrix():
    x_str = request.args.get('x')
    y_str = request.args.get('y')
    pos = try_parse_pos(x_str, y_str)

    if request.method == 'GET':
        data = LedMatrixData()
        data.source = 'led-matrix'
        data.unit = 'rgb'
        data.x = pos[0]
        data.y = pos[1]
        data.rgb = sense.get_pixel(*pos)
        return json.dumps(data, default=vars)

    if request.method ==  'PUT':
        r_str = request.args.get('r')
        g_str = request.args.get('g')
        b_str = request.args.get('b')
        color = try_parse_rgb(r_str, g_str, b_str)
        sense.set_pixel(*pos, color)
        return json.dumps({'success':True}), 200, {'ContentType':'application/json'}
#endregion