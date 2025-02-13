package com.appsqr_task;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.PopupMenu;
public class CreateTripActivity extends AppCompatActivity {

    android.app.AlertDialog dialogFeedback;
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
        String strTripStyle=etCrtTripTravelStyle.getText().toString();
        String strTripDesc=etCrtTripDesc.getText().toString();
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
            etCrtTripDesc.setError("Please enter trip description");
            etCrtTripDesc.requestFocus();
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
                    DialogFeedback();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()) // Dismiss on No
                .show();
    }

    public void DialogFeedback()
    {
        LayoutInflater inflater = LayoutInflater.from(CreateTripActivity.this);
        View dialogLayout = inflater.inflate(R.layout.dialog_feedback, null);
        dialogFeedback = new android.app.AlertDialog.Builder(CreateTripActivity.this).create();
        dialogFeedback.setCancelable(false);
        dialogFeedback.setView(dialogLayout);
        dialogFeedback.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
        dialogFeedback.show();
        ImageView ivDialogFeedbackClose=dialogFeedback.findViewById(R.id.ivDialogFeedbackClose);
       AppCompatEditText etFeedbackName=dialogFeedback.findViewById(R.id.etFeedbackName);
       AppCompatEditText etFeedbackMobile=dialogFeedback.findViewById(R.id.etFeedbackMobile);
       AppCompatEditText etFeedbackDesc=dialogFeedback.findViewById(R.id.etFeedbackDesc);
       AppCompatButton btnFeedbackSubmit=dialogFeedback.findViewById(R.id.btnFeedbackSubmit);

        ivDialogFeedbackClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFeedback.dismiss();
            }
        });
        btnFeedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etFeedbackName.getText().toString().trim();
                String mobile = etFeedbackMobile.getText().toString().trim();
                String feedback = etFeedbackDesc.getText().toString().trim();

                if (name.isEmpty()) {
                    etFeedbackName.setError("Please enter your name");
                    etFeedbackName.requestFocus();
                    return;
                }

                if (mobile.isEmpty()) {
                    etFeedbackMobile.setError("Please enter your mobile number");
                    etFeedbackMobile.requestFocus();
                    return;
                } else if (mobile.length() != 10) {
                    etFeedbackMobile.setError("Mobile number must be 10 digits");
                    etFeedbackMobile.requestFocus();
                    return;
                }
                else if(mobile.startsWith("0")||mobile.startsWith("1")||mobile.startsWith("2")||mobile.startsWith("3")
                ||mobile.startsWith("4")||mobile.startsWith("5"))
                {
                    etFeedbackMobile.setError("Please enter valid mobile number");
                    etFeedbackMobile.requestFocus();
                    return;
                }

                if (feedback.isEmpty()) {
                    etFeedbackDesc.setError("Please enter your feedback");
                    etFeedbackDesc.requestFocus();
                    return;
                }
                dialogFeedback.dismiss();
                finish();
            }
        });

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