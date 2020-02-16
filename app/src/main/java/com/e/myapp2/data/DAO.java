package com.e.myapp2.data;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class DAO {

    private Container container;
  //  private ArrayList<Grocery> groceries;
    private Context context;
    private long groceryId = 0;
    private Contact myContact;

    // static variable single_instance of type Singleton
    private static DAO single_instance = null;

    private DAO(Context context) {
        this.context = context;
        myContact = new Contact("0548426419");
        container = new Container();
    }

    // static method to create instance of Singleton class
    public static DAO getInstance(Context context)
    {
        if (single_instance == null)
            single_instance = new DAO(context);

        return single_instance;
    }

// creat only once dao object using singleton design patern.
    //return the grocery list name
    public List<NameIdPair> getGroceryPairs() {
        List<Grocery> myUserGroceries = getMyUserGroceries();

        List<NameIdPair> pairs = new ArrayList<>();
        for (Grocery grocery : myUserGroceries) {
            pairs.add(new NameIdPair(grocery.getName(),grocery.getId()));
        }

        return pairs;
    }

 // find which grociries belongs to my user.
    private List<Grocery> getMyUserGroceries() {
        List<Grocery> groceries = container.getGroceries();
        List<Grocery> myUserGroceries = new ArrayList<>();
        for (Grocery grocery : groceries) {
            if(belongsToUser(myContact, grocery)){
                myUserGroceries.add(grocery);
            }
        }
        return myUserGroceries;
    }

    // find if a specific grosiry belong to my user
    private boolean belongsToUser(Contact myContact, Grocery grocery) {
        List<Grocery_Contact> grocery_contacts = container.getGrocery_contact();
        for (Grocery_Contact grocery_contact : grocery_contacts) {
            if(grocery_contact.getGroceryId().equals(grocery.getId()) &&
                    grocery_contact.getContactPhone().equals(myContact.getPhoneNumber())){
                return true;
            }
        }
        return false;
    }


    // return all items of a specific list
    public List<Item> getItems(String id) {
        List<Grocery> myUserGroceries = getMyUserGroceries();
        for (Grocery grocery: myUserGroceries) {
            if(grocery.getId().equals(id)){
               return grocery.getItems();
            }
        }
        return null;
    }

    // add a new shopping list
    public void addGrocery(String groceryName, List<LocalContact> localContacts) {
        List<Contact> contacts = localToGeneral(localContacts);
        Grocery grocery = new Grocery(groceryName);
        contacts.add(myContact);
        List<Contact> dBContacts = container.getContacts();
        insetIfNotExist(dBContacts, contacts);
        container.setContacts(dBContacts);
        container.addGrocery(grocery);
        addGroceryContact(contacts, grocery);
    }

     // change a local contact to contact.
    private List<Contact> localToGeneral(List<LocalContact> localContacts) {
        List<Contact> generals = new ArrayList<>();
        for (Contact local : localContacts) {
            generals.add(local);
        }
        return generals;
    }


     //adding a user to dbContact if not exist.
    private void insetIfNotExist(List<Contact> dBContacts, List<Contact> contacts) {
        for (Contact newContact : contacts) {
            boolean exist = false;
            for (Contact dbContact : dBContacts) {
                if(dbContact.getPhoneNumber().equals(newContact.getPhoneNumber())){
                    exist = true;
                }
            }
            if(exist){
                dBContacts.add(newContact);
            }
        }
    }

    // insert to Grocery_Contact list.
    private void addGroceryContact(List<Contact> contacts, Grocery grocery) {
        for (Contact contact : contacts) {
            container.addGroceryContact(grocery.getId(), contact.getPhoneNumber());
        }
    }

    //add an item to a specific list
    public void addItem(String name, String description, Bitmap photo, String groceryId) {
        List<Grocery> groceries = container.getGroceries();
        Item item = new Item(name, description);
        for (Grocery grocery : groceries) {
            if(grocery.getId().equals(groceryId)){
                grocery.getItems().add(item);
            }
        }
    }

    // display item as strikethrough
    public void strikthroughItem() {
        //todo
    }

    // delete item from a list
    public void deleteItem() {
        //todo
    }

    // get item by groceries id and item id, used for item's detiels.
    public Item getItem(String groceryId, String itemId) {
        List<Grocery> groceries = container.getGroceries();
        for (Grocery grocery : groceries) {
            if(grocery.getId().equals(groceryId)){
                List<Item> items = grocery.getItems();
                for (Item item : items) {
                    if(item.getId().equals(itemId)){
                        return item;
                    }
                }
            }
        }
        return null;
    }
}
