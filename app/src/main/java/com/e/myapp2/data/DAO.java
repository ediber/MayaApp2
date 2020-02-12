package com.e.myapp2.data;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class DAO {

    private Container container;
    private ArrayList<Grocery> groceries;
    private Context context;
    private long groceryId = 0;
    private Contact myContact;

    // static variable single_instance of type Singleton
    private static DAO single_instance = null;

    private DAO(Context context) {
        this.context = context;
        myContact = new Contact("0548426419");
        groceries = new ArrayList<>();
        container = new Container();
 //       generateGroceries();
    }

    // static method to create instance of Singleton class
    public static DAO getInstance(Context context)
    {
        if (single_instance == null)
            single_instance = new DAO(context);

        return single_instance;
    }


/*    private void generateGroceries() {
        groceries = new ArrayList<Grocery>();

        Grocery grocery0 = generateGrocery("grocry0");
        Grocery grocery1 = generateGrocery("grocry1");
        Grocery grocery2 = generateGrocery("grocry2");
        Grocery grocery3 = generateGrocery("grocry3");

        groceries.add(grocery0);
        groceries.add(grocery1);
        groceries.add(grocery2);
        groceries.add(grocery3);
    }*/

/*    private Grocery generateGrocery(String groceryName) {
        List<Item> items0 = new ArrayList<Item>();
        items0.add(new Item(groceryName + "," + "item0_0"));
        items0.add(new Item(groceryName + "," + "item0_1"));
        items0.add(new Item(groceryName + "," + "item2"));
        items0.add(new Item(groceryName + "," + "item3"));


        User user0 = new User(myPhoneNumber, "pass0");
        List<User> users0 = new ArrayList<>();
        users0.add(user0);

        groceryId ++;
        Grocery grocery0 = new Grocery(groceryName, users0, groceryId);
        grocery0.setItems(items0);
        return grocery0;
    }*/

    //מציאת כל הרשימות ששייכות למספר טלפון מסויים.
   /* public list <list<com.e.myapp2.data.Item>> get_lists (String phoneNumber)
    {
        String number = phoneNumber;
       if( list.number_there())
       {
           list_pointer=list<item>;
       }
    }*/

    //return the grocery list name
    public List<NameIdPair> getGroceryPairs() {
        List<NameIdPair> pairs = new ArrayList<>();
        for (Grocery grocery : groceries) {
            pairs.add(new NameIdPair(grocery.getName(),grocery.getId()));
        }

        return pairs;
    }

    // print all items of a specific list
    public List<Item> getItems(String id) {
        for (Grocery grocery: groceries) {
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

    private List<Contact> localToGeneral(List<LocalContact> localContacts) {
        List<Contact> generals = new ArrayList<>();
        for (Contact local : localContacts) {
            generals.add(local);
        }
        return generals;
    }

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

    private void addGroceryContact(List<Contact> contacts, Grocery grocery) {
        for (Contact contact : contacts) {
            container.addGroceryContact(grocery.getId(), contact.getPhoneNumber());
        }
    }

    //add an item to a specific list
    public void addItem(String name, String description, Bitmap photo) {

    }

    // display item as strikethrough
    public void strikthroughItem() {
    }

    // delete item from a list
    public void deleteItem() {
    }
}
