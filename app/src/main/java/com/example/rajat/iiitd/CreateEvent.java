package com.example.rajat.iiitd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateEvent extends AppCompatActivity {

    EditText ename, estart, eend, epeople;
    Button button;
    String email, url = "http://192.168.5.92:3000/api/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        ename = (EditText) findViewById(R.id.name);
        estart = (EditText) findViewById(R.id.startTime);
        eend = (EditText) findViewById(R.id.endTime);
        epeople = (EditText) findViewById(R.id.people);
        email = getIntent().getStringExtra("email");
    }

    public void Create(View view) {
        final String s[] = new String[4];
        s[0] = ename.getText().toString().trim();
        s[1] = estart.getText().toString().trim();
        s[2] = eend.getText().toString().trim();
        s[3] = epeople.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url + "createEvent",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("status").equals("200")){
                                finish();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.putExtra("email",email);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("status"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("name", s[0]);
                params.put("start", s[1]);
                params.put("end", s[2]);
                params.put("people", s[3]);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
