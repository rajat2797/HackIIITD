package com.example.rajat.iiitd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static String email = "";
    String url = "http://192.168.5.92:3000/api/v1/";
    RecyclerView rcview;
    PastEventsAdapter pastEventsAdapter;
    Button create_event;
    List<EventsClass> events = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcview = (RecyclerView) findViewById(R.id.rcview);
        create_event = (Button) findViewById(R.id.create_event);
        pastEventsAdapter = new PastEventsAdapter(this, events);
        rcview.setLayoutManager(new LinearLayoutManager(this));
        rcview.setAdapter(pastEventsAdapter);

        email = getIntent().getStringExtra("email");
        makePostRequest();
    }

    private void makePostRequest() {
        Log.d("****here****", "****");
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,
                url + "showdata",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("status").equals("200")){
                                JSONObject data = obj.getJSONObject("data");
                                Toast.makeText(getApplicationContext(), ""+data, Toast.LENGTH_LONG).show();
                                JSONArray d = data.getJSONArray("events");
                                Log.d("*****data = ", "" + d);
                                for(int i=0;i<d.length();i++){
                                    JSONObject v = (JSONObject) d.get(i);

                                    EventsClass e = new EventsClass(v.getString("name"), v.getString("status"), v.getString("created_at"));
                                    events.add(e);
                                }
                                pastEventsAdapter.notifyDataSetChanged();
                                rcview.smoothScrollToPosition(0);

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
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                return params;
            }
        };
        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
        requestqueue.add(stringrequest);
    }

    public void createEvent(View view) {
        Intent i = new Intent(getApplicationContext(), CreateEvent.class);
        i.putExtra("email", email);
        startActivity(i);
    }
}
