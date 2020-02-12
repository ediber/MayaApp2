package com.e.myapp2.data;

public class NameIdPair {
    private  String name;
    private  String id;

    public NameIdPair(String name, String id) {
        this.name = name;
        this.id = id;
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

//    public void setId(int id) {
//        this.id = id;
//    }
}
