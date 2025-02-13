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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.appsqr_task.Adapter.ActivityAdapter;
import com.appsqr_task.Adapter.FlightAdapter;
import com.appsqr_task.Adapter.HotelAdapter;
import com.appsqr_task.Adapter.TripAdapter;
import com.appsqr_task.Constant.EndPoints;
import com.appsqr_task.Helper.ConnectionDetector;
import com.appsqr_task.Helper.MyVolley;
import com.appsqr_task.Helper.TransparentProgressDialog;
import com.appsqr_task.pojo.HotelBean;
import com.appsqr_task.pojo.PlanedTripBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripActivity extends AppCompatActivity {

    TextView tvTitle,tvDate,tvLocation;
    ImageView ivRowtripImage;
    TransparentProgressDialog  progressDialog;
    RelativeLayout rlShare,rlCollaboration;
    RecyclerView rvFlight,rvHotelList,rvActivityList;
    LinearLayoutManager mLayoutManager,mLayoutManagerActivity,mLayoutManagerFlight;
    List<HotelBean> hotelList;
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
        rvFlight= findViewById(R.id.rvFlight);
        rvHotelList= findViewById(R.id.rvHotelList);
        rvActivityList= findViewById(R.id.rvActivityList);
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


        hotelList = new ArrayList<>();
        rvHotelList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvHotelList.setLayoutManager(mLayoutManager);

        rvActivityList.setHasFixedSize(true);
        mLayoutManagerActivity = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvActivityList.setLayoutManager(mLayoutManagerActivity);

        rvFlight.setHasFixedSize(true);
        mLayoutManagerFlight = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvFlight.setLayoutManager(mLayoutManagerFlight);

        HotelList();
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


    private void HotelList()
    {
        ConnectionDetector connectionDetector=ConnectionDetector.getInstance(TripActivity.this);
        if(connectionDetector.isConnectionAvailable()) {

            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.TRIP_DETAILS1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            hotelList.clear();
                            //Log.d("response_data", "" + response);
                            if (response.equals("")) {
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONArray json = obj.getJSONArray("data");
                                    Log.d("response_data", "" + response);
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject jsonObjectCity = json.getJSONObject(i);
                                        String title = jsonObjectCity.getString("title").toString();
                                        String date = jsonObjectCity.getString("date").toString();
                                        String img = jsonObjectCity.getString("img").toString();
                                        String flighttitle = jsonObjectCity.getString("flighttitle").toString();
                                        String tm1 = jsonObjectCity.getString("tm1").toString();
                                        String tm2 = jsonObjectCity.getString("tm2").toString();
                                        String hotel = jsonObjectCity.getString("hotel").toString();
                                        String hotel_address = jsonObjectCity.getString("hotel_address").toString();
                                        String indate = jsonObjectCity.getString("indate").toString();
                                        String outtime = jsonObjectCity.getString("outtime").toString();
                                        String hotel_fees = jsonObjectCity.getString("hotel_fees").toString();
                                        String activity = jsonObjectCity.getString("activity").toString();
                                        String activty_address = jsonObjectCity.getString("activty_address").toString();
                                        String time = jsonObjectCity.getString("time").toString();
                                        String actvity_price = jsonObjectCity.getString("actvity_price").toString();
                                        HotelBean hotelmodel = new HotelBean();
                                        hotelmodel.setDate(date);
                                        hotelmodel.setImg(img);
                                        hotelmodel.setTitle(title);
                                        hotelmodel.setFlighttitle(flighttitle);
                                        hotelmodel.setTm1(tm1);
                                        hotelmodel.setTm2(tm2);
                                        hotelmodel.setHotel(hotel);
                                        hotelmodel.setHotel_address(hotel_address);
                                        hotelmodel.setIndate(indate);
                                        hotelmodel.setOuttime(outtime);
                                        hotelmodel.setHotel_fees(hotel_fees);
                                        hotelmodel.setActivity(activity);
                                        hotelmodel.setActivty_address(activty_address);
                                        hotelmodel.setActvity_price(actvity_price);
                                        hotelmodel.setTime(time);
                                        hotelList.add(hotelmodel);
                                        rvHotelList.setLayoutManager(new LinearLayoutManager(TripActivity.this));
                                        HotelAdapter adapter = new HotelAdapter(TripActivity.this, hotelList);
                                        rvHotelList.setAdapter(adapter);


                                        rvActivityList.setLayoutManager(new LinearLayoutManager(TripActivity.this));
                                        ActivityAdapter adapterActivity = new ActivityAdapter(TripActivity.this, hotelList);
                                        rvActivityList.setAdapter(adapterActivity);

                                        rvFlight.setLayoutManager(new LinearLayoutManager(TripActivity.this));
                                        FlightAdapter flightAdapter = new FlightAdapter(TripActivity.this, hotelList);
                                        rvFlight.setAdapter(flightAdapter);
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
        else
        {
            progressDialog.dismiss();
            Toast.makeText(TripActivity.this,"Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
        }
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

    @Override
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }
}