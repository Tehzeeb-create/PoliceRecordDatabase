package com.vu.policerecorddatabase

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class EditDeleteRecordActivity : AppCompatActivity() {
    private lateinit var spinnerAccessLevel: Spinner
    private lateinit var spinnerStaffOrCriminal: Spinner
    private lateinit var btnGenerateResult: Button
    private lateinit var scrollableLayout: LinearLayout

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_delete)

        database = FirebaseDatabase.getInstance().reference

        spinnerAccessLevel = findViewById(R.id.spinnerAccessLevel)
        spinnerStaffOrCriminal = findViewById(R.id.spinnerStaffOrCriminal)
        btnGenerateResult = findViewById(R.id.btnGenerateResult)
        scrollableLayout = findViewById(R.id.scrollableLayout)

        // Populate access level spinner
        val accessLevels = arrayOf("District level", "Tehsil level", "Police station level")
        val accessLevelAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, accessLevels)
        accessLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAccessLevel.adapter = accessLevelAdapter

        // Populate staff or criminal spinner
        val staffOrCriminal = arrayOf("Staff record", "Criminal record")
        val staffOrCriminalAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, staffOrCriminal)
        staffOrCriminalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStaffOrCriminal.adapter = staffOrCriminalAdapter

        btnGenerateResult.setOnClickListener {
            val accessLevel = spinnerAccessLevel.selectedItem.toString()
            val staffOrCriminal = spinnerStaffOrCriminal.selectedItem.toString()

            queryDatabase(accessLevel, staffOrCriminal)
        }
    }

    private fun queryDatabase(accessLevel: String, staffOrCriminal: String) {
        database.child("records").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing layouts
                scrollableLayout.removeAllViews()

                for (recordSnapshot in snapshot.children) {
                    val record = recordSnapshot.getValue(Record::class.java)
                    if (record != null && record.accessLevel == accessLevel && record.staffOrCriminal == staffOrCriminal) {
                        addDeleteRecordLayout(record, recordSnapshot)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
            }
        })
    }

    private fun addDeleteRecordLayout(record: Record, recordSnapshot: DataSnapshot) {
        val recorddeletelayout = layoutInflater.inflate(R.layout.record_delete_layout, null)
        val titleTextView = recorddeletelayout.findViewById<TextView>(R.id.titleTextView)
        val accessLevelTextView = recorddeletelayout.findViewById<TextView>(R.id.accessLevelTextView)
        val staffOrCriminalTextView = recorddeletelayout.findViewById<TextView>(R.id.staffOrCriminalTextView)
        val descriptionTextView = recorddeletelayout.findViewById<TextView>(R.id.descriptionTextView)
        val deleteButton = recorddeletelayout.findViewById<Button>(R.id.deleteButton)
        val editButton = recorddeletelayout.findViewById<Button>(R.id.editButton)

        titleTextView.text = record.recordTitle
        accessLevelTextView.text = record.accessLevel
        staffOrCriminalTextView.text = record.staffOrCriminal
        descriptionTextView.text = record.description

        deleteButton.setOnClickListener {
            // Delete the record from Firebase
            recordSnapshot.ref.removeValue()
            // Remove the record layout from the UI
            scrollableLayout.removeView(recorddeletelayout)
        }

        editButton.setOnClickListener {
            val intent = Intent(this@EditDeleteRecordActivity, EditRecordActivity::class.java)
            intent.putExtra("recordId", recordSnapshot.key) // Pass the record ID to the EditRecordActivity
            startActivity(intent)
        }


        scrollableLayout.addView(recorddeletelayout)
    }


}
