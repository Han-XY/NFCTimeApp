package com.txbb.nfctimeapp.category;

public class Category {

    private String name;
    private int icon;
    private int id;

    public Category(String name, int icon, int id) {
        this.name = name;
        this.icon = icon;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public int getIcon() {
        return this.icon;
    }

    public int getCategoryId() {
        return this.id;
    }

}
