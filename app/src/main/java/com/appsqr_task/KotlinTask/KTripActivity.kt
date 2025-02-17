package com.appsqr_task

import android.app.ProgressDialog
import android.app.SharedElementCallback
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.appsqr_task.Adapter.ActivityAdapter
import com.appsqr_task.Adapter.FlightAdapter
import com.appsqr_task.Adapter.HotelAdapter
import com.appsqr_task.Constant.EndPoints
import com.appsqr_task.Helper.ConnectionDetector
import com.appsqr_task.Helper.MyVolley
import com.appsqr_task.Helper.TransparentProgressDialog
import com.appsqr_task.KotlinTask.KAdapter.KActivityAdapter
import com.appsqr_task.KotlinTask.KAdapter.KFlightAdapter
import com.appsqr_task.KotlinTask.KAdapter.KHotelAdapter
import com.appsqr_task.KotlinTask.KPojo.KHotelBean
import com.appsqr_task.pojo.HotelBean
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class KTripActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvLocation: TextView
    private lateinit var ivRowtripImage: ImageView
    private lateinit var progressDialog: TransparentProgressDialog
    private lateinit var rlShare: RelativeLayout
    private lateinit var rlCollaboration: RelativeLayout
    private lateinit var rvFlight: RecyclerView
    private lateinit var rvHotelList: RecyclerView
    private lateinit var rvActivityList: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mLayoutManagerActivity: LinearLayoutManager
    private lateinit var mLayoutManagerFlight: LinearLayoutManager
    private var hotelList = ArrayList<KHotelBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)
        supportActionBar?.apply {
            title = "Plan a Trip"
            setDisplayHomeAsUpEnabled(true)
        }
        init()
        onClick()
    }

    private fun init() {
        progressDialog = TransparentProgressDialog(this, R.drawable.progress)
        rvFlight = findViewById(R.id.rvFlight)
        rvHotelList = findViewById(R.id.rvHotelList)
        rvActivityList = findViewById(R.id.rvActivityList)
        tvTitle = findViewById(R.id.tvTitle)
        tvDate = findViewById(R.id.tvDate)
        tvLocation = findViewById(R.id.tvLocation)
        ivRowtripImage = findViewById(R.id.ivImg)
        rlShare = findViewById(R.id.rlShare)
        rlCollaboration = findViewById(R.id.rlCollaboration)

        tvTitle.text = intent.getStringExtra("title")
        tvDate.text = "${intent.getStringExtra("date")} ${intent.getStringExtra("duration")}"
        tvLocation.text = intent.getStringExtra("city")

        Glide.with(this)
            .load(intent.getStringExtra("img"))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(ivRowtripImage)

        rvHotelList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@KTripActivity, LinearLayoutManager.VERTICAL, false)
        }

        rvActivityList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@KTripActivity, LinearLayoutManager.VERTICAL, false)
        }

        rvFlight.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@KTripActivity, LinearLayoutManager.VERTICAL, false)
        }

        HotelList()
    }

    private fun onClick() {
        rlShare.setOnClickListener { Share() }
    }

    private fun HotelList() {
        val connectionDetector = ConnectionDetector.getInstance(this)
        if (connectionDetector.isConnectionAvailable()) {
            progressDialog.show()
            val stringRequest = object : StringRequest(Request.Method.GET, EndPoints.TRIP_DETAILS1,
                Response.Listener { response ->
                    progressDialog.dismiss()
                    hotelList.clear()
                    if (response.isNotEmpty()) {
                        try {
                            val obj = JSONObject(response)
                            val json = obj.getJSONArray("data")
                            for (i in 0 until json.length()) {
                                val jsonObjectCity = json.getJSONObject(i)
                                val hotelModel = KHotelBean().apply {
                                    title = jsonObjectCity.getString("title")
                                    date = jsonObjectCity.getString("date")
                                    img = jsonObjectCity.getString("img")
                                    flightTitle = jsonObjectCity.getString("flighttitle")
                                    tm1 = jsonObjectCity.getString("tm1")
                                    tm2 = jsonObjectCity.getString("tm2")
                                    hotel = jsonObjectCity.getString("hotel")
                                    hotelAddress = jsonObjectCity.getString("hotel_address")
                                    inDate = jsonObjectCity.getString("indate")
                                    outTime = jsonObjectCity.getString("outtime")
                                    hotelFees = jsonObjectCity.getString("hotel_fees")
                                    activity = jsonObjectCity.getString("activity")
                                    activityAddress = jsonObjectCity.getString("activty_address")
                                    time = jsonObjectCity.getString("time")
                                    activityPrice = jsonObjectCity.getString("actvity_price")
                                }
                                hotelList.add(hotelModel)
                            }
                            rvHotelList.adapter = KHotelAdapter(this@KTripActivity, hotelList)
                            rvActivityList.adapter = KActivityAdapter(this@KTripActivity, hotelList)
                            rvFlight.adapter = KFlightAdapter(this@KTripActivity, hotelList)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                },
                Response.ErrorListener {
                    progressDialog.dismiss()
                }) {
                override fun getParams(): Map<String, String> = HashMap()
            }

            MyVolley.getInstance(this).addToRequestQueue(stringRequest)
            stringRequest.retryPolicy = DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        } else {
            progressDialog.dismiss()
            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun Share() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            val title = intent.getStringExtra("title")
            val date = intent.getStringExtra("date")
            val duration = intent.getStringExtra("duration")
            val location = intent.getStringExtra("city")
            val imageUrl = intent.getStringExtra("img")

            val message = "Trip Details:\n" +
                    "Title: $title\n" +
                    "Date: $date $duration\n" +
                    "Location: $location"

            putExtra(Intent.EXTRA_TEXT, message)

            if (!imageUrl.isNullOrEmpty()) {
                val imageUri = Uri.parse(imageUrl)
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, imageUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            setPackage("com.whatsapp")
        }

        try {
            startActivity(shareIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun setEnterSharedElementCallback(callback: SharedElementCallback?) {
        super.setEnterSharedElementCallback(callback)
    }
}
