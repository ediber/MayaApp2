package com.e.myapp2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ContactFragment extends Fragment {

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

//        view.findViewById(R.id.)

        return view;
    }




}
