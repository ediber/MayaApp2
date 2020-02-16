package com.e.myapp2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.e.myapp2.data.Item;
import com.e.myapp2.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MViewHolder> {
    private AdapterListener listener;
    private List <Item> items;
    public ItemAdapter(List<Item> items, AdapterListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemAdapter.MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mv =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        ItemAdapter.MViewHolder vh = new MViewHolder(mv);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MViewHolder holder, int position) {
        final Item item = items.get(position);
        holder.textItem.setText(item.getName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(item.getId());
            }
        });

        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder {
        public View parent;
        public TextView textItem;

        public MViewHolder(@NonNull View view) {
            super(view);
            textItem = view.findViewById(R.id.text_item);
            parent = view.findViewById(R.id.text_item_parent);
        }
    }

    public interface AdapterListener{
        void onClick(String itemId);
        void onLongClick();
    }
}
