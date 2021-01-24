package com.txbb.nfctimeapp.database;

import java.util.ArrayList;

public class CategoryHistory {

    private int category_id;
    private long total_duration;
    private long avg_duration;
    private ArrayList<Session> sessions;

    public CategoryHistory(int category_id, long total_duration, long avg_duration, ArrayList<Session> sessions) {
        this.category_id = category_id;
        this.total_duration = total_duration;
        this.avg_duration = avg_duration;
        this.sessions = sessions;
    }

    public int getCategory_id() {
        return category_id;
    }

    public long getTotal_duration() {
        return total_duration;
    }

    public long getAvg_duration() {
        return avg_duration;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }
}
