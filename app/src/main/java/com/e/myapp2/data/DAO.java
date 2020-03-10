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
    private FireBaseHelper fBHelper = new FireBaseHelper();

    // static variable single_instance of type Singleton
    private static DAO single_instance = null;
    //private HasGroceriesListener groceriesListener;

    private DAO(Context context) {
        this.context = context;
        myContact = new Contact("0548426419");
        container = new Container();
    }

    // create only once dao object using singleton design pattern.
    // static method to create instance of Singleton class
    public static DAO getInstance(Context context)
    {
        if (single_instance == null)
            single_instance = new DAO(context);

        return single_instance;
    }


    //return the grocery list name
    public void getGroceryPairs(final HasGroceriesListener groceriesListener) {

        fBHelper.getGroceriesFromFB(new FireBaseHelper.HasGroceryListener() {
            @Override
            public void hasGroceries(final List<Grocery> groceries) {
                final List<Grocery> myUserGroceries = new ArrayList<>();

                fBHelper.getGrocery_contactFromFB(new FireBaseHelper.HasGrocery_contactListener() {
                    @Override
                    public void hasGrocery_contact(List<Grocery_Contact> grocery_contacts) {
                        for (Grocery grocery : groceries) {
                            if(belongsToUser(myContact, grocery, grocery_contacts)){
                                myUserGroceries.add(grocery);
                            }
                        }

                        List<NameIdPair> pairs = new ArrayList<>();
                        for (Grocery grocery : myUserGroceries) {
                            pairs.add(new NameIdPair(grocery.getName(),grocery.getId()));
                        }

                        groceriesListener.hasPairs(pairs);
                    }
                });



            }
        });



    }



    private boolean belongsToUser(final Contact myContact, final Grocery grocery, List<Grocery_Contact> grocery_contacts) {

        final boolean[] belongs = {false};

        for (Grocery_Contact grocery_contact : grocery_contacts) {
            if(grocery_contact.getGroceryId().equals(grocery.getId()) &&
                    grocery_contact.getContactPhone().equals(myContact.getPhoneNumber())){
                belongs[0] = true;
            }
        }

        return belongs[0];
    }


    // return all items of a specific list
    public void getItems(final String id, final HasItemsListener listener) {
        fBHelper.getGroceriesFromFB(new FireBaseHelper.HasGroceryListener() {
            @Override
            public void hasGroceries(List<Grocery> groceries) {
                for (Grocery grocery: groceries) {
                    if(grocery.getId().equals(id)){
                        listener.hasItems(grocery.getItems());
                    }
                }
            }
        });

    }

    // add a new shopping list
    public void addGrocery(String groceryName, final List<LocalContact> localContacts) {
        final List<Contact> newContacts = localToGeneral(localContacts);
        final Grocery grocery = new Grocery(groceryName);
        newContacts.add(myContact);

      //  container.insertNewContacts(contacts);
        //fBHelper.insertNewContacts(contacts);
        fBHelper.getContactsFromFB(new FireBaseHelper.HasContactListener() {
            @Override
            public void hasContacts(List<Contact> fbContacts) {
                List<Contact> unExistContacts = insetContactIfNotExist(newContacts, fbContacts);
                fBHelper.addContactsToFB(unExistContacts);

                fBHelper.addGrocery(grocery);
                addGroceryContact(newContacts, grocery);
            }
        });


    }

     // change a local contact to contact.
    private List<Contact> localToGeneral(List<LocalContact> localContacts) {
        List<Contact> generals = new ArrayList<>();
        for (Contact local : localContacts) {
            generals.add(local);
        }
        return generals;
    }


    // insert to Grocery_Contact list.
    private void addGroceryContact(List<Contact> contacts, Grocery grocery) {
        for (Contact contact : contacts) {
            fBHelper.addGroceryContact(grocery.getId(), contact.getPhoneNumber());
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

    // get item by groceries id and item id, used for item's details.
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

    //adding a user to dbContact if not exist.
    private List<Contact> insetContactIfNotExist(List<Contact> myContacts, List<Contact> fbContacts) {

        List<Contact> newContacts = new ArrayList<>();
        for (Contact myContact : myContacts) {
            boolean exist = false;
            for (Contact fbContact : fbContacts) {
                if (myContact.getPhoneNumber().equals(fbContact.getPhoneNumber())) {
                    exist = true;
                }
            }
            if (!exist) {
                newContacts.add(myContact);
            }
        }
        return newContacts;
    }


    public interface HasGroceriesListener{
        void hasGroceries(List<Grocery> groceries);
        void hasPairs(List<NameIdPair> pairs);
    }

    public interface HasItemsListener{
        void hasItems(List<Item> items);
    }
}
