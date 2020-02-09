package com.e.myapp2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


import com.e.myapp2.adapters.GroceriesAdapter;
import com.e.myapp2.data.DAO;
import com.e.myapp2.MyApplication;
import com.e.myapp2.data.NameIdPair;
import com.e.myapp2.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DAO dao;
    private List<NameIdPair> groceries;
    private GroceriesAdapter adapter;
    private View addGrocery;
    private String myPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        addGrocery =  findViewById(R.id.add_grocery_button);

        dao = DAO.getInstance(getApplicationContext());
        groceries = dao.getGroceryPairs();

        // recyclerView of al the groceries.
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_grocery);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GroceriesAdapter(groceries, new GroceriesAdapter.GroceryListener() {
            @Override
            public void onGroceryClicked(long id) {

                // intent from the chosen grocery to recyclerView of it's items.
                Intent myIntent = new Intent(MainActivity.this, ItemsActivity.class);
                myIntent.putExtra("grocery_id", id);
                startActivity(myIntent);
            }
        });


        recyclerView.setAdapter(adapter);

        //add grocery button.
        addGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddGroceryActivity.class);
                startActivity(myIntent);
            }
        });

        MyApplication myApplication = (MyApplication) getApplication();
        myPhoneNumber = myApplication.getMyPhoneNumber();

    }
}
