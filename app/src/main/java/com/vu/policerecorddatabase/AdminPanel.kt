package com.vu.policerecorddatabase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.Toast

class AdminPanel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        // Initialize image buttons
        val enterRecordButton: ImageButton = findViewById(R.id.enterRecordButton)
        val generateRecordButton: ImageButton = findViewById(R.id.generateRecordButton)
        val editDeleteRecordButton: ImageButton = findViewById(R.id.editDeleteRecordButton)

        // Set click listeners for each button
        enterRecordButton.setOnClickListener {
            // Launch EnterRecordActivity
            val intent = Intent(this, RecordInputActivity::class.java)
            startActivity(intent)
        }

        generateRecordButton.setOnClickListener {
            // Launch GenerateRecordActivity
            val intent = Intent(this, RecordSearchActivity::class.java)
            startActivity(intent)
        }

        editDeleteRecordButton.setOnClickListener {
            // Launch EditDeleteRecordActivity
            val intent = Intent(this, EditDeleteRecordActivity::class.java)
            startActivity(intent)
        }
    }
}
