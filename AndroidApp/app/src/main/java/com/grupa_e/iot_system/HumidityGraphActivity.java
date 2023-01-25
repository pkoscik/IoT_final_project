package com.grupa_e.iot_system;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

public class HumidityGraphActivity extends AppCompatActivity {

    // default
    //String url = MainActivity.server_URL.toString() + ":" + MainActivity.server_PORT;
    //String url = "http://192.168.131.1/demo/zadanie2.py";
    String url = MainActivity.server_URL.toString() + ":"+ MainActivity.server_PORT.toString();
    String request_url = url.toString();
    String data_source = "";

    String[] unit_suboptions = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_graph);

        // obiekt request queue ----------------------------------------------------------------- //
        RequestQueue queue = Volley.newRequestQueue(this);

        // obiekt handler do zapytania co 1000ms ------------------------------------------------ //
        final Handler handler = new Handler();
        final int delay = 1000;

        // graph -------------------------------------------------------------------------------- //
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>();

        // data option view spinners ------------------------------------------------------------ //
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        String[] data_options = {
                "Temperature(T)",
                "Temperature(H)",
                "Temperature(P)",
                "Humidity",
                "Pressure",
                "Orientation",
                "Compass",
                "Compass-raw",
                "Gyroscope",
                "Gyroscope-raw",
                "Accelerometer",
                "Accelerometer-raw"
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
                    //request_url = url.toString() + "/temperature?source=temperature";
                    data_source = "/temperature?source=temperature";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Temperature(H)")){
                    String[] suboption2 = {"F", "C", "K"};
                    unit_suboptions = suboption2;
                    //request_url = url.toString() + "/temperature?source=humidity";
                    data_source = "/temperature?source=humidity";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Temperature(P)")){
                    String[] suboption3 = {"F", "C", "K"};
                    unit_suboptions = suboption3;
                    //request_url = url.toString() + "/temperature?source=pressure";
                    data_source = "/temperature?source=pressure";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Humidity")){
                    String[] suboption4 = {"%", "digit"};
                    unit_suboptions = suboption4;
                    //request_url = url.toString() + "/humidity?";
                    data_source = "/humidity";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Pressure")){
                    String[] suboption5 = {"hPa", "mmHg"};
                    unit_suboptions = suboption5;
                    //request_url = url.toString() + "/pressure?";
                    data_source = "/pressure";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Orientation")){
                    String[] suboption6 = {"deg", "rad"};
                    unit_suboptions = suboption6;
                    //request_url = url.toString() + "/orientation?";
                    data_source = "/orientation";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Compass")){
                    String[] suboption7 = {"-"};
                    unit_suboptions = suboption7;
                    //request_url = url.toString() + "/compass";
                    data_source = "/compass";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Compass-raw")){
                    String[] suboption8 = {"-"};
                    unit_suboptions = suboption8;
                    //request_url = url.toString() + "/compass-raw";
                    data_source = "/compass-raw";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Gyroscope")){
                    String[] suboption9 = {"-"};
                    unit_suboptions = suboption9;
                    //request_url = url.toString() + "/gyroscope";
                    data_source = "/gyroscope";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Gyroscope-raw")){
                    String[] suboption10 = {"-"};
                    unit_suboptions = suboption10;
                    //request_url = url.toString() + "/gyroscope-raw";
                    data_source = "/gyroscope-raw";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Accelerometer")){
                    String[] suboption11 = {"-"};
                    unit_suboptions = suboption11;
                    //request_url = url.toString() + "/accelerometer";
                    data_source = "/accelerometer";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else if(selectedItem.equals("Accelerometer-raw")){
                    String[] suboption12 = {"-"};
                    unit_suboptions = suboption12;
                    //request_url = url.toString() + "/accelerometer-raw";
                    data_source = "/accelerometer-raw";

                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }
                else{
                    //nothing
                    series1.resetData(new DataPoint[]{});
                    series2.resetData(new DataPoint[]{});
                    series3.resetData(new DataPoint[]{});
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                        HumidityGraphActivity.this,
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
                series1.resetData(new DataPoint[]{});

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // http get - request for graph data (cycle)
        handler.postDelayed(new Runnable() {
            public void run() {

                // Tworzymy obiekt StringRequest
                StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // json parser

                                try {
                                    String JString = response;
                                    JSONObject measurement = new JSONObject(JString);
                                    String source = measurement.getString("source");
                                    Long time = measurement.getLong("time");

                                    if(source.equals("imu-accelerometer-raw") ||
                                            source.equals("imu-gyroscope-raw") ||
                                            source.equals("imu-compass-raw")){
                                        JSONObject valueJ = measurement.getJSONObject("value");
                                        double x = valueJ.getDouble("x");
                                        double y = valueJ.getDouble("y");
                                        double z = valueJ.getDouble("z");

                                        series1.appendData(new DataPoint(time, x),
                                                true, 10);
                                        series1.setTitle("x");
                                        series2.appendData(new DataPoint(time, y),
                                                true, 10);
                                        series2.setTitle("y");
                                        series3.appendData(new DataPoint(time, z),
                                                true, 10);
                                        series3.setTitle("z");
                                    }
                                    else if(source.equals("imu-accelerometer") ||
                                            source.equals("imu-gyroscope") ||
                                            source.equals("imu-orientation")){
                                        JSONObject valueJ = measurement.getJSONObject("value");
                                        double roll = valueJ.getDouble("roll");
                                        double pitch = valueJ.getDouble("pitch");
                                        double yaw = valueJ.getDouble("yaw");

                                        series1.appendData(new DataPoint(time, roll),
                                                true, 10);
                                        series1.setTitle("roll");
                                        series2.appendData(new DataPoint(time, pitch),
                                                true, 10);
                                        series2.setTitle("pitch");
                                        series3.appendData(new DataPoint(time, yaw),
                                                true, 10);
                                        series3.setTitle("yaw");

                                    }
                                    else{
                                        double value = measurement.getDouble("value");
                                        series1.appendData(new DataPoint(time, value),
                                                true, 10);
                                        series1.setTitle("value");
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
                graph.removeAllSeries();
                queue.add(stringRequest);
                graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                graph.getLegendRenderer().setVisible(true);
                //graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                graph.getLegendRenderer().setBackgroundColor(Color.WHITE);
                graph.getLegendRenderer().setTextSize(30);
                graph.getLegendRenderer().setPadding(10);
                graph.getLegendRenderer().setFixedPosition(20,20);
                graph.getLegendRenderer().setVisible(true);

                handler.postDelayed(this, delay);
                series1.setDrawDataPoints(true);
                if (!series2.isEmpty()){
                    series2.setDrawDataPoints(true);
                    series2.setColor(Color.GREEN);
                    graph.addSeries(series2);
                }
                if (!series3.isEmpty()) {
                    series3.setDrawDataPoints(true);
                    series3.setColor(Color.RED);
                    graph.addSeries(series3);
                }
                graph.addSeries(series1);
            }
        }, delay);

    }


    //go to 'Menu' view by button click
    public void button_onClick_menu(View view){
        startActivity(new Intent(HumidityGraphActivity.this, SensorsActivity.class));
    }
}