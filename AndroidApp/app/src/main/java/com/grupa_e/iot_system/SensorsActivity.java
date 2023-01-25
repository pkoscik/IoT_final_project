package com.grupa_e.iot_system;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SensorsActivity extends AppCompatActivity {

    String url = MainActivity.server_URL.toString() + ":"+ MainActivity.server_PORT.toString();
    String request_url = url.toString();

    String[] unit_suboptions = {};
    String data_source = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        // data option -------------------------------------------------------------------------- //
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        // main selection
        String[] data_options = {
                "Temperature(T)",
                "Temperature(H)",
                "Temperature(P)",
                "Humidity",
                "Pressure",
                "Orientation",
                "Compass",
                "Compass(RAW)",
                "Gyroscope",
                "Gyroscope(RAW)",
                "Accelerometer",
                "Accelerometer(RAW)"
        };

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, data_options);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, unit_suboptions);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        // first spinner handler
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if(selectedItem.equals("Temperature(T)")){
                    String[] suboption1 = {"F", "C", "K"};
                    unit_suboptions = suboption1;
                    data_source = "/temperature?source=temperature";
                    //request_url = url.toString() + "/temperature?source=temperature";

                }
                else if(selectedItem.equals("Temperature(H)")){
                    String[] suboption2 = {"F", "C", "K"};
                    unit_suboptions = suboption2;
                    data_source = "/temperature?source=humidity";
                    //request_url = url.toString() + "/temperature?source=humidity";

                }
                else if(selectedItem.equals("Temperature(P)")){
                    String[] suboption3 = {"F", "C", "K"};
                    unit_suboptions = suboption3;
                    data_source = "/temperature?source=pressure";
                    //request_url = url.toString() + "/temperature?source=pressure";

                }
                else if(selectedItem.equals("Humidity")){
                    String[] suboption4 = {"%", "digit"};
                    unit_suboptions = suboption4;
                    data_source = "/humidity";
                    //request_url = url.toString() + "/humidity?";

                }
                else if(selectedItem.equals("Pressure")){
                    String[] suboption5 = {"hPa", "mmHg"};
                    unit_suboptions = suboption5;
                    data_source = "/pressure";
                    //request_url = url.toString() + "/pressure?";

                }
                else if(selectedItem.equals("Orientation")){
                    String[] suboption6 = {"deg", "rad"};
                    unit_suboptions = suboption6;
                    data_source = "/orientation";
                    //request_url = url.toString() + "/orientation?";

                }
                else if(selectedItem.equals("Compass")){
                    String[] suboption7 = {"-"};
                    unit_suboptions = suboption7;
                    data_source = "/compass";
                    //request_url = url.toString() + "/compass";

                }
                else if(selectedItem.equals("Compass(RAW)")){
                    String[] suboption8 = {"-"};
                    unit_suboptions = suboption8;
                    data_source = "/compass-raw";
                    //request_url = url.toString() + "/compass-raw";

                }
                else if(selectedItem.equals("Gyroscope")){
                    String[] suboption9 = {"-"};
                    unit_suboptions = suboption9;
                    data_source = "/gyroscope";
                    //request_url = url.toString() + "/gyroscope";

                }
                else if(selectedItem.equals("Gyroscope(RAW)")){
                    String[] suboption10 = {"-"};
                    unit_suboptions = suboption10;
                    data_source = "/gyroscope-raw";
                    //request_url = url.toString() + "/gyroscope-raw";

                }
                else if(selectedItem.equals("Accelerometer")){
                    String[] suboption11 = {"-"};
                    unit_suboptions = suboption11;
                    data_source = "/accelerometer";
                    //request_url = url.toString() + "/accelerometer";

                }
                else if(selectedItem.equals("Accelerometer(RAW)")){
                    String[] suboption12 = {"-"};
                    unit_suboptions = suboption12;
                    data_source = "/accelerometer-raw";
                    //request_url = url.toString() + "/accelerometer-raw";
                }
                else{
                    //nothing
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                        SensorsActivity.this,
                        android.R.layout.simple_spinner_item, unit_suboptions);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // unit selection for spinner 2 (child of spinner 1)
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if(selectedItem.equals("C")){
                    request_url = url.toString() + data_source.toString() + "&unit=celcius";

                }
                else if(selectedItem.equals("F")){
                    request_url = url.toString() + data_source.toString() + "&unit=farenhait";

                }
                else if(selectedItem.equals("K")){
                    request_url = url.toString() + data_source.toString() + "&unit=kelvin";

                }
                else if(selectedItem.equals("%")){
                    request_url = url.toString() + data_source.toString() + "?unit=percent";

                }
                else if(selectedItem.equals("digit")){
                    request_url = url.toString() + data_source.toString() + "?unit=digital";

                }
                else if(selectedItem.equals("hPa")){
                    request_url = url.toString() + data_source.toString() + "?unit=hpa";

                }
                else if(selectedItem.equals("mmHg")){
                    request_url = url.toString() + data_source.toString() + "?unit=mmhg";

                }
                else if(selectedItem.equals("deg")){
                    request_url = url.toString() + data_source.toString() + "?unit=degrees";

                }
                else if(selectedItem.equals("rad")){
                    request_url = url.toString() + data_source.toString() + "?unit=radians";

                }
                else{
                    request_url = url.toString() + data_source.toString();
                    //nothing
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        // Request Queue
        //RequestQueue queue = Volley.newRequestQueue(this);

        //String url = MainActivity.server_URL;
        //String url = "http://192.168.131.1/demo/zadanie2.py";
/*
        //Send request.get and parse response
        //StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        queue.add(stringRequest);
*/
    }

    //go to 'Menu' view by button click
    public void button_onClick_menu(View view){
        startActivity(new Intent(SensorsActivity.this, MainActivity.class));
    }

    //go to 'Graph' view by button click
    public void button_onClick_graph(View view){
        //startActivity(new Intent(SensorsActivity.this, TempGraphActivity.class));
        startActivity(new Intent(SensorsActivity.this, HumidityGraphActivity.class));
    }

    //request new data
    public void button_onClick_GET(View view){

        final TextView text_data = (TextView) findViewById(R.id.textViewResponseData);


        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // json parser
                        try {
                            String JString = response;
                            JSONObject measurement = new JSONObject(JString);

                            String source = measurement.getString("source");
                            String timex = measurement.getString("time");
                            String unit = measurement.getString("unit");

                            long dv = Long.valueOf(timex)*1000;
                            Date df = new java.util.Date(dv);
                            String time = new SimpleDateFormat("MM dd, yyyy hh:mma", Locale.GERMAN).format(df);

                            if(source.equals("imu-accelerometer-raw") ||
                                    source.equals("imu-gyroscope-raw") ||
                                    source.equals("imu-comapss-raw")){
                                JSONObject valueJ = measurement.getJSONObject("value");
                                String x = valueJ.getString("x");
                                String y = valueJ.getString("y");
                                String z = valueJ.getString("z");

                                text_data.setText("Source: " + source.toString() + "\n"
                                        + "Time: " + time.toString() + "\n"
                                        + "Unit: " + unit.toString() + "\n"
                                        + "x: " + x.toString() + "\n"
                                        + "y: " + y.toString() + "\n"
                                        + "z: " + z.toString() + "\n");
                            }
                            else if(source.equals("imu-accelerometer") ||
                                    source.equals("imu-gyroscope") ||
                                    source.equals("imu-orientation")){
                                JSONObject valueJ = measurement.getJSONObject("value");
                                String roll = valueJ.getString("roll");
                                String pitch = valueJ.getString("pitch");
                                String yaw = valueJ.getString("yaw");

                                text_data.setText("Source: " + source.toString() + "\n"
                                        + "Time: " + time.toString() + "\n"
                                        + "Unit: " + unit.toString() + "\n"
                                        + "Roll: " + roll.toString() + "\n"
                                        + "Pitch: " + pitch.toString() + "\n"
                                        + "Yaw: " + yaw.toString() + "\n");

                            }
                            else{
                                String value = measurement.getString("value");

                                text_data.setText("Source: " + source.toString() + "\n"
                                        + "Time: " + time.toString() + "\n"
                                        + "Unit: " + unit.toString() + "\n"
                                        + "Value: " + value.toString() + "\n");
                            }
                        }
                        catch (JSONException e){
                            // don't plot
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                    }
                }
        );

        // Dodajemy obiekt StringRequest do RequestQueue
        queue.add(stringRequest);

    }

    // refresh data - resend get request
    /*
    public void button_onClick_Refresh(View view){
        // Sensors data - text fields
        final TextView response_temp = (TextView) findViewById(R.id.textView_temperature);
        final TextView response_humi = (TextView) findViewById(R.id.textView_humidity);
        final TextView response_press = (TextView) findViewById(R.id.textView_pressure);
        final TextView response_roll = (TextView) findViewById(R.id.textView_roll);
        final TextView response_pitch = (TextView) findViewById(R.id.textView_pitch);
        final TextView response_yaw = (TextView) findViewById(R.id.textView_yaw);

        // Request Queue
        RequestQueue queue = Volley.newRequestQueue(this);

        //String url = MainActivity.server_URL;
        String url = "http://192.168.131.1/demo/zadanie2.py";
        String endpoint_temp = MainActivity.server_URL + "/demo/zadanie2.py";
        String endpoint_humi = MainActivity.server_URL + "/demo/zadanie2.py";
        String endpoint_press = MainActivity.server_URL + "/demo/zadanie2.py";

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, endpoint_temp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response_temp.setText("Temperature: "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });


        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, endpoint_humi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response_humi.setText("Humidity: "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });


        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, endpoint_humi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response_press.setText("Pressure: "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });

        StringRequest stringRequest4 = new StringRequest(Request.Method.GET, endpoint_humi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response_roll.setText("Roll: "+ response.toString());
                        response_pitch.setText("Pitch: "+ response.toString());
                        response_yaw.setText("Yaw: "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });

        queue.add(stringRequest1);
        queue.add(stringRequest2);
        queue.add(stringRequest3);
        queue.add(stringRequest4);

    }*/



}