package com.txbb.nfctimeapp.database;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class DatabaseHandler {

    private static Gson gson = new Gson();

    private static boolean checkFileExists(String fileName, Context context){
        File file = new File(context.getFilesDir(),fileName);
        return file.exists();
    }

    private static void writeTagToFile(ArrayList<Tag> content, Context context) {

        try {
            // Create output handler
            FileOutputStream fileOutputStream = context.openFileOutput("tags.json", Context.MODE_PRIVATE);

            // Convert ArrayList to json string
            String json = gson.toJson(content);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();

        } catch (IOException e) {
            System.out.println("Cannot open file");
            e.printStackTrace();
        }

    }

    private static void writeCategoryHistoryToFile(ArrayList<CategoryHistory> content, Context context) {

        try {
            // Create output handler
            FileOutputStream fileOutputStream = context.openFileOutput("categoryHistory.json", Context.MODE_PRIVATE);
            // Convert ArrayList to json string
            String json = gson.toJson(content);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();

        } catch (IOException e) {
            System.out.println("Cannot open file");
            e.printStackTrace();
        }

    }

    private static String readFileContent(String fileName, Context context){

        // Default file output
        String fileContent = "";

        try {
            // read from file: only file name need to be change
            FileInputStream fin = context.openFileInput(fileName);
            int c;
            while ((c = fin.read()) != -1) {
                fileContent = fileContent + Character.toString((char) c);
            }
            fin.close();

        } catch (IOException e) {
            System.out.println("Cannot open file");
            e.printStackTrace();
        }

        return fileContent;
    }

    public static void initTag(Context context) {

        // initialise the file
        // fake tag: for testing only

        Tag tag = new Tag("123","tag1", "cate1",1);
        ArrayList<Tag> prevTags = new ArrayList<>();
        prevTags.add(tag);


        writeTagToFile(prevTags, context);
        String fileContent = readFileContent("tags.json", context);
        ArrayList<Tag> s = gson.fromJson(fileContent, new TypeToken<ArrayList<Tag>>() {}.getType());

    }


    public static void createTag(Tag tag, Context context){

        // For resting only: manually reset file
        initTag(context);

        ArrayList<Tag> prevTags = new ArrayList<Tag>();

        if (checkFileExists("tags.json", context)){
            System.out.println("File Exists");
            String fileContent = readFileContent("tags.json", context);
            prevTags = gson.fromJson(fileContent, new TypeToken<ArrayList<Tag>>() {}.getType());
        }

        prevTags.add(tag);

        writeTagToFile(prevTags, context);

        // Read back to see if it is working
        String newFileContent = readFileContent("tags.json", context);
        ArrayList<Tag> s = gson.fromJson(newFileContent, new TypeToken<ArrayList<Tag>>() {}.getType());
        System.out.println(s);
        System.out.println(s.get(0).getName());

    }


    public static void initCategoryHistory(Context context) {

        // initialise the file
        // fake categoryHistory: for testing only
        CategoryHistory category = new CategoryHistory("categoryName");
        ArrayList<CategoryHistory> categories = new ArrayList<>();
        categories.add(category);


        writeCategoryHistoryToFile(categories, context);
        String fileContent = readFileContent("categoryHistory.json", context);
        ArrayList<CategoryHistory> s = gson.fromJson(fileContent, new TypeToken<ArrayList<CategoryHistory>>() {
        }.getType());

    }

    public static void createCategoryHistory(CategoryHistory category, Context context) {

        // For resting only: manually reset file
        initCategoryHistory(context);

        ArrayList<CategoryHistory> categories = new ArrayList<CategoryHistory>();

        if (checkFileExists("categoryHistory.json", context)){
            System.out.println("File Exists");
            String fileContent = readFileContent("categoryHistory.json", context);
            categories = gson.fromJson(fileContent, new TypeToken<ArrayList<CategoryHistory>>() {}.getType());
        }

        categories.add(category);

        writeCategoryHistoryToFile(categories, context);

        // Read back to see if it is working
        String newFileContent = readFileContent("categoryHistory.json", context);
        ArrayList<CategoryHistory> s = gson.fromJson(newFileContent, new TypeToken<ArrayList<CategoryHistory>>() {}.getType());
        System.out.println(s);
        System.out.println(s.get(0).getName());

    }

    public static void addSession(String categoryName, Session session, Context context){

        String fileContent = readFileContent("categoryHistory.json", context);
        ArrayList<CategoryHistory> categories = gson.fromJson(fileContent, new TypeToken<ArrayList<CategoryHistory>>() {}.getType());

        for (CategoryHistory c: categories){
            if (c.getName().equals(categoryName)) {
                c.addSession(session);
            }
        }

        writeCategoryHistoryToFile(categories, context);

        // Read back to see if it is working
        fileContent = readFileContent("categoryHistory.json", context);
        ArrayList<CategoryHistory> s = gson.fromJson(fileContent, new TypeToken<ArrayList<CategoryHistory>>() {}.getType());
        System.out.println(s.get(1).getSessions());
        System.out.println(s.get(1).getSessions().get(0).getDuration());


    }

}





