package com.example.nate.getfreshbooks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Book extends HashMap<String, Object> {
//    private final static String baseURL = "http://172.17.253.129/GetFreshBooks/Inventory/";
//    private final static String imageURL = "http://172.17.253.129/GetFreshBooks/images/";

    private final static String baseURL = "http://192.168.1.21/GetFreshBooks/Inventory/";
    private final static String imageURL = "http://192.168.1.21/GetFreshBooks/images/";

    private int bookId;
    private String title;
    private int categoryId;
    private String isbn;
    private String author;
    private int stock;
    private double price;

    public Book(int bookId, String title, int categoryId, String isbn, String author, int stock, double price) {
        put("bookId", bookId);
        put("title", title);
        put("categoryId", categoryId);
        put("isbn", isbn);
        put("author", author);
        put("stock", stock);
        put("price", price);
    }

    public static List<Book> list() throws JSONException {
        JSONArray data = JSONParser.getJSONArrayFromUrl(baseURL + "loaddata");
        List<Book> books = new ArrayList<>();

        // Loop through all JSON objects
        for (int i = 0; i < data.length(); i++) {
            JSONObject bookJson = data.getJSONObject(i);

            Book book = new Book(
                    bookJson.getInt("BookID"),
                    bookJson.getString("Title"),
                    bookJson.getInt("CategoryID"),
                    bookJson.getString("ISBN"),
                    bookJson.getString("Author"),
                    bookJson.getInt("Stock"),
                    bookJson.getDouble("Price")
            );

            books.add(book);
        }

        return books;
    }

    public static Book findBookbyId(int id) throws JSONException {
        JSONArray jsonArray = JSONParser.getJSONArrayFromUrl(baseURL + "loadsingle/" + String.valueOf(id));
        JSONObject bookJson = jsonArray.getJSONObject(0);

        return new Book(
                bookJson.getInt("BookID"),
                bookJson.getString("Title"),
                bookJson.getInt("CategoryID"),
                bookJson.getString("ISBN"),
                bookJson.getString("Author"),
                bookJson.getInt("Stock"),
                bookJson.getDouble("Price")
        );
    }

    public static Bitmap getPhoto(String isbn) {
        try {
            URL url = (new URL(String.format("%s%s.jpg", imageURL, isbn)));
            URLConnection conn = url.openConnection();
            InputStream ins = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(ins);
            ins.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Employee.getPhoto()", "Bitmap error");
        }
        return null;
    }
}
