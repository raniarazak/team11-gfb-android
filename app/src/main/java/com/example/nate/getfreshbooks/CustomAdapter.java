package com.example.nate.getfreshbooks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Book> {

    final private int resource;
    private List<Book> books;

    public CustomAdapter(Context context, int resource, List<Book> books) {
        super(context, resource, books);
        this.resource = resource;
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(resource, null);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Book book = books.get(position);

        //  holder.bookPic.setImageBitmap(null);
        holder.bookTitle.setText(book.get("title").toString());
        holder.bookAuthor.setText(book.get("author").toString());

        //holder.bookPic= v.findViewById(R.id.image_cover_thumbnail);
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                return Book.getPhoto(strings[0]);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                holder.bookPic.setImageBitmap(bitmap);
            }
        }.execute(book.get("isbn").toString());


        return convertView;
    }

    class ViewHolder {
        ImageView bookPic;
        TextView bookAuthor;
        TextView bookTitle;


        public ViewHolder(View view) {
            bookPic = (ImageView) view.findViewById(R.id.image_cover_thumbnail);
            bookAuthor = (TextView) view.findViewById(R.id.bookAuthorView);
            bookTitle = (TextView) view.findViewById(R.id.bookTitleView);
        }
    }


}


