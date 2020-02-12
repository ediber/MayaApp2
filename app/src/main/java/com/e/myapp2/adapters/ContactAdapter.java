package com.e.myapp2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.myapp2.R;
import com.e.myapp2.data.LocalContact;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter {

    private List<LocalContact> localContacts;


    public ContactAdapter(Context context, List<LocalContact> localContacts) {
        this.localContacts = localContacts;
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
        final LocalContact localContact = localContacts.get(position);
        ((MyViewHolder)holder).nameText.setText(localContact.getName());
        ((MyViewHolder)holder).numberText.setText(localContact.getPhoneNumber());
        final View parent = ((MyViewHolder) holder).parent;
        if(localContact.isSelected()){
            parent.setBackgroundColor(Color.YELLOW);
        } else {
            parent.setBackgroundColor(Color.TRANSPARENT);
        }
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(localContact, parent);
            }
        });

        ((MyViewHolder)holder).nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(localContact, parent);
            }
        });

    }

    private void click(LocalContact localContact, View parent) {
        if(! localContact.isSelected()){
            localContact.setSelected(true);
            parent.setBackgroundColor(Color.YELLOW);
        } else {
            localContact.setSelected(false);
            parent.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        Log.d("contact adapter - size", localContacts.size()+"");
        return localContacts.size();
    }

    public List<LocalContact> getSelectedContacts() {
        List<LocalContact> selected = new ArrayList<>();
        for (LocalContact localContact : localContacts) {
            if(localContact.isSelected()){
                selected.add(localContact);
            }
        }
        return selected;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView numberText;
        public View parent;
        public TextView nameText;

        public MyViewHolder(@NonNull View view) {
            super(view);
            nameText = view.findViewById(R.id.contact_row_name);
            parent = view.findViewById(R.id.contact_row_parent);
            numberText = view.findViewById(R.id.contact_row_number);
        }
    }

    public interface AdapterListener{
        void onSave(List<LocalContact> selected);
    }
}
