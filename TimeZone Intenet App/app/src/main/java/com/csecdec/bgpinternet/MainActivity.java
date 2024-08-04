package com.csecdec.bgpinternet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer time;
    TextView textView;
    EditText co,tz;
    Button button;
    ProgressBar progressBar;
    String v;
    RequestQueue queue ;
    String url = "https://worldtimeapi.org/api/timezone/";
    String new_url="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = new Timer();


        queue = Volley.newRequestQueue(MainActivity.this);

        co = (EditText)findViewById(R.id.coET);
        tz = (EditText)findViewById(R.id.tzET);
        button = (Button) findViewById(R.id.buttonBT);
        progressBar = (ProgressBar)findViewById(R.id.progressPB);
        textView = (TextView)findViewById(R.id.textTV);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String a = co.getText().toString().trim();
                String b = tz.getText().toString().trim();
                new_url = url+a+"/"+b;
                dataFetch();


            }
        });


    }

    public void dataFetch()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, new_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String data = null;
                try {
                    data = response.getString("datetime");
                    String y = data.substring(0,4);
                    String m = data.substring(5,7);
                    String d = data.substring(8,10);
                    String time =data.substring(11,19);
                    int s = Integer.parseInt(data.substring(11,13));



                    if(s<12 && s>=0)
                        v = "AM";
                    else
                        v = "PM";

                    textView.setText("Date: "+d+"/"+m+"/"+y+"\nTime: "+time+" "+v);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Please enter continent and timezone");
                progressBar.setVisibility(View.GONE);

            }
        });

        queue.add(jsonObjectRequest);
    }
    
}