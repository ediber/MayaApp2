package com.e.myapp2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.myapp2.R;
import com.e.myapp2.data.DAO;
import com.e.myapp2.data.Item;

public class ItemDetailsActivity extends AppCompatActivity {

    private String groceryId;
    private String itemId;
    private TextView name;
    private TextView description;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        name = findViewById(R.id.item_details_name);
        description = findViewById(R.id.item_details_description);
        photo = findViewById(R.id.item_details_photo);


        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            groceryId = extras.getString("grocery_id");
            itemId = extras.getString("item_id");
        }

        Item item = DAO.getInstance(this).getItem(groceryId, itemId);
        name.setText(item.getName());
        description.setText(item.getDescription());
    }
}
