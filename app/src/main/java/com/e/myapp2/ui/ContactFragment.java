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
import com.e.myapp2.data.LocalContact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ContactFragment extends Fragment {

    private FragmentListener listener;
    // The ListView
    private ListView lstNames;

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private RecyclerView recycler;
    private ContactAdapter adapter;
    private View ok;
    private View cancel;

    public ContactFragment() {
        // Required empty public constructor
    }

    public ContactFragment(FragmentListener listener) {
        this.listener = listener;
    }


    public static ContactFragment newInstance(FragmentListener listener) {
        ContactFragment fragment = new ContactFragment(listener);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        recycler = view.findViewById(R.id.contact_recycler);
        ok = view.findViewById(R.id.contact_ok);
        cancel = view.findViewById(R.id.contact_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOk(adapter.getSelectedContacts());
            }
        });

        showContacts();

        return view;
    }


    private void buidAdapter(List<LocalContact> localContacts) {
        Collections.sort(localContacts);

        adapter = new ContactAdapter(getContext(), localContacts);
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
                public void onContactsReady(List<LocalContact> localContacts) {
                    localContacts = removeSmallNumbers(localContacts);   // <10
                    removeUneceserrySymbols(localContacts);
                    fixFormat(localContacts); // +972 -> 0
                    localContacts = removeSmallNumbers(localContacts);   // <10
                    localContacts = removeNot05(localContacts);
                    localContacts = removeDuplicates(localContacts);
                    buidAdapter(localContacts);
                }
            }).execute();
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
//            lstNames.setAdapter(adapter);
        }
    }

    private List<LocalContact> removeNot05(List<LocalContact> localContacts) {
        List<LocalContact> tmp = new ArrayList<>();
        for (LocalContact localContact : localContacts) {
            if (localContact.getPhoneNumber().substring(0, 2).equals("05")) {
                tmp.add(localContact);
            }
        }
        return tmp;
    }

    private void removeUneceserrySymbols(List<LocalContact> localContacts) {
        String phone;
        for (LocalContact localContact : localContacts) {
            phone = localContact.getPhoneNumber();
            phone = phone.replaceAll(" ", "");
            phone = phone.replaceAll("\\(", "");
            phone = phone.replaceAll("\\)", "");
            phone = phone.replaceAll("-", "");
            phone = phone.replaceAll("\\+", "");
            localContact.setPhoneNumber(phone);
        }
    }

    private List<LocalContact> removeSmallNumbers(List<LocalContact> localContacts) {
        List<LocalContact> tmp = new ArrayList<>();
        for (LocalContact localContact : localContacts) {
            if (localContact.getPhoneNumber().length() >= 10) {
                tmp.add(localContact);
            }
        }
        return tmp;
    }

    private void fixFormat(List<LocalContact> localContacts) {
        for (LocalContact localContact : localContacts) {
            // +972
            String phone = localContact.getPhoneNumber();
            if (phone.length() > 0 && phone.substring(0, 1).equals("9")) {
                phone = "0" + phone.substring(3);
            }
            localContact.setPhoneNumber(phone);
        }
    }

    private List<LocalContact> removeDuplicates(List<LocalContact> localContacts) {
        // Create a new ArrayList
        ArrayList<LocalContact> newList = new ArrayList<>();

        // Traverse through the first list
        for (LocalContact localContact : localContacts) {

            // If this element is not present in newList
            // then add it
            if (!phoneExist(newList, localContact)) {
                newList.add(localContact);
            }
        }
        return newList;
    }

    private boolean phoneExist(ArrayList<LocalContact> newList, LocalContact localContact) {
        for (LocalContact newCont : newList) {
            if (newCont.getPhoneNumber().equals(localContact.getPhoneNumber())) {
                return true;
            }
        }
        return false;
    }

    public interface FragmentListener{
        void onOk(List<LocalContact> selectedLocalContacts);
        void onCancel();
    }

}
