package com.e.myapp2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.myapp2.R;
import com.e.myapp2.data.Contact;
import com.e.myapp2.data.NameIdPair;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter {

    private List<Contact> contacts;

    public ContactAdapter(Context context, List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Contact contact = contacts.get(position);
        ((MyViewHolder)holder).nameText.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;

        public MyViewHolder(@NonNull View view) {
            super(view);
            nameText = view.findViewById(R.id.contact_row_name);
        }
    }
}
