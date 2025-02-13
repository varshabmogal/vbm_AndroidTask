package com.appsqr_task;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateTripActivity extends AppCompatActivity {

    AppCompatButton btnCrtTripNext;
    AppCompatEditText etCrtTripName,etCrtTripTravelStyle,etCrtTripDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        getSupportActionBar().setTitle("Plan a Trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        onclick();


    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenu().add("Solo");
        popupMenu.getMenu().add("Couple");
        popupMenu.getMenu().add("Family");
        popupMenu.getMenu().add("Group");

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            etCrtTripTravelStyle.setText(menuItem.getTitle());
            return true;
        });

        popupMenu.show();
    }
    private void init()
    {
        btnCrtTripNext=findViewById(R.id.btnCrtTripNext);
        etCrtTripName=findViewById(R.id.etCrtTripName);
        etCrtTripTravelStyle=findViewById(R.id.etCrtTripTravelStyle);
        etCrtTripDesc=findViewById(R.id.etCrtTripDesc);
    }
    private void onclick()
    {
        btnCrtTripNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etCrtTripTravelStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
        btnCrtTripNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(isValid())
                {
                    showConfirmationDialog();
                }
            }
        });
    }
    private boolean isValid()
    {
        String strTripName=etCrtTripName.getText().toString();
        String strTripStyle=etCrtTripName.getText().toString();
        String strTripDesc=etCrtTripName.getText().toString();
        if(TextUtils.isEmpty(strTripName))
        {
            etCrtTripName.setError("Please enter trip name");
            etCrtTripName.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(strTripStyle))
        {
            Toast.makeText(CreateTripActivity.this,"Please select trip style",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(strTripDesc))
        {
            etCrtTripName.setError("Please enter trip description");
            etCrtTripName.requestFocus();
            return false;
        }

        return true;
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Trip Creation")
                .setMessage("Are you sure you want to create the trip?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(CreateTripActivity.this, "Trip Created!", Toast.LENGTH_SHORT).show();

                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()) // Dismiss on No
                .show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}