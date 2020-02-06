package com.e.myapp2.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.e.myapp2.ContactsTask;
import com.e.myapp2.R;
import com.e.myapp2.adapters.ContactAdapter;
import com.e.myapp2.data.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ContactFragment extends Fragment {

    // The ListView
    private ListView lstNames;

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private RecyclerView recycler;
    private ContactAdapter adapter;

    public ContactFragment() {
        // Required empty public constructor
    }


    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        recycler = view.findViewById(R.id.contact_recycler);

       showContacts();

        return view;
    }



    private void buidAdapter(List<Contact> contacts) {
        Collections.sort(contacts);

        adapter = new ContactAdapter(getContext(), contacts);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
         //   List<String> contacts = getContactNames();
       //     List<String> contacts = getContactNumbers();
            new ContactsTask(getContext(), new ContactsTask.TasklListener() {
                @Override
                public void onContactsReady(List<Contact> contacts) {
                    contacts = removeSmallNumbers(contacts);   // <10
                    removeUneceserrySymbols(contacts);
                    fixFormat(contacts); // +972 -> 0
                    contacts = removeSmallNumbers(contacts);   // <10
                    contacts = removeNot05(contacts);
                    contacts = removeDuplicates(contacts);
                    buidAdapter(contacts);
                }
            }).execute();
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
//            lstNames.setAdapter(adapter);
        }
    }

    private List<Contact> removeNot05(List<Contact> contacts) {
        List<Contact> tmp = new ArrayList<>();
        for (Contact contact : contacts) {
            if(contact.getPhoneNumber().substring(0,2).equals("05")){
                tmp.add(contact);
            }
        }
        return tmp;
    }

    private void removeUneceserrySymbols(List<Contact> contacts) {
        String phone;
        for (Contact contact : contacts) {
            phone = contact.getPhoneNumber();
            phone = phone.replaceAll(" ","");
            phone = phone.replaceAll("\\(","");
            phone = phone.replaceAll("\\)","");
            phone = phone.replaceAll("-","");
            phone = phone.replaceAll("\\+","");
            contact.setPhoneNumber(phone);
        }
    }

    private List<Contact> removeSmallNumbers(List<Contact> contacts) {
        List<Contact> tmp = new ArrayList<>();
        for (Contact contact : contacts) {
            if(contact.getPhoneNumber().length() >= 10){
                tmp.add(contact);
            }
        }
        return tmp;
    }

    private void fixFormat(List<Contact> contacts) {
        for (Contact contact : contacts) {
            // +972
            String phone = contact.getPhoneNumber();
            if(phone.length() > 0 && phone.substring(0,1).equals("9")){
                phone = "0" + phone.substring(3);
            }
            contact.setPhoneNumber(phone);
        }
    }

    private List<Contact> removeDuplicates(List<Contact> contacts) {
        // Create a new ArrayList
        ArrayList<Contact> newList = new ArrayList<>();

        // Traverse through the first list
        for (Contact contact : contacts) {

            // If this element is not present in newList
            // then add it
            if (! phoneExist(newList, contact)) {
                newList.add(contact);
            }
        }
        return newList;
    }

    private boolean phoneExist(ArrayList<Contact> newList, Contact contact) {
        for (Contact newCont: newList) {
            if(newCont.getPhoneNumber().equals(contact.getPhoneNumber())){
                return true;
            }
        }
        return false;
    }


}
