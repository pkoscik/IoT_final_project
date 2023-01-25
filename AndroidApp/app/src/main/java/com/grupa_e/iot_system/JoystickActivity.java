package com.grupa_e.iot_system;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

public class JoystickActivity extends AppCompatActivity {
    String url = MainActivity.server_URL.toString() + ":"+ MainActivity.server_PORT.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

    }

    //go to 'Menu' view by button click
    public void button_onClick_menu(View view){
        startActivity(new Intent(JoystickActivity.this, MainActivity.class));
    }


    //request new Joystick data
    public void button_onClick_GET(View view){

        final TextView text_clicks = (TextView) findViewById(R.id.textViewClicks);
        final TextView text_posX = (TextView) findViewById(R.id.textViewPosX);
        final TextView text_posY = (TextView) findViewById(R.id.textViewPosY);

        RequestQueue queue = Volley.newRequestQueue(this);
        String request_url = url.toString() + "/joystick?mode=data";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // json parser
                                try {
                                    String JString = response;
                                    JSONObject joystick = new JSONObject(JString);
                                    JSONObject valueJ = joystick.getJSONObject("value");

                                    String clicks = valueJ.getString("clicks");
                                    String posx = valueJ.getString("x");
                                    String posy = valueJ.getString("y");

                                    text_clicks.setText("Clicks: " + clicks.toString());
                                    text_posX.setText("X: " + posx.toString());
                                    text_posY.setText("Y: " + posy.toString());

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

    //request new Joystick data
    public void button_onClick_RESET(View view){

        final TextView text_clicks = (TextView) findViewById(R.id.textViewClicks);
        final TextView text_posX = (TextView) findViewById(R.id.textViewPosX);
        final TextView text_posY = (TextView) findViewById(R.id.textViewPosY);

        RequestQueue queue = Volley.newRequestQueue(this);
        String request_url = url.toString() + "/joystick?mode=reset";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // json parser
                        try {
                            String JString = response;
                            JSONObject joystick = new JSONObject(JString);
                            String success = joystick.getString("success");
                            if(success.equals("true")){
                                String clicks = "0";
                                String posx = "0";
                                String posy = "0";

                                text_clicks.setText("Clicks: " + clicks.toString());
                                text_posX.setText("X: " + posx.toString());
                                text_posY.setText("Y: " + posy.toString());
                            }

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
}