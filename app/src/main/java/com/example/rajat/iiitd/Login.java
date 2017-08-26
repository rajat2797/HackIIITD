package com.example.rajat.iiitd;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView t, tvRegister;
    EditText uemail, upassword;
    Button b;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    String url = "http://192.168.5.92:3000/api/v1/loginOrganizer";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvRegister = (TextView) findViewById(R.id.textViewRegister);
        uemail = (EditText) findViewById(R.id.email);
        upassword = (EditText) findViewById(R.id.password);
        b = (Button) findViewById(R.id.submit);

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
    }

    public void login(View view) {
        final String email = uemail.getText().toString().trim();
        final String password = upassword.getText().toString().trim();
        if( networkInfo != null ) {
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                finish();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                JSONObject d = obj.getJSONObject("data");
                                String email = d.getString("email");
                                intent.putExtra("email",email);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void registerScreen(View view) {
        Intent i = new Intent(Login.this, Register.class);
        startActivity(i);
    }
}
