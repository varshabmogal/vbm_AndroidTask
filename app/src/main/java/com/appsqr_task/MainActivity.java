package com.appsqr_task;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.appsqr_task.Adapter.CityAdapter;
import com.appsqr_task.Adapter.TripAdapter;
import com.appsqr_task.Constant.EndPoints;
import com.appsqr_task.Helper.ConnectionDetector;
import com.appsqr_task.Helper.MyVolley;
import com.appsqr_task.Helper.OnPositionClickListener;
import com.appsqr_task.Helper.SharedPreferenceManager;
import com.appsqr_task.Helper.TransparentProgressDialog;
import com.appsqr_task.pojo.PlanedTripBean;
import com.appsqr_task.pojo.SelectCityBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    AlertDialog dialogCity;
    AlertDialog dialogCreateTrip;
    AlertDialog dialogSelectDate;
    ImageView ivDialogCityClose;
    AppCompatEditText etDialogCitySearch;
    RecyclerView rvDialogCity;
    RelativeLayout rlMainSelectCity,rlMainEndDate,rlMainStartDate;
    AppCompatTextView tvMainSelectCity,tvSelectStartDate,tvSelectEndDate  ;
    AppCompatButton btnMainCreateTrip,btnRowViewTrip;
    SharedPreferenceManager sp;
    final Calendar myCalendar= Calendar.getInstance();
    String StartDate,EndDate;
    TransparentProgressDialog progressDialog;
    List<PlanedTripBean> tripList;
    List<SelectCityBean> cityList;
    private EditText startDate, endDate;
    private Button chooseDateBtn;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        onClick();
    }
    private void init()
    {
        sp=new SharedPreferenceManager(MainActivity.this);
        progressDialog=new TransparentProgressDialog(MainActivity.this,R.drawable.progress);
        tvMainSelectCity=findViewById(R.id.tvMainSelectCity);
        btnRowViewTrip=findViewById(R.id.btnRowViewTrip);
        rlMainSelectCity=findViewById(R.id.rlMainSelectCity);
        rlMainEndDate=findViewById(R.id.rlMainEndDate);
        rlMainStartDate=findViewById(R.id.rlMainStartDate);
        tvSelectStartDate=findViewById(R.id.tvSelectStartDate);
        tvSelectEndDate=findViewById(R.id.tvSelectEndDate);
        btnMainCreateTrip=findViewById(R.id.btnMainCreateTrip);
        tripList = new ArrayList<>();
        cityList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        PlannedTripList();
    }
    private void onClick()
    {
        btnRowViewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,TripActivity.class);
                startActivity(intent);
            }
        });

        rlMainSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityDialog();
            }
        });
        rlMainStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateSelectionDialog();
            }
        });
        rlMainEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateSelectionDialog();
            }
        });
        tvSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DateSelectionDialog();
            }
        });
        btnMainCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CreateTripActivity.class);
                startActivity(intent);
            }
        });
        tvSelectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
              DateSelectionDialog();
            }
        });
    }

    public void CityDialog()
    {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialogLayout = inflater.inflate(R.layout.dialog_select_city, null);
        dialogCity = new AlertDialog.Builder(MainActivity.this).create();
        dialogCity.setCancelable(false);
        dialogCity.setView(dialogLayout);
        dialogCity.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
        dialogCity.show();

        ivDialogCityClose=dialogCity.findViewById(R.id.ivDialogCityClose);
        etDialogCitySearch=dialogCity.findViewById(R.id.etDialogCitySearch);
        rvDialogCity=dialogCity.findViewById(R.id.rvDialogCity);
        ivDialogCityClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCity.dismiss();
            }
        });
        SelectCityAPI();

    }

    private void DateSelectionDialog()
    {

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialogLayout = inflater.inflate(R.layout.dialog_date_selection, null);
        dialogSelectDate = new AlertDialog.Builder(MainActivity.this).create();
        dialogSelectDate.setCancelable(false);
        dialogSelectDate.setView(dialogLayout);
        dialogSelectDate.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
        dialogSelectDate.show();

        // Initialize UI elements
        startDate = dialogSelectDate.findViewById(R.id.startDate);
        endDate = dialogSelectDate.findViewById(R.id.endDate);
        chooseDateBtn = dialogSelectDate.findViewById(R.id.chooseDateBtn);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d", Locale.getDefault());
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        startDate.setText(currentDate);
        endDate.setText(currentDate);
        // Set listeners for date selection
        startDate.setOnClickListener(v -> showDatePickerDialog(startDate));
        endDate.setOnClickListener(v -> showDatePickerDialog(endDate));

        // Button Click Listener
        chooseDateBtn.setOnClickListener(v -> {
            String selectedStartDate = startDate.getText().toString();
            String selectedEndDate = endDate.getText().toString();

            if (!selectedStartDate.isEmpty() && !selectedEndDate.isEmpty()) {
                // Do something with selected dates (e.g., pass data to another screen)
                dialogSelectDate.dismiss();
                tvSelectStartDate.setText(selectedStartDate);
                tvSelectEndDate.setText(selectedEndDate);
            }
        });
    }


    private void showDatePickerDialog(EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    editText.setText(dateFormat.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void PlannedTripList()
    {
        ConnectionDetector connectionDetector=ConnectionDetector.getInstance(MainActivity.this);
        if(connectionDetector.isConnectionAvailable()) {

            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.TRIP,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            tripList.clear();
                            //Log.d("response_data", "" + response);
                            if (response.equals("")) {
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONArray json = obj.getJSONArray("data");
                                    Log.d("response_data", "" + response);
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject jsonObjectCity = json.getJSONObject(i);
                                        String id = jsonObjectCity.getString("id").toString();
                                        String citys = jsonObjectCity.getString("city").toString();
                                        String date = jsonObjectCity.getString("date").toString();
                                        String img = jsonObjectCity.getString("img").toString();
                                        String title = jsonObjectCity.getString("title").toString();
                                        String duration = jsonObjectCity.getString("duration").toString();
                                        PlanedTripBean city = new PlanedTripBean();
                                        city.setId(id);
                                        city.setcity(citys);
                                        city.setdate(date);
                                        city.setImg(img);
                                        city.setTitle(title);
                                        city.setDuration(duration);
                                        tripList.add(city);
                                        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                        TripAdapter adapter = new TripAdapter(MainActivity.this, tripList);
                                        mRecyclerView.setAdapter(adapter);
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
            MyVolley.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        else
        {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this,"Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    private void SelectCityAPI() {
        ConnectionDetector connectionDetector = ConnectionDetector.getInstance(MainActivity.this);
        if (connectionDetector.isConnectionAvailable()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.CITY,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            cityList.clear();
                            //Log.d("response_data", "" + response);
                            if (response.equals("")) {
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONArray json = obj.getJSONArray("data");
                                    Log.d("response_data", "" + response);
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject jsonObjectCity = json.getJSONObject(i);
                                        String citys = jsonObjectCity.getString("city").toString();
                                        String country = jsonObjectCity.getString("country").toString();

                                        SelectCityBean city = new SelectCityBean();
                                        city.setCity(citys);
                                        city.setCountry(country);
                                        cityList.add(city);
                                        rvDialogCity.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                        CityAdapter adapter = new CityAdapter(MainActivity.this, cityList, new OnPositionClickListener() {
                                            @Override
                                            public void onPositionClick(int position) {
                                                tvMainSelectCity.setText(cityList.get(position).getCity() + " " + cityList.get(position).getCountry());
                                                dialogCity.dismiss();
                                            }
                                        });
                                        rvDialogCity.setAdapter(adapter);
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
            MyVolley.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } else {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

}