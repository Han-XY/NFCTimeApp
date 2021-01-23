package com.txbb.nfctimeapp.backend;
import com.txbb.nfctimeapp.FrontBackInterface;

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

    public TagIO(TagManager tagManager, FrontBackInterface frontBackInterface) {
        this.tagManager = tagManager;
        this.frontBackInterface = frontBackInterface;
    }

    private TagManager tagManager;
    private FrontBackInterface frontBackInterface;

    private enum State {
        REGISTRATION,
        STANDARD,
        NEW_TAG,
        OLD_TAG
    }

    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);

        // get state
        State state = frontBackInterface.getCurrentState();

        switch (state) {
            // In STANDARD state, empty tags are ignored.
            case STANDARD:
                this.onStandardRead();
                break;

            // In REGISTRATION state, both empty and non-empty reads will be passed to manager.
            case REGISTRATION:
                this.onRegistrationRead();
                break;

            // In new_tag state, overwrite the tag regardless;
            case NEW_TAG:
                this.onWrite();
                break;

            // in this case, we don't have to write a new id
            // we just need to read the old id and then pass the id back to front id
            case OLD_TAG:
                this.onOldTagRead();
                break;

        }
    }

    public void onStandardRead() {
        if (!this.isTagEmpty()) {
            tagManager.onStandardRead(this.readTag());
        }
        // simply ignore the scan if the tag is empty
    }

    public void onRegistrationRead() {

        if (this.isTagEmpty()) {
            tagManager.onRegistrationRead(true, null);
        } else {
            tagManager.onRegistrationRead(false, this.readTag());
        }

    }

    public void onOldTagRead() {

        frontBackInterface.onTagRegister(this.readTag());
    }

    public void onWrite() {

        // Assign a new uuid
        String newID = tagManager.generateUUID();

        // Write the new ID into tag, if succeed, register id
        if (this.writeTag(newID)){
            frontBackInterface.onTagRegister(newID);
        } else {
            // TODO: return error message to frontend
            frontBackInterface.onTagRegisterFailure();
        }

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

    private boolean writeTag(String id) {

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
                    return false;
                }
                try {
                    ndef.close();
                    return true;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("NFC Tag", "Connection cannot be closed, please try again!");
                    return false;
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
                    return false;

                }
                try {
                    format.close();
                    return true;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("NFC Tag", "Connection cannot be closed, please try again!");
                    return false;
                }

            }
        }

        return false;
    }

}
