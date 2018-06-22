package com.example.nate.getfreshbooks;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import org.json.JSONException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Change to async task
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);

        TextView textView = findViewById(R.id.textView);
        try {
            textView.setText(Book.list().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}