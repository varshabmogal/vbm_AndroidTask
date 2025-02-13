package com.appsqr_task;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.appsqr_task.Helper.MyVolley;
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
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        getSupportActionBar().setTitle("Plan a Trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvTitle= findViewById(R.id.tvTitle);
        tvDate= findViewById(R.id.tvDate);
        tvLocation= findViewById(R.id.tvLocation);
        ivRowtripImage= findViewById(R.id.ivImg);
        tvTitle.setText(getIntent().getStringExtra("title"));
        Glide.with(TripActivity.this).load(getIntent().getStringExtra("img"))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivRowtripImage);
        load_trip();
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
    private void load_trip() {
        progressDialog = new ProgressDialog(TripActivity.this);
        progressDialog.setMessage("Loading.....");
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
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject jsonObjectCity = json.getJSONObject(i);
                                    /*String title = jsonObjectCity.getString("title").toString();
                                    String date = jsonObjectCity.getString("date").toString();
                                    //tvDate.setText(date);
                                    tvLocation.setText(title);*/
                                }
                            }  catch (JSONException e) {
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
}