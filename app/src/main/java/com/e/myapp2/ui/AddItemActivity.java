package com.e.myapp2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.myapp2.R;
import com.e.myapp2.data.DAO;
import com.e.myapp2.data.Item;

public class AddItemActivity extends AppCompatActivity {

    private String id;
    private EditText name;
    private EditText description;
    private ImageView photo;
    private View save;
    private View cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        name = findViewById(R.id.add_item_name);
        description = findViewById(R.id.add_item_description);
        photo = findViewById(R.id.add_item_photo);
        save = findViewById(R.id.add_item_save);
        cancel = findViewById(R.id.add_item_cancel);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            id = extras.getString("grocery_id");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validData()){
                    DAO.getInstance(getApplicationContext()).addItem(name.getText().toString(), description.getText().toString(), null, id);

                    Intent myIntent = new Intent(AddItemActivity.this, ItemsActivity.class);
                    myIntent.putExtra("grocery_id", id); //Optional parameters
                    startActivity(myIntent);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AddItemActivity.this, ItemsActivity.class);
                myIntent.putExtra("grocery_id", id); //Optional parameters
                startActivity(myIntent);
            }
        });
    }

    private boolean validData() {
        if(! name.getText().equals("")){
            return true;
        }
        Toast.makeText(this, "insert name", Toast.LENGTH_SHORT).show();
        return false;
    }
}
