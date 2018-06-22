package com.example.nate.getfreshbooks;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
        listView.setAdapter(new SimpleAdapter(
                this, books, R.layout.row, new String[]{"title", "author"}, new int[]{R.id.bookTitleView, R.id.bookAuthorView}
        ));
    }
}