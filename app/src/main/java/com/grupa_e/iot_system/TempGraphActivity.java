package com.grupa_e.iot_system;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class TempGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_graph);

        // obiekt request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        final TextView response_data = (TextView) findViewById(R.id.textView11);

        // obiekt handler do zapytania co 1000ms
        final Handler handler = new Handler();
        final int delay = 1000;

        String url = "http://192.168.131.1/demo/zadanie2.py";

        // graph
        GraphView graph = (GraphView) findViewById(R.id.graph1);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();


        handler.postDelayed(new Runnable() {
            public void run() {

                // Tworzymy obiekt StringRequest
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // graph test
                                double x = System.currentTimeMillis();
                                double y = Double.parseDouble(response);

                                series.appendData(new DataPoint(x, y), true, 10);

                                response_data.setText("Response is: \n"+ response.toString());

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
                handler.postDelayed(this, delay);
                graph.addSeries(series);
            }
        }, delay);
    }

    //go to 'Menu' view by button click
    public void button_onClick_menu(View view){
        startActivity(new Intent(TempGraphActivity.this, SensorsActivity.class));
    }
}

