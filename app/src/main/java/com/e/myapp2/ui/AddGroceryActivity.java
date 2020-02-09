package com.e.myapp2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.e.myapp2.R;
import com.e.myapp2.adapters.GroceryAddSelectedAdapter;
import com.e.myapp2.data.LocalContact;
import com.e.myapp2.data.DAO;

import java.util.List;


public class AddGroceryActivity extends AppCompatActivity {

    private View addContact;
    private ContactFragment contactFragment;
    private RecyclerView recycler;
    private View save;
    private View cancel;
    private EditText groceryName;
    private List<LocalContact> selectedLocalContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        addContact = findViewById(R.id.grocery_add_contact_button);
        recycler = findViewById(R.id.grocery_add_recycler);
        save = findViewById(R.id.grocery_add_save);
        cancel = findViewById(R.id.grocery_add_cancel);
        groceryName = findViewById(R.id.grocery_name_edit);

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 contactFragment = new ContactFragment().newInstance(new ContactFragment.FragmentListener() {

                     @Override
                     public void onOk(List<LocalContact> selectedLocalContacts) {
                         AddGroceryActivity.this.selectedLocalContacts = selectedLocalContacts;
                         GroceryAddSelectedAdapter adapter = new GroceryAddSelectedAdapter(selectedLocalContacts);
                         recycler.setAdapter(adapter);
                         recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                         findViewById(R.id.grocery_constraintLayout2).setVisibility(View.VISIBLE);
                         findViewById(R.id.grocery_frame).setVisibility(View.GONE);
                     }

                     @Override
                     public void onCancel() {
                         movToMainActivity();
                     }
                 });
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.grocery_frame, contactFragment);
                transaction.commit();

                findViewById(R.id.grocery_constraintLayout2).setVisibility(View.GONE);
                findViewById(R.id.grocery_frame).setVisibility(View.VISIBLE);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO check if groceryName not empty
                DAO.getInstance(getApplicationContext()).addGrocery(groceryName.getText().toString(), selectedLocalContacts);
                movToMainActivity();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movToMainActivity();
            }
        });


    }

    private void movToMainActivity() {
        Intent intent = new Intent(AddGroceryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
