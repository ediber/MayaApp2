package com.e.myapp2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.e.myapp2.data.NameIdPair;
import com.e.myapp2.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class GroceriesAdapter extends RecyclerView.Adapter<GroceriesAdapter.MyViewHolder> {

    public interface GroceryListener{
        void onGroceryClicked(long id);
    }

    private List<NameIdPair> groceries;
    private GroceryListener listener;

    public GroceriesAdapter(List<NameIdPair> groceries, GroceryListener listener) {
        this.groceries = groceries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroceriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.groceries_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull GroceriesAdapter.MyViewHolder holder, final int position) {
        final NameIdPair pair = groceries.get(position);
        holder.textGrocery.setText(pair.getName());
        holder.textGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameIdPair pair = groceries.get(position);
                listener.onGroceryClicked(pair.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textGrocery;

        public MyViewHolder(@NonNull View view) {
            super(view);
            textGrocery = view.findViewById(R.id.text_grocery);
        }
    }
}
