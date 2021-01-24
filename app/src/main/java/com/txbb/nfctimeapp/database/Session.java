package com.txbb.nfctimeapp.database;

public class Session {
    private int startTime;
    private int duration;
    private String category_name;
    private String tag_id;

    public Session(int startTime, int duration, String category_name, String tag_id) {
        this.startTime = startTime;
        this.duration = duration;
        this.category_name = category_name;
        this.tag_id = tag_id;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getTag_id() {
        return tag_id;
    }
}
