package com.txbb.nfctimeapp;

import com.txbb.nfctimeapp.backend.Actor;
import com.txbb.nfctimeapp.backend.TagManager;

import java.util.Map;

public class FrontBackInterface {

    private Actor currentActor;
    private TagManager tagManager;

    public FrontBackInterface(Actor currentActor, TagManager tagManager) {
        this.currentActor = currentActor;
        this.tagManager = tagManager;
    }

    /* Front to back */

    public void updateTagProperties(String id, TagProperties tagProperties) {
        this.tagManager.registerTag(id, tagProperties);
    }

    public void deleteTag(String id) {
        this.tagManager.deleteTag(id);
    }

    public void syncRequest() {
        this.tagManager.syncRequest();
    }

    /* Back to front */

    public void onUnknownTagRead() {
        this.currentActor.onUnknownTagRead();
    }

    public void onKnownTagRead() {
        this.currentActor.onKnownTagRead();
    }

    public void onTagRegister(String id) {
        this.currentActor.onTagRegister(id);
    }

    public void sync(Map<String, TagProperties> tags) {
        this.currentActor.sync(tags);
    }

    // below are the function

    // get the current state of the activity: one of STANDARD, REGISTRATION and NEW_TAG
    public void getCurrentState() {


    }

}
