package com.e.myapp2.data;

public class Contact implements Comparable{
    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    // a function to support the sorting of the contacts name list.
    public int compareTo(Object other) {
        String otherName = ((Contact)other).getName();
        String otherLetter = otherName.substring(0,2);
        String letter = name.substring(0,2);
        int res = letter.compareTo(otherLetter);
        return res;
    }
}
