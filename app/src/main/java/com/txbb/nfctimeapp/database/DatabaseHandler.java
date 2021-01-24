package com.txbb.nfctimeapp.database;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.txbb.nfctimeapp.TagProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class DatabaseHandler {

    // singleton pattern
    // make sure only one instance of db is constructed
    private static DatabaseHandler db = new DatabaseHandler();

    private DatabaseHandler() {
        gson = new Gson();
    }

    public static DatabaseHandler getDb() {
        return db;
    }

    private Gson gson;

    // <key:category name, sessions>
    private HashMap<Integer, ArrayList<Session>> cateToSessions;

    // <key: tag_id, ArrayList<Session>>
    private HashMap<String, ArrayList<Session>> idToSessions;

    // <key: tag_id, TagProperties>>
    private HashMap<String, TagProperties> idToTag;

    /**
     * Check if file exist
     *
     * @param fileName - json filename
     * @param context  - activity
     * @return bool
     */
    private boolean checkFileExists(String fileName, Context context) {
        File file = new File(context.getFilesDir(), fileName);
        return file.exists();
    }

    /**
     * Write new hashmap idToTag to the tags.json
     *
     * @param context - activity
     */
    // Write array list of NfcTag to json file "tags.json"
    private void writeTagsJson(Context context) {
        try {
            // Create output handler
            FileOutputStream fileOutputStream = context.openFileOutput("tags.json", Context.MODE_PRIVATE);

            // Convert hashmap to json string
            String json = gson.toJson(this.idToTag);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();

        } catch (IOException e) {
            System.out.println("Cannot open file");
            e.printStackTrace();
        }

    }

    /**
     * Write new hashmap cateToSessions to categoryHistory.json
     *
     * @param context - activity
     */
    private void writeCategoryHistoryJson(Context context) {
        try {
            // Create output handler
            FileOutputStream fileOutputStream = context.openFileOutput("categoryHistory.json", Context.MODE_PRIVATE);
            // Convert ArrayList to json string
            String json = gson.toJson(this.cateToSessions);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();

        } catch (IOException e) {
            System.out.println("Cannot open file");
            e.printStackTrace();
        }

    }

    /**
     * Write new hashmap idToSessions to categoryHistory.json
     *
     * @param context - activity
     */
    private void writeTagSessionsJson(Context context) {
        try {
            // Create output handler
            FileOutputStream fileOutputStream = context.openFileOutput("tagSessions.json", Context.MODE_PRIVATE);
            // Convert ArrayList to json string
            String json = gson.toJson(this.idToSessions);
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
     * @param fileName - the file to read from
     * @param context  - activity
     * @return fileContent - a string type of fileContent transferred from bytes
     */
    private static String readFileContent(String fileName, Context context) {

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
     * Get hashmap idToTag from json file "tags.json",
     *
     * @param context - activity
     */
    private void readTagsJson(Context context) {

        if (checkFileExists("tags.json", context)) {
            System.out.println("File Exists");
            String fileContent = readFileContent("tags.json", context);
            this.idToTag = gson.fromJson(fileContent, new TypeToken<HashMap<String, TagProperties>>() {
            }.getType());
        }
    }

    /**
     * Get hashmap idToSessions from json file "tagSessions.json",
     *
     * @param context - activity
     */
    private void readTagSessionsJson(Context context) {

        if (checkFileExists("tagSessions.json", context)) {
            System.out.println("File Exists");
            String fileContent = readFileContent("tagSessions.json", context);
            this.idToSessions = gson.fromJson(fileContent, new TypeToken<HashMap<String, ArrayList<Session>>>() {
            }.getType());
        }
    }

    /**
     * Get hashmap cateToSession from json file "categoryHistory.json",
     *
     * @param context - activity
     */
    private void readCategoryHistoryJson(Context context) {

        if (checkFileExists("tags.json", context)) {
            System.out.println("File Exists");
            String fileContent = readFileContent("categoryHistory.json", context);
            this.cateToSessions = gson.fromJson(fileContent, new TypeToken<HashMap<Integer, ArrayList<Session>>>() {
            }.getType());
        }
    }

    /**
     * TEST ONLY
     * Reset the tags.json file containing a dummy tag
     *
     * @param context - activity
     */
    public void initTags(Context context) {

        // initialise the file
        // fake tag: for testing only

        TagProperties tag = new TagProperties("dummy", 1);
        tag.setId("0");
        HashMap<String, TagProperties> dummy = new HashMap<String, TagProperties>();
        dummy.put(tag.getId(), tag);
        this.idToTag = dummy;

        writeTagsJson(context);
        readTagsJson(context);
        System.out.println("init tags.json");
    }

    /**
     * TEST ONLY
     * Reset the tagSessions.json file containing a dummy tag
     *
     * @param context - activity
     */
    public void initTagSessions(Context context) {

        // initialise the file
        // fake tag: for testing only

        TagProperties tag = new TagProperties("tag1", 1);
        tag.setId("0");

        HashMap<String, ArrayList<Session>> dummy = new HashMap<>();
        ArrayList<Session> sessions = new ArrayList<>();

        dummy.put(tag.getId(), sessions);
        this.idToSessions = dummy;

        writeTagSessionsJson(context);
        readTagSessionsJson(context);
        System.out.println("init tagSessions.json");
    }

    /**
     * TEST ONLY
     * Reset the categoryHistory.json file containing a dummy tag
     *
     * @param context - activity
     */
    public void initCategoryHistory(Context context) {

        // initialise the file

        HashMap<Integer, ArrayList<Session>> dummy = new HashMap<>();
        ArrayList<Session> sessions = new ArrayList<>();

        for (int i = 1; i < 17; i++){
            dummy.put(i, sessions);
        }
        this.cateToSessions = dummy;

        writeCategoryHistoryJson(context);
        readCategoryHistoryJson(context);
        System.out.println(this.cateToSessions);
    }

    /**
     * To Check whether an id exists in the database
     *
     * @param id - String tag_id
     * @param context - activity
     * @return boolean
     */
    public boolean containsId(String id, Context context){
        readTagsJson(context);

        String allKeys = "";
        for (String s : idToTag.keySet()) {
            allKeys += " " + s;
        }
        Log.i("TXBB1000", "All keys inside the JSON " + allKeys);

        return this.idToTag.containsKey(id);
    }

    public HashMap<String, TagProperties> getAll(Context context) {
        readTagsJson(context);
        HashMap<String, TagProperties> result = new HashMap<>();
        for (String id: idToTag.keySet()) {
            result.put(id,new TagProperties(idToTag.get(id)));
        }
        return result;
    }

    public void debug() {
        String allKeys = "";
        for (String s : idToTag.keySet()) {
            allKeys += " " + s;
        }

        Log.i("TXBB1000", "DatabaseHandler::debug All keys inside the JSON " + allKeys);
    }

    /**
     * When a new tag is create, add the tag to db
     *
     * @param tag     - Tag Properties
     * @param context - activity
     */
    public void createTag(TagProperties tag, Context context) {

        // For resting only: manually reset file
        // TODO: comment out this line if not testing!!
//        initTags(context);
//        initTagSessions(context);

        // write to tags.json
        readTagsJson(context);
        this.idToTag.put(tag.getId(), tag);

        // initiate tagSessions.json
        readTagSessionsJson(context);
        ArrayList<Session> sessions = new ArrayList<>();
        this.idToSessions.put(tag.getId(), sessions);


        writeTagsJson(context);

        // TEST::
        readTagsJson(context);
        System.out.println(this.idToTag.get(tag.getId()));

    }

    /**
     * Delete tag from both tags.json and tagSessions.json, update it with a new id generated
     *
     * @param id - tag_id to be deleted
     * @param context - activity
     */
    public void deleteTag(String id, Context context){
        Log.i("TXBB1000", "DatabaseHandler::deleteTag " + id);

        // generate a new id for tag
        String new_id = UUID.randomUUID().toString();

        readTagsJson(context);
        readTagSessionsJson(context);

        // delete old_id from tags.json and add new_id
        if (containsId(id, context)){
            // update idToTag
            TagProperties tag = this.idToTag.get(id);
            tag.setId(new_id); // reset tag_id inside TagProperties

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long currentTime = timestamp.getTime() / 1000;
            tag.setEndTime(currentTime);

            tag.setDeleted(true);

            this.idToTag.remove(id);  // remove old id
            this.idToTag.put(new_id, tag);  // put new id
            writeTagsJson(context);  // update tags.json

            // update idToSession
            ArrayList<Session> sessions = this.idToSessions.get(id);
            this.idToSessions.remove(id);  // remove old id
            this.idToSessions.put(new_id, sessions);  // put new id
            writeTagSessionsJson(context);  //update tagSesssions.json

        }
        else{
            System.out.println("cannot find tag to delete");
        }
    }

    /**
     * called by backend to update TagProperties stored in tags.json
     *
     * @param tag - tag_id
     */
    public void updateTagProperties(TagProperties tag, Context context){
        Log.i("TXBB1000", "DatabaseHandler::updateTagProperties: " + tag.getId());

        if (containsId(tag.getId(), context)){
            readTagsJson(context);
            TagProperties oldProperties = this.idToTag.get(tag.getId());
            oldProperties.updateProperties(tag);
            this.idToTag.put(tag.getId(), oldProperties);
            Log.i("TXBB1000", "DatabaseHandler::updateTagProperties2 " + oldProperties.getCategory());

            writeTagsJson(context);
        }
        else{
            System.out.println("cannot find tag id to update");
        }
    }

    /**
     * Get the tag properties from tags.json by id
     *
     * @param id - tag id
     * @param context - activity
     * @return TagProperties
     */
    public TagProperties getProperties(String id, Context context){
        readTagsJson(context);
        return this.idToTag.get(id);
    }

    /**
     * Add a new session to db
     * @param id - tag id
     * @param startTime - startTime
     * @param endTime - endTime
     * @param context - activity
     */
    public void addSession(String id, long startTime, long endTime, Context context) {
        readTagsJson(context);
        readTagSessionsJson(context);
        readCategoryHistoryJson(context);

        TagProperties tag = this.idToTag.get(id);
        ArrayList<Session> tag_sessions = this.idToSessions.get(id);
        ArrayList<Session> cate_sessions = this.cateToSessions.get(tag.getCategory());

        Session session = new Session(startTime, endTime);
        tag_sessions.add(session);
        cate_sessions.add(session);

        this.idToSessions.put(id, tag_sessions);
        this.cateToSessions.put(tag.getCategory(), cate_sessions);

        writeTagSessionsJson(context);
        writeCategoryHistoryJson(context);

    }
}




