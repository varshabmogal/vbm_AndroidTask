package com.appsqr_task;

import android.app.ProgressDialog;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.appsqr_task.Constant.EndPoints;
import com.appsqr_task.Helper.ConnectionDetector;
import com.appsqr_task.Helper.MyVolley;
import com.appsqr_task.Helper.TransparentProgressDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TripActivity extends AppCompatActivity {

    TextView tvTitle,tvDate,tvLocation;
    ImageView ivRowtripImage;
    TransparentProgressDialog  progressDialog;
    RelativeLayout rlShare,rlCollaboration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        getSupportActionBar().setTitle("Plan a Trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        onClick();

    }
    private void init()
    {
        progressDialog=new TransparentProgressDialog(TripActivity.this,R.drawable.progress);
        tvTitle= findViewById(R.id.tvTitle);
        tvDate= findViewById(R.id.tvDate);
        tvLocation= findViewById(R.id.tvLocation);
        ivRowtripImage= findViewById(R.id.ivImg);
        rlShare= findViewById(R.id.rlShare);
        rlCollaboration= findViewById(R.id.rlCollaboration);
        tvTitle.setText(getIntent().getStringExtra("title"));
        tvDate.setText(getIntent().getStringExtra("date")+" "+getIntent().getStringExtra("duration"));
        tvLocation.setText(getIntent().getStringExtra("city"));
        Glide.with(TripActivity.this).load(getIntent().getStringExtra("img"))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivRowtripImage);
        load_trip();
    }
    private void onClick()
    {
        rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Share();
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
    private void Share()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String title = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("date");
        String duration = getIntent().getStringExtra("duration");
        String location = getIntent().getStringExtra("city");
        String imageUrl = getIntent().getStringExtra("img");

        String message = "Trip Details:\n" +
                "Title: " + title + "\n" +
                "Date: " + date + " " + duration + "\n" +
                "Location: " + location;
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Uri imageUri = Uri.parse(imageUrl);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        shareIntent.setPackage("com.whatsapp");
        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(TripActivity.this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private void load_trip() {
        ConnectionDetector connectionDetector = ConnectionDetector.getInstance(TripActivity.this);
        if (connectionDetector.isConnectionAvailable()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.TRIP_DETAILS1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            //Log.d("response_data", "" + response);
                            if (response.equals("")) {
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONArray json = obj.getJSONArray("data");
                                    Log.d("response_data", "" + response);
                                    for (int i = 0; i < json.length(); i++)
                                    {
                                        JSONObject jsonObjectCity = json.getJSONObject(i);
                                    /*String title = jsonObjectCity.getString("title").toString();
                                    String date = jsonObjectCity.getString("date").toString();
                                    //tvDate.setText(date);
                                    tvLocation.setText(title);*/
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            // Toast.makeText(Recepi.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    return params;
                }
            };
            MyVolley.getInstance(TripActivity.this).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        else {
            progressDialog.dismiss();
            Toast.makeText(TripActivity.this,"Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }
}