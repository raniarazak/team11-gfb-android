package com.example.nate.getfreshbooks;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends Activity {

    List<Book> books;
    List<Book> queryBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Change to async task
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);

        books = new ArrayList<>();
        queryBooks = new ArrayList<>();

        // Initialize SearchView
        final SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnClickListener(v -> searchView.setIconified(false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @TargetApi(24)
            @Override
            public boolean onQueryTextChange(String s) {
                queryBooks = MainActivity.this.books.stream()
                        .filter(x -> x.get("title").toString().toLowerCase().contains(s.toLowerCase()) ||
                                x.get("author").toString().toLowerCase().contains(s.toLowerCase()))
                        .collect(Collectors.toList());
                ListView listView = findViewById(R.id.listView);
                listView.setAdapter(new CustomAdapter(MainActivity.this, R.layout.row, queryBooks));

                return true;
            }

        });


        // Initialize ListView
        try {
            books = Book.list();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new CustomAdapter(this, R.layout.row, books));
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Book book = (Book) listView.getItemAtPosition(i);
            Log.i("LIST ON CLICK", book.get("isbn").toString());
            Intent intent = new Intent(this, BookDetailsActivity.class);
            intent.putExtra("bookId", (int) book.get("bookId"));
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_option_1:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}