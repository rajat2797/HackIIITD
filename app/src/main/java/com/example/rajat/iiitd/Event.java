package com.example.rajat.iiitd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Event extends AppCompatActivity {

    TextView t;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        t = (TextView) findViewById(R.id.tv);
        status = getIntent().getStringExtra("status");
        t.setText(status);
        if(status == "success"){
            // show image
        }
    }
}
