package com.txbb.nfctimeapp.database;


public class Tag {

    private String id;
    private String name;
    private String categoryName;
    private int startTime;

    public Tag(String id, String name, String categoryName, int startTime) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getStartTime() {
        return startTime;
    }
}
