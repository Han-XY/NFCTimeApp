package com.txbb.nfctimeapp.database;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseHandler {

    private static Gson gson = new Gson();

    // <key:category, name, sessions>
    private HashMap<String, ArrayList<Session>> cateToSessions;

    // <key: tag_id, ArrayList<Session>>
    private HashMap<String, ArrayList<Session>> idToSessions;

    // <key: tag_id, NfcTag>>
    private HashMap<String, NfcTag> idToTag;

    /**
     * Check if file exist
     *
     * @param fileName
     * @param context
     * @return
     */
    private static boolean checkFileExists(String fileName, Context context){
        File file = new File(context.getFilesDir(),fileName);
        return file.exists();
    }

    /**
     * Write new arraylist of tags to the db
     *
     * @param content - lists of tags read from previous tags.json
     * @param context - activity
     */
    // Write array list of NfcTag to json file "tags.json"
    private static void writeTagToFile(ArrayList<NfcTag> content, Context context) {

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

    /**
     *  Write array list of CategoryHistory to json file "categoryHistory.json"
     *
     * @param content - lists of categories read from previous tags.json
     * @param context - activity
     */
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

    /**
     * Read and return file which has fileName under context file directory content in String
     *
     * @param fileName  - the file to read from
     * @param context - activity
     * @return fileContent - a string type of fileContent transferred from bytes
     */
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

    /**
     * Get NfcTag List from json file "tags.json",
     *
     * @param context - activity
     * @return tags - ArrayList<Nfc Tag>
     */
    private static ArrayList<NfcTag> getTagList(Context context){

        ArrayList<NfcTag> tags = new ArrayList<NfcTag>();

        if (checkFileExists("tags.json", context)){
            System.out.println("File Exists");
            String fileContent = readFileContent("tags.json", context);
            tags = gson.fromJson(fileContent, new TypeToken<ArrayList<NfcTag>>() {}.getType());
        }

        return tags;

    }

    /**
     * Get CategoryHistory List from json file "categoryHistory.json",
     *
     * @param context - activity
     * @return categories - an arraylist of categoryhistory
     */
    //
    // if there is no such file, return an empty array list
    private static ArrayList<CategoryHistory> getCategoryHistoryList(Context context){

        ArrayList<CategoryHistory> categories = new ArrayList<CategoryHistory>();

        if (checkFileExists("categoryHistory.json", context)){
            System.out.println("File Exists");
            String fileContent = readFileContent("categoryHistory.json", context);
            categories = gson.fromJson(fileContent, new TypeToken<ArrayList<CategoryHistory>>() {}.getType());
        }

        return categories;

    }

    /**
     * TEST ONLY
     * Reset the tags.json file containing a dummy tag
     *
     * @param context - activity
     */
    public static void initTag(Context context) {

        // initialise the file
        // fake tag: for testing only

        NfcTag tag = new NfcTag("123","tag1", "cate1");
        ArrayList<NfcTag> prevTags = new ArrayList<>();
        prevTags.add(tag);


        writeTagToFile(prevTags, context);
        String fileContent = readFileContent("tags.json", context);
        ArrayList<NfcTag> s = gson.fromJson(fileContent, new TypeToken<ArrayList<NfcTag>>() {}.getType());

    }


    /**
     * Main Func to write to db upon a new tag is registered
     *
     * @param tag - NfcTag table
     * @param context -
     */
    public static void createTag(NfcTag tag, Context context){

        // For resting only: manually reset file
        initTag(context);

        // Get an arraylist of nfctags from the json file
        ArrayList<NfcTag> tags = getTagList(context);
        tags.add(tag);

        writeTagToFile(tags, context);

        // Read back to see if it is working
        String newFileContent = readFileContent("tags.json", context);
        ArrayList<NfcTag> s = gson.fromJson(newFileContent, new TypeToken<ArrayList<NfcTag>>() {}.getType());
        System.out.println(s);
        System.out.println(s.get(0).getName());

    }

    /**
     * TEST ONLY
     * Reset the categoryHistory.json file containing a dummy category
     *
     * @param context - activity
     */
    public static void initCategoryHistory(Context context) {

        // initialise the file
        // fake categoryHistory: for testing only
        CategoryHistory category = new CategoryHistory("categoryName");
        ArrayList<CategoryHistory> categories = new ArrayList<>();
        categories.add(category);


        writeCategoryHistoryToFile(categories, context);
        String fileContent = readFileContent("categoryHistory.json", context);
        ArrayList<CategoryHistory> s = gson.fromJson(fileContent, new TypeToken<ArrayList<CategoryHistory>>() {}.getType());

    }

    /**
     * Add a new category to the database
     *
     * @param category - new CategoryHistory class
     * @param context - activity
     */
    public static void createCategoryHistory(CategoryHistory category, Context context) {

        // For resting only: manually reset file
        initCategoryHistory(context);

        // Get an arraylist of CategoryHistory from the json file
        ArrayList<CategoryHistory> categories = getCategoryHistoryList(context);
        categories.add(category);

        writeCategoryHistoryToFile(categories, context);

        // Read back to see if it is working
        ArrayList<CategoryHistory> s = getCategoryHistoryList(context);
        System.out.println(s);
        System.out.println(s.get(0).getName());

    }

    /**
     * Main Func: add a new finished session under the category
     * @param categoryName
     * @param session
     * @param context
     */
    public static void addSession(String categoryName, Session session, Context context){

        ArrayList<CategoryHistory> categories = getCategoryHistoryList(context);

        for (CategoryHistory c: categories){
            if (c.getName().equals(categoryName)) {
                c.addSession(session);
            }
        }

        writeCategoryHistoryToFile(categories, context);

        // Read back to see if it is working
        String newFileContent = readFileContent("categoryHistory.json", context);
        ArrayList<CategoryHistory> s = gson.fromJson(newFileContent, new TypeToken<ArrayList<CategoryHistory>>() {}.getType());
        System.out.println(s.get(1).getSessions());
        System.out.println(s.get(1).getSessions().get(0).getDuration());

    }

    /**
     * Check if id is in the database
     *
     * @param id - tag_id
     * @param context - activity
     * @return boolean
     */
    public static boolean checkTagId(String id, Context context){

        ArrayList<NfcTag> tags = getTagList(context);

        for (NfcTag tag: tags){
            if (tag.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Obtain the wanted NfcTag table by its id
     *
     * @param id - Nfctag id
     * @param context - activity
     * @return tag - NfcTag
     */
    // Get the NfcTag by its id
    public static NfcTag getTagById(String id, Context context){

        ArrayList<NfcTag> tags = getTagList(context);

        for (NfcTag tag: tags){
            if (tag.getId().equals(id)) {
                return tag;
            }
        }

        return null;
    }

    /**
     * Update tags.json for the Tag startTime by its id.
     *
     * @param id - tag id
     * @param startTime - startTime when a tag is scanned
     * @param context - activity
     */
    public static void updateTagStartTime(String id, long startTime, Context context){
        ArrayList<NfcTag> tags = getTagList(context);

        for (NfcTag tag: tags){
            if (tag.getId().equals(id)) {
                tag.setStartTime(startTime);
                break;
            }
        }

        writeTagToFile(tags, context);

    }

    /**
     * Update tags.json for the Tag endTime by its id.
     *
     * @param id - tag Id
     * @param endTime - endTime when a tag is scanned
     * @param context - activity
     */
    public static void updateTagEndTime(String id, long endTime, Context context){
        ArrayList<NfcTag> tags = getTagList(context);

        for (NfcTag tag: tags){
            if (tag.getId().equals(id)) {
                tag.setEndTime(endTime);
                break;
            }
        }

        writeTagToFile(tags, context);

    }

    /**
     * Delete a tag from db by id
     *
     * @param id - tag id
     * @param context - activity
     */
    public static void deleteTagById(String id, Context context){
        ArrayList<NfcTag> tags = getTagList(context);

        // TEST ONLY
        System.out.println("Before deletion the array size is " + String.valueOf(tags.size()));

        for (NfcTag tag: tags){
            if (tag.getId().equals(id)) {
                tags.remove(tag);
                break;
            }
        }

        // TEST ONLY
        System.out.println("Successfully deleted, new array size is " + String.valueOf(tags.size()));

        writeTagToFile(tags, context);
    }
}





