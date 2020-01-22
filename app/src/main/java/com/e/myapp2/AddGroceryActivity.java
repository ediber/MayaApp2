package com.e.myapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;


public class AddGroceryActivity extends AppCompatActivity {

    private View addContact;
    private ContactFragment contactFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        addContact = findViewById(R.id.grocery_add_contact_button); //

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 contactFragment = new ContactFragment().newInstance();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.grocery_frame, contactFragment);
                transaction.commit();

                findViewById(R.id.grocery_constraintLayout2).setVisibility(View.GONE);
            }
        });
    }
}
