package com.example.nate.getfreshbooks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;

public class BookDetailsActivity extends AppCompatActivity {

    private Book book;

    private void setBookCover(Book book) {
        ImageView imageView = BookDetailsActivity.this.findViewById(R.id.bookDetailsImageView);
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                return Book.getPhoto(strings[0]);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }.execute(book.get("isbn").toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent intent = getIntent();

        new AsyncTask<Integer, Void, Book>() {
            @Override
            protected Book doInBackground(Integer... integers) {
                try {
                    return Book.findBookbyId(integers[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return new Book(0, "Error", 1, "0", "N/A", 0, 0.0);
                }
            }

            @Override
            protected void onPostExecute(Book book) {
                BookDetailsActivity.this.book = book;

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

                BookDetailsActivity.this.setBookCover(book);
            }
        }.execute(intent.getExtras().getInt("bookId"));
    }
}
