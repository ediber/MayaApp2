package com.e.myapp2.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grocery {
   private List<Item> items;
   private String name;
 //  private List<User> users;
   private String id;

    public Grocery( String name) {
        this.name = name;
   //     this.users = users;

        this.id  = (long) (Math.random() * Long.MAX_VALUE + 1) + "";
        items = new ArrayList<>();

    }

    public Grocery(String name, String id) {
        this.name = name;
        this.id = id;
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
