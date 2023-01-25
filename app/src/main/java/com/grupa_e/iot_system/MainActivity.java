package com.grupa_e.iot_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    // main application default parameters ------------------------------------------------------ //
    public static String server_URL = "http://192.168.78.134";
    public static String server_PORT = "5000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //go to 'Settings' view by button click
    public void button_onClick_settings(View view){
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    //go to 'Joystick' view by button click
    public void button_onClick_joystick_view(View view){
        startActivity(new Intent(MainActivity.this, JoystickActivity.class));
    }

    //go to 'Sensors' view by button click
    public void button_onClick_sensors_view(View view){
        startActivity(new Intent(MainActivity.this, SensorsActivity.class));
    }

    //go to 'LED' view by button click
    public void button_onClick_led_view(View view){
        startActivity(new Intent(MainActivity.this, LedActivity_X.class));
    }
}

