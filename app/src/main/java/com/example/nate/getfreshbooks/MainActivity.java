package com.example.nate.getfreshbooks;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    private List<Book> books;
    private List<Book> queryBooks;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        books = new ArrayList<>();
        queryBooks = new ArrayList<>();

        getSupportActionBar().setIcon(R.drawable.ic_book_white_24dp);
        getSupportActionBar().setTitle(" FreshBooks");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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
        ListView listView = findViewById(R.id.listView);

        new AsyncTask<Void, Void, List<Book>>() {
            @Override
            protected List<Book> doInBackground(Void... voids) {
                try {
                    return Book.list();
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Book> books) {
                MainActivity.this.books = books;
                listView.setAdapter(new CustomAdapter(MainActivity.this, R.layout.row, books));
            }
        }.execute();

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Book book = (Book) listView.getItemAtPosition(i);
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