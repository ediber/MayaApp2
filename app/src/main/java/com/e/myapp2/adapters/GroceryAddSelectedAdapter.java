package com.e.myapp2.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.myapp2.R;
import com.e.myapp2.data.LocalContact;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class GroceryAddSelectedAdapter extends RecyclerView.Adapter {

    private List<LocalContact> selectedLocalContacts;

    public GroceryAddSelectedAdapter(List<LocalContact> selectedLocalContacts) {
        this.selectedLocalContacts = selectedLocalContacts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_contacts_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).textName.setText(selectedLocalContacts.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return selectedLocalContacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;

        public MyViewHolder(@NonNull View view) {
            super(view);
            textName = view.findViewById(R.id.selected_contacts_row_name);
        }
    }
}
