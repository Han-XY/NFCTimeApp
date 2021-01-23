package com.txbb.nfctimeapp.backend;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.util.Log;

import java.time.Instant;


import java.util.UUID;

public class TagManager {

    public void onRead(boolean isEmpty, String id) {

    }

    public void registerId(String id) {

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
