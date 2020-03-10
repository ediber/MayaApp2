package com.e.myapp2.data;

import java.util.ArrayList;
import java.util.List;

public class Container {
    private List<Contact> contacts;
    private List<Grocery> groceries;
    private List<Grocery_Contact> grocery_contact;

    public Container() {
        contacts = new ArrayList<>();
        groceries = new ArrayList<>();
        grocery_contact = new ArrayList<>();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Grocery> getGroceries() {
        return groceries;
    }

    public void setGroceries(List<Grocery> groceries) {
        this.groceries = groceries;
    }

    public List<Grocery_Contact> getGrocery_contact() {
        return grocery_contact;
    }

    public void setGrocery_contact(List<Grocery_Contact> grocery_contact) {
        this.grocery_contact = grocery_contact;
    }

    public void addGrocery(Grocery grocery) {
        groceries.add(grocery);
    }

    public void addGroceryContact(String groceryId, String phoneNumber) {
        grocery_contact.add(new Grocery_Contact(groceryId, phoneNumber));
    }

    public void insertNewContacts(List<Contact> contacts) {
        insetContactIfNotExist(this.contacts, contacts);
    }

    //adding a user to dbContact if not exist.
    private void insetContactIfNotExist(List<Contact> dBContacts, List<Contact> contacts) {
        for (Contact newContact : contacts) {
            boolean exist = false;
            for (Contact dbContact : dBContacts) {
                if(dbContact.getPhoneNumber().equals(newContact.getPhoneNumber())){
                    exist = true;
                }
            }
            if(! exist){
                dBContacts.add(newContact);
            }
        }
    }
}
