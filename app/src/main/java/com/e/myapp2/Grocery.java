package com.e.myapp2;

import java.util.List;

public class Grocery {
   private List<Item> items;
   private String name;
   private List<User> users;
   private long id;

    public Grocery( String name, List<User> users, long id) {
        this.name = name;
        this.users = users;

//        this.id = System.currentTimeMillis();
        this.id = id;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
