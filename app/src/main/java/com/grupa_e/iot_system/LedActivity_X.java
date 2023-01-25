package com.grupa_e.iot_system;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LedActivity_X extends AppCompatActivity {

    String url = MainActivity.server_URL.toString() + ":"+ MainActivity.server_PORT.toString();
    String request_url = url.toString();
    String x = "0";
    String y = "0";

    String r = "0";
    String g = "0";
    String b = "0";


    private View colorView;
    private LedModel preview;

    private View colorView2;
    private LedModel preview2;

    private SeekBar seekbar_r;
    private SeekBar seekbar_g;
    private SeekBar seekbar_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_x);
        colorView = findViewById(R.id.colorView);
        colorView2 = findViewById(R.id.colorView3);

        preview = new LedModel();
        preview2 = new LedModel();

        // data option -------------------------------------------------------------------------- //
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        final TextView text_r = (TextView) findViewById(R.id.textViewR);
        final TextView text_g = (TextView) findViewById(R.id.textViewG);
        final TextView text_b = (TextView) findViewById(R.id.textViewB);


        seekbar_r = (SeekBar) findViewById(R.id.seekBar2);
        seekbar_r.setProgressTintList(ColorStateList.valueOf(Color.RED));
        seekbar_r.setThumbTintList(ColorStateList.valueOf(Color.RED));

        seekbar_g = (SeekBar) findViewById(R.id.seekBar3);
        seekbar_g.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        seekbar_g.setThumbTintList(ColorStateList.valueOf(Color.GREEN));

        seekbar_b = (SeekBar) findViewById(R.id.seekBar4);
        seekbar_b.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
        seekbar_b.setThumbTintList(ColorStateList.valueOf(Color.BLUE));


        // main selection
        String[] data_options = {
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7"
        };

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, data_options);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, data_options);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                x = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 y = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        seekbar_r.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle progress changes here
                r = Integer.toString(progress);
                text_r.setText("R: " + r.toString());

                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle start tracking touch here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle stop tracking touch here
                preview.R = progressChangedValue;
                setLedViewColor(colorView, preview.getColor());

            }
        });

        seekbar_g.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle progress changes here
                g = Integer.toString(progress);
                text_g.setText("G: " + g.toString());

                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle start tracking touch here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle stop tracking touch here
                preview.G = progressChangedValue;
                setLedViewColor(colorView, preview.getColor());
            }
        });

        seekbar_b.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle progress changes here
                b = Integer.toString(progress);
                text_b.setText("B: " + b.toString());

                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle start tracking touch here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle stop tracking touch here
                preview.B = progressChangedValue;
                setLedViewColor(colorView, preview.getColor());

            }
        });

    }

    //go to 'Menu' view by button click
    public void button_onClick_menu(View view){
        startActivity(new Intent(LedActivity_X.this, MainActivity.class));
    }

    //request new data
    public void button_onClick_GET(View view){

        final TextView text_data = (TextView) findViewById(R.id.textViewLedData);


        RequestQueue queue = Volley.newRequestQueue(this);
        request_url = url.toString() + "/led-matrix?x=" + x.toString() + "&y=" + y.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // json parser
                        try {
                            String JString = response;
                            JSONObject data = new JSONObject(JString);
                            JSONObject valueJ = data.getJSONObject("value");
                            JSONArray rgb = valueJ.getJSONArray("rgb");

                            String source = data.getString("source");
                            String unit = data.getString("unit");
                            String timex = data.getString("time");
                            String x = valueJ.getString("x");
                            String y = valueJ.getString("y");


                            long dv = Long.valueOf(timex)*1000;
                            Date df = new java.util.Date(dv);
                            String time = new SimpleDateFormat("MM dd, yyyy hh:mma", Locale.GERMAN).format(df);

                            Integer r = rgb.getInt(0);
                            Integer g = rgb.getInt(1);
                            Integer b = rgb.getInt(2);

                            text_data.setText("Source: " + source.toString() + "\n"
                                    + "Time: " + time.toString() + "\n"
                                    + "Unit: " + unit.toString() + "\n"
                                    + "x: " + x.toString() + "\n"
                                    + "y: " + y.toString() + "\n"
                                    + "r: " + r.toString() + "\n"
                                    + "g: " + g.toString() + "\n"
                                    + "b: " + b.toString() + "\n");


                            // set color
                            preview2.R = r;
                            preview2.G = g;
                            preview2.B = b;
                            setLedViewColor(colorView2, preview2.getColor());

                        }
                        catch (JSONException e){
                            // do nothing
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

    public void button_onClick_PUT(View view) {


        final TextView text_data = (TextView) findViewById(R.id.textViewData);

        request_url = url.toString() + "/led-matrix?x=" + x.toString() + "&y=" + y.toString() + "&r=" + r.toString() + "&g=" + g.toString() + "&b=" + b.toString();
        String requestBody = "";

        StringRequest putRequest = new StringRequest(Request.Method.PUT, request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // handle response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(putRequest);

    }

    private void setLedViewColor(View v, int color){
        Drawable backgroundColor = v.getBackground();
        if (backgroundColor instanceof ShapeDrawable) {
            ((ShapeDrawable)backgroundColor).getPaint().setColor(color);
        } else if (backgroundColor instanceof GradientDrawable) {
            ((GradientDrawable)backgroundColor).setColor(color);
        } else if (backgroundColor instanceof ColorDrawable) {
            ((ColorDrawable)backgroundColor).setColor(color);
        }
    }
}