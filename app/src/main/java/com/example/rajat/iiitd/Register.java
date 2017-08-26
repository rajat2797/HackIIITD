package com.example.rajat.iiitd;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextView t, tvLogin;
    EditText uemail, upassword, uname, uphone;
    Button b;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    String url = "http://192.168.5.92:3000/api/v1/createOrganizer";
    float lat,lon;
    int PLACE_PICKER_REQUEST = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null) {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent i = null;
            try {
                i = builder.build(getApplicationContext());
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
            startActivityForResult(i, PLACE_PICKER_REQUEST);
        }

        tvLogin = (TextView) findViewById(R.id.textViewLogin);
        uemail = (EditText) findViewById(R.id.email);
        upassword = (EditText) findViewById(R.id.password);
        uname = (EditText) findViewById(R.id.name);
        uphone = (EditText) findViewById(R.id.phone);

        b = (Button) findViewById(R.id.submit);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                Log.i("yoyooyo", "onActivityResult: "+place.getLatLng());
                lat = (float) place.getLatLng().latitude;
                lon = (float) place.getLatLng().longitude;
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, "lat = "+lat + " lon = "+lon, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void register(View view) {
        final String email = uemail.getText().toString().trim();
        final String password = upassword.getText().toString().trim();
        final String name = uname.getText().toString().trim();
        final String phone = uphone.getText().toString().trim();
        Log.d("*********network = ", "" + networkInfo);
        if (networkInfo != null) {
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if(obj.getString("status").equals("200")){
//                                    finish();
                                    Log.d("***here","***");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    JSONObject d = obj.getJSONObject("data");
                                    String email = d.getString("email");
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("status"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);
                    params.put("name", name);
                    params.put("phone", phone);
                    params.put("lat", ""+lat);
                    params.put("lng", ""+lon);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

    }
    
    public void loginScreen(View view) {
        Intent i = new Intent(Register.this, Login.class);
        startActivity(i);
    }
}
