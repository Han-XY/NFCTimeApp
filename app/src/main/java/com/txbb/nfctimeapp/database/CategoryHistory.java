package com.txbb.nfctimeapp.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryHistory {

    private String name;
    private ArrayList<Session> sessions;

    public CategoryHistory(String name) {
        this.name = name;
        this.sessions = new ArrayList<Session>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void addSession(Session session) {
        this.sessions.add(session);
    }
}
