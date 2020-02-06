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
import com.e.myapp2.data.ListDAO;
import com.e.myapp2.R;

import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private View addItem;
    private ListDAO listDAO;
    private Object items;
    private ItemAdapter adapter;
    private List<Item> itemList;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_items);

        addItem =  findViewById(R.id.add_item_button);

        Intent intent = getIntent();
        id = intent.getLongExtra("grocery_id", -1);


        listDAO = new ListDAO(getApplicationContext());
        items = listDAO.getItems(id);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_items);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ItemAdapter((List<Item>) items);
        recyclerView.setAdapter(adapter);
    }

    // TODO addItem
}
