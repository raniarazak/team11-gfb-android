package com.example.nate.getfreshbooks;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Book> {

    private int resource;
    private List<Book> books;

    public CustomAdapter(Context context, int resource, List<Book> books) {
        super(context, resource, books);
        this.resource = resource;
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(resource, null);

        Book book = books.get(position);

        if (book != null) {
            TextView bookTitleView = v.findViewById(R.id.bookTitleView);
            bookTitleView.setText(book.get("title").toString());

            TextView authorTitleView = v.findViewById(R.id.bookAuthorView);
            authorTitleView.setText(book.get("author").toString());

            ImageView imageView = v.findViewById(R.id.image_cover_thumbnail);
            imageView.setImageBitmap(Book.getPhoto(book.get("isbn").toString()));
        }

        return v;
    }


}
