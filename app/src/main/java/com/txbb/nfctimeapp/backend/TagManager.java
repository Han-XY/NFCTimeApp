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


import java.util.UUID;

public class TagManager {

    private FrontBackInterface frontBackInterface;

    // this function will be called when we create the frontBackInterface
    public void setFrontBackInterface(FrontBackInterface frontBackInterface) {
        this.frontBackInterface = frontBackInterface;
    }

    // TagManager receives data from TagIO in standard state
    public void onStandardRead(String id) {

        // TODO: what will happen if an ID is not found in our database
        // if id is not found:
        // notification: this event has been deleted; you need to set a new event for this tag
        frontBackInterface.deletedTagNotification();

        // TODO: if id is found, start or stop an event accordingly
        // if the event is ongoing, we stop the event, update database and front end
        // else, we start the event and do the update as well
        // call onTagStart or onTagStop in frontbackinterface
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
        // TODO: read data from database

        frontBackInterface.sync(null);

    }

    private long getCurrentTime() {
        return Instant.now().getEpochSecond();
    }

    public String generateUUID(){

        return UUID.randomUUID().toString();

    }
}
