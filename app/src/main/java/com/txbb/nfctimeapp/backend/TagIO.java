package com.txbb.nfctimeapp.backend;

import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.util.Log;

import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

public class TagIO extends AppCompatActivity {

    private TagManager tagManager;

    public void onRead() {

    }

    public void onWrite() {

    }


    private boolean isTagEmpty() {
        // check if the tag is empty;
        return NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction());
    }

    private String readTag() {
        // read and return the tag id; return null uuid if not successful.

        // null uuid
        String id = "00000000-0000-0000-0000-000000000000";

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())){
            Tag tag = (Tag) getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);

            if (tag != null){
                Ndef ndef = Ndef.get(tag);
                NdefMessage msg = ndef.getCachedNdefMessage();
                for (NdefRecord record:msg.getRecords()){

                    if (record != null){
                        String payload = new String(record.getPayload());
                        // Example: txbb://tag.id/123e4567-e89b-12d3-a456-556642440000
                        id = payload.substring(15);
                    }

                    else{
                        Log.i("NFC Tag", "No tag detected");
                    } // else
                } // for
            } // if
        } // if
        return id;
    }

    private void writeTag(String id) {

        // id format: 123e4567-e89b-12d3-a456-556642440000
        // load example: txbb://tag.id/123e4567-e89b-12d3-a456-556642440000
        String load = "txbb://tag.id/" + id;

        Log.i("NFC Tag", "Ready to write");

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {

            Tag tag = (Tag) getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                Ndef ndef = Ndef.get(tag);
                try {
                    ndef.connect();
                    NdefRecord rtdUriRecord1 = NdefRecord.createUri(load);
                    NdefMessage ndefMessage = new NdefMessage(rtdUriRecord1);
                    ndef.writeNdefMessage(ndefMessage);
                    Log.i("NFC Tag", "Done");

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("NFC Tag", "Message cannot be sent, please try again!");
                } finally {
                    try {
                        ndef.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("NFC Tag", "Connection cannot be closed, please try again!");
                    }
                }
            }
        } else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
            Tag tag = (Tag) getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                NdefFormatable format = NdefFormatable.get(tag);
                try {
                    format.connect();
                    NdefRecord rtdUriRecord1 = NdefRecord.createUri(load);
                    NdefMessage ndefMessage = new NdefMessage(rtdUriRecord1);
                    format.format(ndefMessage);
                    Log.i("NFC Tag", "Done");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("NFC Tag", "Message cannot be sent, please try again!");
                } finally {
                    try {
                        format.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("NFC Tag", "Connection cannot be closed, please try again!");
                        System.out.print("Connection cannot be closed, please try again!");
                    }
                }
            }
        }
    }

}
