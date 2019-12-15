package com.e.myapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


import com.e.myapp2.GroceriesAdapter;
import com.e.myapp2.ListDAO;
import com.e.myapp2.NameIdPair;
import com.e.myapp2.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ListDAO listDao;
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

        listDao = new ListDAO(getApplicationContext());
        groceries = listDao.getGroceryPairs();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_grocery);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GroceriesAdapter(groceries, new GroceriesAdapter.GroceryListener() {
            @Override
            public void onGroceryClicked(long id) {
                Intent myIntent = new Intent(MainActivity.this, ItemsActivity.class);
                myIntent.putExtra("grocery_id", id); //Optional parameters
                startActivity(myIntent);
            }
        });


        recyclerView.setAdapter(adapter);

        addGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddGroceryActivity.class);
//                myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
            }
        });

        MyApplication myApplication = (MyApplication) getApplication();
        myPhoneNumber = myApplication.getMyPhoneNumber();

    }
}
