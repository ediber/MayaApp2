package com.e.myapp2;

public class NameIdPair {
    private  String name;
    private  long id;

    public NameIdPair(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }
}
