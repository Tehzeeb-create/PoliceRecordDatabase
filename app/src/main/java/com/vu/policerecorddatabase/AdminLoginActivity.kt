package com.vu.policerecorddatabase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        usernameEditText = findViewById(R.id.adminnameEditText)
        passwordEditText = findViewById(R.id.adminpasswordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username == "Admin" && password == "Zainab") {
                // Correct username and password, login to AdminLoginActivity
                val intent = Intent(this, AdminPanel::class.java)
                startActivity(intent)
                finish() // Close LoginActivity
            } else {
                // Incorrect username or password, show error message
                Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
