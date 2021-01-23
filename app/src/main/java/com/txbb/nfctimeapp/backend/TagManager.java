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


import java.util.Map;
import java.util.UUID;

public class TagManager {

    private FrontBackInterface frontBackInterface;

    public void onRead(boolean isEmpty, String id) {

    }

    public void registerId(String id) {

    }

    // set tagProperties for a tag id
    // used both for tag creation and tag update
    public void registerTag(String id, TagProperties tagProperties) {

    }

    // delete the id and its associated data from our database
    public void deleteTag(String id) {

    }

    // get a map of from all the ids to properties from backend database
    // call sync() after the data is available
    public void syncRequest() {
        //get data first

        frontBackInterface.sync(null);

    }

    private void stopTag(String id) {

    }

    private void startTag(String id) {

    }

    private long getCurrentTime() {
        return Instant.now().getEpochSecond();
    }

    public String generateUUID(){

        String uuid = UUID.randomUUID().toString();
        return uuid;

    }
}
