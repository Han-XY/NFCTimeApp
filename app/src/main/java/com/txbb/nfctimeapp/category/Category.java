package com.txbb.nfctimeapp.category;

public class Category {

    private String name;
    private int icon;

    public Category(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return this.name;
    }

    public int getIcon() {
        return this.icon;
    }

}
