package com.e.myapp2.data;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class FireBaseHelper {

    final String TAG = "my fire tag";
    private FirebaseFirestore db;


    public FireBaseHelper() {
        db = FirebaseFirestore.getInstance();
    }


    public List<Contact> getContacts() {
        return null;
    }

    public void addGrocery(Grocery grocery) {
        Map<String, Object> groceryMap = new HashMap<>();

        groceryMap.put("name", grocery.getName());
        groceryMap.put("id", grocery.getId());
        db.collection("groceries")
                .add(groceryMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void addGroceryContact(String groceryId, String phoneNumber) {
        Map<String, Object> groceryMap = new HashMap<>();

        groceryMap.put("grocery_id", groceryId);
        groceryMap.put("phone_number", phoneNumber);
        db.collection("grocery_contact")
                .add(groceryMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    public void addContactsToFB(List<Contact> contacts) {
        Map<String, Object> contactMap = new HashMap<>();

        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            contactMap.put("phone_number", contact.getPhoneNumber());
            db.collection("contacts")
                    .add(contactMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    public void getContactsFromFB(final HasContactListener listener) {
        db.collection("contacts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Contact> contacts = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                if(document.getData().size() > 0 ){
                                    Contact contact = new Contact(document.getData().get("phone_number").toString());
                                    contacts.add(contact);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        //   Log.d(TAG, "index " + i);
                        listener.hasContacts(contacts);
                    }
                });
    }

    public void getGroceriesFromFB(final HasGroceryListener listener) {
        final ArrayList<Grocery> myGroceries = new ArrayList<>();
        db.collection("groceries")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Grocery grocery = new Grocery(document.getData().get("name").toString(),
                                        document.getData().get("id").toString());
                                myGroceries.add(grocery);
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        //   Log.d(TAG, "index " + i);
                        listener.hasGroceries(myGroceries);
                    }
                });
    }

    public void getGrocery_contactFromFB(final HasGrocery_contactListener listener) {
        db.collection("grocery_contact")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Grocery_Contact> grocery_contacts = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Grocery grocery = new Grocery(document.getData().get("name").toString());
//                                myGroceries.add(grocery);
                                if(document.getData().size() > 0){
                                    Grocery_Contact grocery_contact = new Grocery_Contact(document.getData().get("grocery_id").toString(),
                                            document.getData().get("phone_number").toString());
                                    grocery_contacts.add(grocery_contact);
                                }
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        //   Log.d(TAG, "index " + i);
                        listener.hasGrocery_contact(grocery_contacts);
                    }
                });
    }




    public interface HasGroceryListener {
        void hasGroceries(List<Grocery> groceries);
    }

    public interface HasContactListener {
        void hasContacts(List<Contact> contacts);
    }

    public interface HasGrocery_contactListener {
        void hasGrocery_contact(List<Grocery_Contact> grocery_contacts);
    }
}
