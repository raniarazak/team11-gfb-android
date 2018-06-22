package com.example.nate.getfreshbooks;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Change to async task
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);

        List<Book> books = new ArrayList<>();

        try {
            books = Book.list();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.row, R.id.bookTitleView, books));
    }
}