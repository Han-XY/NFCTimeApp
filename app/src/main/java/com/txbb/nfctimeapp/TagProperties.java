package com.txbb.nfctimeapp;

public class TagProperties {

    private String id;
    private String name;
    private int category;
    private long startTime;
    private long endTime;

    public TagProperties(String id, String name, int category, long startTime, long endTime) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
