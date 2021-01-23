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
        this.tagManager.setFrontBackInterface(this);
    }

    /* Front to back */

    public void updateTagProperties(String id, TagProperties tagProperties) {
        this.tagManager.registerTag(id, tagProperties);
    }

    public void deleteTag(String id) {

        this.tagManager.deleteTag(id);
    }

    // front end needs all the data
    public void syncRequest() {
        this.tagManager.syncRequest();
    }

    /* Back to front */

    // get the current state of the activity: one of STANDARD, REGISTRATION and NEW_TAG
    // need to change the return type
    // TODO: need to add a new state OLD_TAG
    public void getCurrentState() {

    }

    // STANDARD state: read a non-empty tag, give start signal
    public void onTagStart(String id, long startTime) {

    }

    // STANDARD state: give stop signal for a tag
    public void onTagStop(String id, long stopTime) {

    }

    // STANDARD state: we are scanning a tag with deleted id
    public void deletedTagNotification() {
        // need to give user notification that this is a deleted tag
        // state remains at standatd
    }

    // in registration state: user reads an empty tag, start empty tag registration
    public void emptyTagRegistration() {
        // change state to NEW_TAG
        // notify users that they need to take the tag away, input data and then scan tag again
        // no need to generate new ID here
        // we will take care of it in the write step in TagIO

    }

    // in registration state: user reads an old tag with a deleted id
    // start existing tag registration
    public void oldTagRegistration() {
        // change state to OLD_TAG
        // notify users to put away tag, input data and scan tag again
    }

    // we are in registration state, the user reads an non-empty tag
    // give a warning that we are registering an existing tag, state remains in registration
    public void onKnownTagRegistration() {
        this.currentActor.onKnownTagRead();
    }

    // we are in new_tag state, we've successfully registered an id to the new tag
    // or
    // we are in existing_tag state, we are using the same old id
    // tell front end that our tag register is successful
    // front end will then transit to STANDARD state and send backend the tag property info
    public void onTagRegister(String id) {
        this.currentActor.onTagRegister(id);
    }

    // we are in new_tag state and want to assign a new id to a new tag
    // however the write fails
    // notify user about the failure
    public void onTagRegisterFailure() {
        // give user notification that register fails, probably due to tag leaving too early
        // state stays at NEW_TAG
        // ask users to scan the same tag again
    }

    // pass all the data to the front end
    public void sync(Map<String, TagProperties> tags) {
        this.currentActor.sync(tags);
    }













}
