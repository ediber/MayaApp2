package com.e.myapp2.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.e.myapp2.R;
import com.e.myapp2.adapters.ContactAdapter;
import com.e.myapp2.data.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ContactFragment extends Fragment {

    // The ListView
    private ListView lstNames;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private List<Contact> contacts;
    private RecyclerView recycler;
    private ContactAdapter adapter;

    public ContactFragment() {
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
        //TODO contact fragment is "gone".

        contacts = new ArrayList<>();
       showContacts();


        return view;
    }

    private void getContactList() {
        // permission + achieving contact name and phone number.
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("contact", "Name: " + name);
                        Log.i("contact", "Phone Number: " + phoneNo);
                        contacts.add(new Contact(name, phoneNo));
                    }
                    pCur.close();
                }
            }
            buidAdapter();
        }
        if(cur!=null){
            cur.close();
        }
    }

    // recyclerView and sort of the contacts by name (till second letter).
    private void buidAdapter() {
        Collections.sort(contacts);

        adapter = new ContactAdapter(getContext(), contacts);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
       //  list of contacts name and list of contacts phone number;
            getContactList();
        }
    }






}
