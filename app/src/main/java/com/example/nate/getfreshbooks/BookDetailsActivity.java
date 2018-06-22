package com.example.nate.getfreshbooks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONException;

public class BookDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent intent = getIntent();

        Book book = null;
        try {
            book = Book.findBookbyId(intent.getExtras().getInt("bookId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Initialize Book Details
        EditText bookIdEditText = findViewById(R.id.bookIdEditText);
        bookIdEditText.setText(book.get("bookId").toString());

        EditText titleEditText = findViewById(R.id.titleEditText);
        titleEditText.setText(book.get("title").toString());

        EditText categoryIdEditText = findViewById(R.id.categoryIdEditText);
        categoryIdEditText.setText(book.get("categoryId").toString());

        EditText isbnEditText = findViewById(R.id.isbnEditText);
        isbnEditText.setText(book.get("isbn").toString());

        EditText authorEditText = findViewById(R.id.authorEditText);
        authorEditText.setText(book.get("author").toString());

        EditText stockEditText = findViewById(R.id.stockEditText);
        stockEditText.setText(book.get("stock").toString());

        EditText priceEditText = findViewById(R.id.priceEditText);
        priceEditText.setText(book.get("price").toString());

    }
}
