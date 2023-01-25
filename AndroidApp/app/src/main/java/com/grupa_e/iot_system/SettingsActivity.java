package com.grupa_e.iot_system;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // display new server URL in setting field
        TextView url_display = (TextView) findViewById(R.id.textView2);
        TextView port_display = (TextView) findViewById(R.id.textView7);

        url_display.setText(MainActivity.server_URL.toString());
        port_display.setText(MainActivity.server_PORT.toString());
    }



    //go to 'Menu' view by button click
    public void button_onClick_menu(View view){
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
    }

    //set settings (save)
    public void button_onClick_Set_Url(View view){
        EditText input_URL = (EditText) findViewById(R.id.editTextURL);
        input_URL.setInputType(InputType.TYPE_CLASS_TEXT);
        MainActivity.server_URL = input_URL.getText().toString();

        EditText input_PORT = (EditText) findViewById(R.id.editTextPORT);
        input_PORT.setInputType(InputType.TYPE_CLASS_TEXT);
        MainActivity.server_PORT = input_PORT.getText().toString();

        // display settings
        TextView url_display = (TextView) findViewById(R.id.textView2);
        TextView port_display = (TextView) findViewById(R.id.textView7);

        url_display.setText(MainActivity.server_URL.toString());
        port_display.setText(MainActivity.server_PORT.toString());
    }
}
