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
                    buidAdapter(contacts);
                }
            }).execute();
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
//            lstNames.setAdapter(adapter);
        }
    }






}
