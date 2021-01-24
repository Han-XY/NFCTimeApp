package com.txbb.nfctimeapp;

public class TagProperties {

    private String name;
    private int category;
    private long startTime;
    private long endTime;

    public TagProperties(String name, int category) {
        this.name = name;
        this.category = category;
    }

    public TagProperties(String name, int category, long starTime, long endTime) {
        this.name = name;
        this.category = category;
        this.startTime = starTime;
        this.endTime = endTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return this.name;
    }

    public int getCategory() {
        return this.category;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }
}
