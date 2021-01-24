package com.txbb.nfctimeapp.backend;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.util.Log;

import com.txbb.nfctimeapp.FrontBackInterface;
import com.txbb.nfctimeapp.TagProperties;
import com.txbb.nfctimeapp.database.DatabaseHandler;

import java.time.Instant;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class TagManager {

    private FrontBackInterface frontBackInterface;
    private CustomActivity currentActivity;
    private DatabaseHandler db;

    public TagManager(FrontBackInterface frontBackInterface) {
        this.frontBackInterface = frontBackInterface;
        this.currentActivity = frontBackInterface.getCurrentActivity();
        this.db = DatabaseHandler.getDb();
    }

    /**
     * Called when a tag is read during the standard read stage.
     * @param id the id of the tag.
     */
    public void onStandardRead(String id) {
        Log.i("TXBB1000", "onStandardRead");

        // check if this id is valid/existing
        // if id is not found:
        // notification: this event has been deleted; you need to set a new event for this tag
        if (db.containsId(id,currentActivity)) {
            frontBackInterface.deletedTagNotification();
            return;
        }

        // now that the id is valid, we start or stop an event accordingly

        TagProperties tp = db.getProperties(id,currentActivity);

        // we check if the event is ongoing by looking at if the starting time has been set
        if (tp.getStartTime() != 0) {
            // if there's starting time
            // we need to stop the event:

            // - get the stop timestamp, pass it to front end by calling onTagStop
            long startTime = tp.getStartTime();
            long stopTime = getCurrentTime();
            frontBackInterface.onTagStop(id,stopTime);

            // - clear both start and end time, reflect the update in database
            tp.setStartTime(0);
            tp.setEndTime(0);
            db.updateTagProperties(tp,currentActivity);

            // - add a session to backend database
            db.addSession(id,startTime,stopTime,currentActivity);
        }

        // else, we need to start the event:
        else {
            // - get the start timestamp, pass it to front end by calling onTagStart
            long startTime = getCurrentTime();
            frontBackInterface.onTagStart(id,startTime);

            // - add the time to our tag properties
            tp.setStartTime(startTime);
            db.updateTagProperties(tp,currentActivity);
        }

    }


    /**
     * Called when a tag is read during the registration stage. The tag ID is always read and
     * passed to this method, even if it does not exist.
     * @param isEmpty whether the tag is empty
     * @param id the id.
     */
    public void onRegistrationRead(boolean isEmpty, String id) {
        Log.i("TXBB1000", "onRegistrationRead with id: " + id);


//        if (isEmpty) {
//            frontBackInterface.emptyTagRegistration();
//            return;
//        }

        if (id == null) {
            frontBackInterface.emptyTagRegistration();
            return;
        }

        if (!db.containsId(id, currentActivity)) {
            frontBackInterface.oldTagRegistration();
            Log.i("TXBB1000", "onRegistrationRead: id not found in DB");
        } else {
            frontBackInterface.onKnownTagRegistration();
            Log.i("TXBB1000", "onRegistrationRead: id found in DB");
        }

    }

    // set tagProperties for a tag id
    // used both for tag creation and tag update
    public void updateTag(String id, TagProperties tagProperties) {

        // we need to check if it's a new id or old id
        if (db.containsId(id,currentActivity)) {
            // old id
            // we update directly
            db.updateTagProperties(tagProperties,frontBackInterface.getCurrentActivity());
        }

        else {
            // new id, we need to call creatTag in db and do the relevant initialisation
            db.createTag(tagProperties,currentActivity);
        }


    }

    // delete the id and its associated data from our database
    public void deleteTag(String id) {
        db.deleteTag(id,frontBackInterface.getCurrentActivity());
    }

    // get a map of from all the ids to properties from backend database
    // call sync() after the data is available
    public void syncRequest() {

        //get data first
        Map<String, TagProperties> fakeData = new HashMap<>();

        fakeData.put("239hsgras3", new TagProperties("Read about life", 1));
        fakeData.put("23rrsd", new TagProperties("Do calculus", 5));
        fakeData.put("sdvsdv3", new TagProperties("Play games", 9));
        fakeData.put("343ty ", new TagProperties("Exercise", 2));

        frontBackInterface.sync(fakeData);

    }

    private long getCurrentTime() {
        return Instant.now().getEpochSecond();
    }

    public String generateUUID(){

        return UUID.randomUUID().toString();

    }

}
