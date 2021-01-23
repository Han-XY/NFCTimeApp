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

    // get the current state of the activity: one of STANDARD, REGISTRATION and NEW_TAG
    // need to change the return type
    public void getCurrentState() {

    }

    // standard state: read a non-empty tag, give start signal
    public void onTagStart(long startTime) {

    }

    // give stop signal for a tag
    public void onTagStop(long stopTime) {

    }

    // in registration state: user reads an empty tag, start empty tag registration
    public void emptyTagRegistration() {
        // change state to newTag
        // notify users that they need to input data and scan tag again
        // no need to generate new ID on your side
        // we will take care of it in the backend write step

    }

    // we are in registration state, the user reads an non-empty tag
    // give a warning that we are registering an existing tag, state remains in registration
    public void onKnownTagRegistration() {
        this.currentActor.onKnownTagRead();
    }

    // we are in new_tag state, we've successfully written a tag and added a new tag to the database
    // tell front end that our tag register is successful
    public void onTagRegister(String id) {
        this.currentActor.onTagRegister(id);
    }

    // pass all the data to the front end
    public void sync(Map<String, TagProperties> tags) {
        this.currentActor.sync(tags);
    }













}
