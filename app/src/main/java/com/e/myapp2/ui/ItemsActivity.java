package com.e.myapp2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


import com.e.myapp2.data.Item;
import com.e.myapp2.adapters.ItemAdapter;
import com.e.myapp2.data.DAO;
import com.e.myapp2.R;

import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private View addItem;
    private DAO DAO;
    private ItemAdapter adapter;
    private List<Item> itemList;
    private String groceryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_items);

        addItem =  findViewById(R.id.add_item_button);


        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            groceryId = extras.getString("grocery_id");
        }

        DAO = DAO.getInstance(getApplicationContext());
        DAO.getItems(groceryId + "", new DAO.HasItemsListener() {
            @Override
            public void hasItems(List<Item> items) {
                generateRecyclerView(items);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemsActivity.this, AddItemActivity.class);
                intent.putExtra("grocery_id", groceryId);
                startActivity(intent);
            }
        });
    }

    private void generateRecyclerView(List<Item> items) {
        // recyclerView of al the items.
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_items);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ItemAdapter(items, new ItemAdapter.AdapterListener() {
            @Override
            public void onClick(String itemId) {
                Intent intent = new Intent(ItemsActivity.this, ItemDetailsActivity.class);
                intent.putExtra("grocery_id", groceryId);
                intent.putExtra("item_id", itemId);
                startActivity(intent);
            }

            @Override
            public void onLongClick() {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    // TODO addItem
}
