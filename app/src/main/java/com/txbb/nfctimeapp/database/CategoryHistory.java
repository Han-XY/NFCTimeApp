package com.txbb.nfctimeapp.database;

import java.util.ArrayList;

public class CategoryHistory {

    private int category_id;
    private ArrayList<Session> sessions;

    public CategoryHistory(int category_id, ArrayList<Session> sessions) {
        this.category_id = category_id;
        this.sessions = sessions;
    }

    public int getCategory_id() {
        return category_id;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }
}
