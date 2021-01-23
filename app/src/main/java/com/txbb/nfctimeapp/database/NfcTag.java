package com.txbb.nfctimeapp.database;


public class NfcTag {

    // Sample id: 123e4567-e89b-12d3-a456-426614174000
    private String id;
    private String name;
    private String categoryName;
    private long startTime;
    private long endTime;

    public NfcTag(String id, String name, String categoryName) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
    }

    public void setStartTime(long startTime) { this.startTime = startTime; }

    public void setEndTime(long endTime) { this.endTime = endTime; }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }
}