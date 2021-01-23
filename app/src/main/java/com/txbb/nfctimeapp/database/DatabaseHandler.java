package com.txbb.nfctimeapp.database;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    // reset tags.json with fake tag
    //
    public static void initTag(Context context) {
        Gson gson = new Gson();
        // fake tag
        Tag tag = new Tag("123","tag1", "cate1",1);
        try {
            ArrayList<Tag> prev_tags = new ArrayList<>();
            prev_tags.add(tag);

            // Write to file
            FileOutputStream fileOutputStream = context.openFileOutput("tags.json", Context.MODE_PRIVATE);
            // convert arraylist to json string
            String json = gson.toJson(prev_tags);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();

            // read from file: only file name need to be change
            FileInputStream fin1 = context.openFileInput("tags.json");
            int d;
            String temp1="";
            while( (d = fin1.read()) != -1){
                temp1 = temp1 + Character.toString((char)d);
            }
            fin1.close();

            // convert string back to arraylist
            ArrayList<Tag> s = gson.fromJson(temp1, new TypeToken<ArrayList<Tag>>() {}.getType());
        } catch (IOException e) {
            System.out.println("final");
            e.printStackTrace();
        }

    }

    // register a new tag to the db
    public static void createTag(Tag tag, Context context){
        Gson gson = new Gson();
        try {
            // Read database
            FileInputStream fin = context.openFileInput("tags.json");
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            fin.close();

            ArrayList<Tag> prev_tags = gson.fromJson(temp, new TypeToken<ArrayList<Tag>>() {}.getType());
            prev_tags.add(tag);

            // Write to file
            FileOutputStream fileOutputStream = context.openFileOutput("tags.json", Context.MODE_PRIVATE);
            String json = gson.toJson(prev_tags);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();

            FileInputStream fin1 = context.openFileInput("tags.json");
            int d;
            String temp1="";
            while( (d = fin1.read()) != -1){
                temp1 = temp1 + Character.toString((char)d);
            }
            fin1.close();

            ArrayList<Tag> s = gson.fromJson(temp1, new TypeToken<ArrayList<Tag>>() {}.getType());
            System.out.println(s.get(0).getId());

        } catch (IOException e) {
            System.out.println("final");
            e.printStackTrace();
        }
    }

    public void createCategoryHistory(CategoryHistory category) {
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader("categoryHistory.json"));

            List<CategoryHistory> categories = gson.fromJson(reader, CategoryHistory.class);

            categories.add(category);
            gson.toJson(categories, new FileWriter("tags.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSession(String categoryName, Session session, Context context){
        Gson gson =  new Gson();
        try {
            FileInputStream fin = context.openFileInput("categoryHistory.json");
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            fin.close();

            ArrayList<CategoryHistory> categories = gson.fromJson(temp, CategoryHistory.class);

            for (CategoryHistory c: categories){
                if (c.getName().equals(categoryName)) {
                    c.addSession(session);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}





