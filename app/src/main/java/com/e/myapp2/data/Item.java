package com.e.myapp2.data;

import android.graphics.Bitmap;

public class Item {
    String id;
    String name;
    String description;
    Bitmap photo;
    boolean isStrikethrough;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.id  = (long) (Math.random() * Long.MAX_VALUE + 1) + "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public boolean isStrikethrough() {
        return isStrikethrough;
    }

    public void setStrikethrough(boolean strikethrough) {
        isStrikethrough = strikethrough;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
