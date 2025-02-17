package com.appsqr_task

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.PopupMenu

class KCreateTripActivity : AppCompatActivity() {

    private lateinit var dialogFeedback: android.app.AlertDialog
    private lateinit var btnCrtTripNext: AppCompatButton
    private lateinit var etCrtTripName: AppCompatEditText
    private lateinit var etCrtTripTravelStyle: AppCompatEditText
    private lateinit var etCrtTripDesc: AppCompatEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_trip)
        supportActionBar?.title = "Plan a Trip"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
        onclick()
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menu.add("Solo")
        popupMenu.menu.add("Couple")
        popupMenu.menu.add("Family")
        popupMenu.menu.add("Group")
        popupMenu.setOnMenuItemClickListener { menuItem ->
            etCrtTripTravelStyle.setText(menuItem.title)
            true
        }
        popupMenu.show()
    }

    private fun init() {
        btnCrtTripNext = findViewById(R.id.btnCrtTripNext)
        etCrtTripName = findViewById(R.id.etCrtTripName)
        etCrtTripTravelStyle = findViewById(R.id.etCrtTripTravelStyle)
        etCrtTripDesc = findViewById(R.id.etCrtTripDesc)
    }

    private fun onclick() {
        btnCrtTripNext.setOnClickListener {
            finish()
        }
        etCrtTripTravelStyle.setOnClickListener {
            showPopupMenu(it)
        }
        btnCrtTripNext.setOnClickListener {
            if (isValid()) {
                showConfirmationDialog()
            }
        }
    }

    private fun isValid(): Boolean {
        val strTripName = etCrtTripName.text.toString()
        val strTripStyle = etCrtTripTravelStyle.text.toString()
        val strTripDesc = etCrtTripDesc.text.toString()

        if (TextUtils.isEmpty(strTripName)) {
            etCrtTripName.error = "Please enter trip name"
            etCrtTripName.requestFocus()
            return false
        }
        if (TextUtils.isEmpty(strTripStyle)) {
            Toast.makeText(this, "Please select trip style", Toast.LENGTH_LONG).show()
            return false
        }
        if (TextUtils.isEmpty(strTripDesc)) {
            etCrtTripDesc.error = "Please enter trip description"
            etCrtTripDesc.requestFocus()
            return false
        }
        return true
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Trip Creation")
            .setMessage("Are you sure you want to create the trip?")
            .setPositiveButton("Yes") { _, _ ->
                Toast.makeText(this, "Trip Created!", Toast.LENGTH_SHORT).show()
                DialogFeedback()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun DialogFeedback() {
        val inflater = LayoutInflater.from(this)
        val dialogLayout = inflater.inflate(R.layout.dialog_feedback, null)
        dialogFeedback = android.app.AlertDialog.Builder(this).create()
        dialogFeedback.setCancelable(false)
        dialogFeedback.setView(dialogLayout)
        dialogFeedback.window?.setBackgroundDrawableResource(R.drawable.dialog_shape)
        dialogFeedback.show()

        val ivDialogFeedbackClose: ImageView = dialogFeedback.findViewById(R.id.ivDialogFeedbackClose)
        val etFeedbackName: AppCompatEditText = dialogFeedback.findViewById(R.id.etFeedbackName)
        val etFeedbackMobile: AppCompatEditText = dialogFeedback.findViewById(R.id.etFeedbackMobile)
        val etFeedbackDesc: AppCompatEditText = dialogFeedback.findViewById(R.id.etFeedbackDesc)
        val btnFeedbackSubmit: AppCompatButton = dialogFeedback.findViewById(R.id.btnFeedbackSubmit)

        ivDialogFeedbackClose.setOnClickListener {
            dialogFeedback.dismiss()
        }

        btnFeedbackSubmit.setOnClickListener {
            val name = etFeedbackName.text.toString().trim()
            val mobile = etFeedbackMobile.text.toString().trim()
            val feedback = etFeedbackDesc.text.toString().trim()

            if (name.isEmpty()) {
                etFeedbackName.error = "Please enter your name"
                etFeedbackName.requestFocus()
                return@setOnClickListener
            }

            if (mobile.isEmpty()) {
                etFeedbackMobile.error = "Please enter your mobile number"
                etFeedbackMobile.requestFocus()
                return@setOnClickListener
            } else if (mobile.length != 10) {
                etFeedbackMobile.error = "Mobile number must be 10 digits"
                etFeedbackMobile.requestFocus()
                return@setOnClickListener
            } else if (mobile.startsWith("0") || mobile.startsWith("1") || mobile.startsWith("2") ||
                mobile.startsWith("3") || mobile.startsWith("4") || mobile.startsWith("5")
            ) {
                etFeedbackMobile.error = "Please enter valid mobile number"
                etFeedbackMobile.requestFocus()
                return@setOnClickListener
            }

            if (feedback.isEmpty()) {
                etFeedbackDesc.error = "Please enter your feedback"
                etFeedbackDesc.requestFocus()
                return@setOnClickListener
            }
            dialogFeedback.dismiss()
            finish()
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
}
