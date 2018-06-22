package com.example.nate.getfreshbooks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Book extends HashMap<String, Object> {
    final static String baseURL = "http://172.17.253.129/GetFreshBooks/Inventory/loaddata";
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
//        List<Book> list = new ArrayList<>();

        // Done, returns data object
        JSONObject a = JSONParser.getJSONFromUrl(baseURL);

        // Done, returns array of JSON objects
        JSONArray data = new JSONArray(a.getString("data"));

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
}
