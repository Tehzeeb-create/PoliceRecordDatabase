package com.vu.policerecorddatabase

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class RecordInputActivity : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance().reference

    private lateinit var editTextRecordTitle: EditText
    private lateinit var spinnerAccessLevel: Spinner
    private lateinit var spinnerstaffOrCriminal: Spinner
    private lateinit var editdescription: EditText
    private lateinit var btnSaveRecord: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_input)

        editTextRecordTitle = findViewById(R.id.editTextRecordTitle)
        spinnerAccessLevel = findViewById(R.id.spinnerAccessLevel)
        spinnerstaffOrCriminal = findViewById(R.id.spinnerstaffOrCriminal)
        editdescription = findViewById(R.id.editdescription)

        btnSaveRecord = findViewById(R.id.btnSaveRecord)

        val accessLevels = arrayOf("District level", "Tehsil level", "Police station level")
        val accessLevelAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, accessLevels)
        accessLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAccessLevel.adapter = accessLevelAdapter

        // Populating dropdown for staff or criminal
        val staffOrCriminal = arrayOf("Staff record", "Criminal record")
        val staffOrCriminalAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, staffOrCriminal)
        staffOrCriminalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerstaffOrCriminal.adapter = staffOrCriminalAdapter

        // Button click listener to save the record
        btnSaveRecord.setOnClickListener {
            val recordId: String? = null // Replace with the actual logic to get the goalId
            val recordTitle = editTextRecordTitle.text.toString()
            val accessLevel = spinnerAccessLevel.selectedItem.toString()
            val staffOrCriminal = spinnerstaffOrCriminal.selectedItem.toString()
            val description = editdescription.text.toString()

            val record = Record(recordId, recordTitle, accessLevel, staffOrCriminal, description)

            // Push the record data to Firebase Realtime Database
            val newRecordRef = database.child("records").push()
            newRecordRef.setValue(record)
                .addOnSuccessListener {
                    // Clear the EditText fields after successful record submission
                    editTextRecordTitle.text.clear()
                    editdescription.text.clear()

                    // Show a toast message indicating success
                    Toast.makeText(this, "Record saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Handle the failure to save record if needed
                    Toast.makeText(this, "Failed to save record", Toast.LENGTH_SHORT).show()
                }
        }

    }
}

