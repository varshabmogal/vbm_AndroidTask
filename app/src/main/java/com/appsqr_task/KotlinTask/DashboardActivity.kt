package com.appsqr_task.KotlinTask

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.appsqr_task.Adapter.KTripAdapter
import com.appsqr_task.Constant.EndPoints
import com.appsqr_task.Helper.ConnectionDetector
import com.appsqr_task.Helper.MyVolley
import com.appsqr_task.Helper.OnPositionClickListener
import com.appsqr_task.Helper.SharedPreferenceManager
import com.appsqr_task.Helper.TransparentProgressDialog
import com.appsqr_task.KCreateTripActivity
import com.appsqr_task.KTripActivity
import com.appsqr_task.KotlinTask.KAdapter.KCityAdapter
import com.appsqr_task.KotlinTask.KPojo.KPlanedTripBean
import com.appsqr_task.KotlinTask.KPojo.KSelectCityBean
import com.appsqr_task.R
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DashboardActivity : AppCompatActivity() {
    private lateinit var dialogCity: AlertDialog
    private lateinit var dialogSelectDate: AlertDialog
    private lateinit var tvMainSelectCity: AppCompatTextView
    private lateinit var tvSelectStartDate: AppCompatTextView
    private lateinit var tvSelectEndDate: AppCompatTextView
    private lateinit var btnMainCreateTrip: AppCompatButton
    private lateinit var btnRowViewTrip: AppCompatButton
    private lateinit var progressDialog: TransparentProgressDialog
    private lateinit var sp: SharedPreferenceManager
    private val myCalendar = Calendar.getInstance()
    private val tripList = mutableListOf<KPlanedTripBean>()
    private val cityList = mutableListOf<KSelectCityBean>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var rvDialogCity: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setupClickListeners()
    }

    private fun init() {
        sp = SharedPreferenceManager(this)
        progressDialog = TransparentProgressDialog(this, R.drawable.progress)

        tvMainSelectCity = findViewById(R.id.tvMainSelectCity)
        tvSelectStartDate = findViewById(R.id.tvSelectStartDate)
        tvSelectEndDate = findViewById(R.id.tvSelectEndDate)
        btnMainCreateTrip = findViewById(R.id.btnMainCreateTrip)
        btnRowViewTrip = findViewById(R.id.btnRowViewTrip)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DashboardActivity)
        }
        fetchPlannedTrips()
    }

    private fun setupClickListeners() {
        btnRowViewTrip.setOnClickListener {
            startActivity(Intent(this, KTripActivity::class.java))
        }

        findViewById<RelativeLayout>(R.id.rlMainSelectCity).setOnClickListener { showCityDialog() }
        findViewById<RelativeLayout>(R.id.rlMainStartDate).setOnClickListener { showDateSelectionDialog() }
        findViewById<RelativeLayout>(R.id.rlMainEndDate).setOnClickListener { showDateSelectionDialog() }
        tvSelectStartDate.setOnClickListener { showDateSelectionDialog() }
        tvSelectEndDate.setOnClickListener { showDateSelectionDialog() }

        btnMainCreateTrip.setOnClickListener {
            when {
                tvMainSelectCity.text.isBlank() || tvMainSelectCity.text == "Select City" -> showToast("Please select a city")
                tvSelectStartDate.text.isBlank() || tvSelectStartDate.text == "Enter Date" -> showToast("Please select start date")
                tvSelectEndDate.text.isBlank() || tvSelectEndDate.text == "Enter Date" -> showToast("Please select end date")
                else -> startActivity(Intent(this, KCreateTripActivity::class.java))
            }
        }
    }

    private fun showCityDialog() {
        val dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_select_city, null)

        dialogCity = AlertDialog.Builder(this).create().apply {
            setView(dialogLayout)
            setCancelable(false)
            window?.setBackgroundDrawableResource(R.drawable.dialog_shape)
            show()
        }

        rvDialogCity = dialogLayout.findViewById(R.id.rvDialogCity) // Corrected
        dialogLayout.findViewById<ImageView>(R.id.ivDialogCityClose).setOnClickListener { dialogCity.dismiss() }

        fetchCities()
    }

    private fun fetchCities() {
        val connectionDetector = ConnectionDetector.getInstance(this)
        if (connectionDetector.isConnectionAvailable()) {
            progressDialog.show()

            val stringRequest = object : StringRequest(Method.GET, EndPoints.CITY,
                Response.Listener { response ->
                    progressDialog.dismiss()
                    cityList.clear()

                    if (response.isNotEmpty()) {
                        try {
                            val obj = JSONObject(response)
                            val jsonArray = obj.getJSONArray("data")

                            for (i in 0 until jsonArray.length()) {
                                val jsonObjectCity = jsonArray.getJSONObject(i)
                                val cityName = jsonObjectCity.getString("city")
                                val countryName = jsonObjectCity.getString("country")

                                val city = KSelectCityBean().apply {
                                    city = cityName
                                    country = countryName
                                }

                                cityList.add(city)
                            }

                            // Setup RecyclerView Adapter
                            rvDialogCity.layoutManager = LinearLayoutManager(this@DashboardActivity)
                            val adapter = KCityAdapter(this@DashboardActivity, cityList) { position ->
                                tvMainSelectCity.text = "${cityList[position].city}, ${cityList[position].country}"
                                dialogCity.dismiss()
                            }
                            rvDialogCity.adapter = adapter

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                },
                Response.ErrorListener { error ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "Error fetching cities: ${error.message}", Toast.LENGTH_LONG).show()
                }) {

                override fun getParams(): MutableMap<String, String> = HashMap()
            }

            MyVolley.getInstance(this).addToRequestQueue(stringRequest)
            stringRequest.retryPolicy = DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

        } else {
            progressDialog.dismiss()
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showDateSelectionDialog() {
        val dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_date_selection, null)
        dialogSelectDate = AlertDialog.Builder(this).create().apply {
            setView(dialogLayout)
            setCancelable(false)
            window?.setBackgroundDrawableResource(R.drawable.dialog_shape)
            show()
        }

        val startDate = dialogLayout.findViewById<EditText>(R.id.startDate)
        val endDate = dialogLayout.findViewById<EditText>(R.id.endDate)
        val chooseDateBtn = dialogLayout.findViewById<Button>(R.id.chooseDateBtn)
        val dateFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
        val currentDate = dateFormat.format(Calendar.getInstance().time)
        startDate.setText(currentDate)
        endDate.setText(currentDate)

        val datePicker = { editText: EditText ->
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                myCalendar.set(year, month, dayOfMonth)
                editText.setText(dateFormat.format(myCalendar.time))
            }, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]).show()
        }

        startDate.setOnClickListener { datePicker(startDate) }
        endDate.setOnClickListener { datePicker(endDate) }

        chooseDateBtn.setOnClickListener {
            tvSelectStartDate.text = startDate.text
            tvSelectEndDate.text = endDate.text
            dialogSelectDate.dismiss()
        }
    }

    private fun fetchPlannedTrips() {
        if (!ConnectionDetector.getInstance(this).isConnectionAvailable) {
            showToast("Please check your internet connection")
            return
        }
        progressDialog.show()
        val request = StringRequest(Request.Method.GET, EndPoints.TRIP, { response ->
            progressDialog.dismiss()
            tripList.clear()
            try {
                val jsonArray = JSONObject(response).getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    jsonArray.getJSONObject(i).apply {
                        tripList.add(KPlanedTripBean(getString("id"), getString("city"), getString("date"), getString("img"), getString("title"), getString("duration")))
                    }
                }
                recyclerView.adapter = KTripAdapter(this, tripList)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, {
            progressDialog.dismiss()
            Log.e("API_ERROR", it.message ?: "Unknown error")
        })
        MyVolley.getInstance(this).addToRequestQueue(request)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
