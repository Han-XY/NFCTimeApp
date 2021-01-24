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

import java.time.Instant;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TagManager {

    private FrontBackInterface frontBackInterface;

    public TagManager(FrontBackInterface frontBackInterface) {
        this.frontBackInterface = frontBackInterface;
    }

    // TagManager receives data from TagIO in standard state
    public void onStandardRead(String id) {

        // TODO: what will happen if an ID is not found in our database
        // if id is not found:
        // notification: this event has been deleted; you need to set a new event for this tag
        frontBackInterface.deletedTagNotification();

        // TODO: if id is found, start or stop an event accordingly
        // we check if the event is ongoing by looking at if the starting time has been set
        // if there's starting time
        // we need to stop the event:
        // - get the stop timestamp, pass it to front end by calling onTagStop
        // - add a session to backend database
        // - clear both start and end time

        // else, we need to start the event:
        // - get the start timestamp, pass it to front end by calling onTagStart
        // - add the time to our tag properties
    }

    // receives data in registration state
    public void onRegistrationRead(boolean isEmpty, String id) {
        if (isEmpty) {
            // that's what we are supposed to get in most cases
            // trigger the registration process in the front end
            frontBackInterface.emptyTagRegistration();
        }
        else {
            // we are trying to register with a used tag

            // TODO: first check if this tag has been deleted from database
            // if yes, we can simply use the existing id directly
            // we don't need to generate a new ID in onwrite again
            // start existing tag registration process
            frontBackInterface.oldTagRegistration();

            // if this tag actually exists, we throw a warning
            frontBackInterface.onKnownTagRegistration();
        }
    }

    // set tagProperties for a tag id
    // used both for tag creation and tag update
    public void registerTag(String id, TagProperties tagProperties) {
        // TODO: need to add/update id<->properties pair in our database
    }

    // delete the id and its associated data from our database
    public void deleteTag(String id) {
        // TODO

    }

    // get a map of from all the ids to properties from backend database
    // call sync() after the data is available
    public void syncRequest() {

        //get data first
        Map<String, TagProperties> fakeData = new HashMap<>();

        fakeData.put("239hsgras3", new TagProperties("Read about life", 1, 1611438728, 0));
        fakeData.put("23rrsd", new TagProperties("Do calculus", 5, 1611438728, 0));
        fakeData.put("sdvsdv3", new TagProperties("Play games", 9, 1611438728, 0));
        fakeData.put("343ty ", new TagProperties("Exercise", 2, 1611438728, 0));

        frontBackInterface.sync(fakeData);

    }

    private long getCurrentTime() {
        return Instant.now().getEpochSecond();
    }

    public String generateUUID(){

        return UUID.randomUUID().toString();

    }

    public void setFrontBackInterface(FrontBackInterface frontBackInterface) {
        this.frontBackInterface = frontBackInterface;
    }

    public void setFrontBackManager(FrontBackInterface frontBackInterface) {
    }
}
