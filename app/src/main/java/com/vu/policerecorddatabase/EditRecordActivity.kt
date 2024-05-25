package com.vu.policerecorddatabase

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class EditRecordActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var recordId: String
    private lateinit var editTextRecordTitle: EditText
    private lateinit var spinnerAccessLevel: Spinner
    private lateinit var spinnerStaffOrCriminal: Spinner
    private lateinit var editTextDescription: EditText
    private lateinit var btnSaveChanges: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_record)

        editTextRecordTitle = findViewById(R.id.editTextRecordTitle)
        spinnerAccessLevel = findViewById(R.id.spinnerAccessLevel)
        spinnerStaffOrCriminal = findViewById(R.id.spinnerStaffOrCriminal)
        editTextDescription = findViewById(R.id.editTextDescription)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)

        database = FirebaseDatabase.getInstance().reference
        recordId = intent.getStringExtra("recordId") ?: ""

        // Retrieve record details from Firebase using recordId
        database.child("records").child(recordId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val record = snapshot.getValue(Record::class.java)
                if (record != null) {
                    // Populate UI fields with record details
                    editTextRecordTitle.setText(record.recordTitle)
                    editTextDescription.setText(record.description)
                    // Set selection for spinners based on record details
                    record.accessLevel?.let { setSpinnerSelection(spinnerAccessLevel, it) }
                    record.staffOrCriminal?.let { setSpinnerSelection(spinnerStaffOrCriminal, it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
            }
        })

        btnSaveChanges.setOnClickListener {
            // Update the record in Firebase with the new details
            val newRecord = Record(
                recordId, // Pass the existing recordId
                editTextRecordTitle.text.toString(),
                spinnerAccessLevel.selectedItem.toString(),
                spinnerStaffOrCriminal.selectedItem.toString(),
                editTextDescription.text.toString()
            )
            database.child("records").child(recordId).setValue(newRecord)
                .addOnSuccessListener {
                    // Record updated successfully
                    Toast.makeText(this, "Record updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    // Failed to update record
                    Toast.makeText(this, "Failed to update record", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Function to set spinner selection based on record details
    private fun setSpinnerSelection(spinner: Spinner, value: String) {
        val adapter = spinner.adapter
        if (adapter is ArrayAdapter<*>) {
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i).toString() == value) {
                    spinner.setSelection(i)
                    return
                }
            }
        }
    }

}
