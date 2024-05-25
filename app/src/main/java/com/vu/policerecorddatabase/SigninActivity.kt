package com.vu.policerecorddatabase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SigninActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var adminButton: ImageButton
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)
        signUpButton = findViewById(R.id.signUpButton)
        adminButton = findViewById(R.id.adminbutton)
        signInButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Sign in user with email and password
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser

                        val intent = Intent(this, UserPanel::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this, "Sign in successful as ${user?.email}", Toast.LENGTH_SHORT).show()
                        // Optionally, you can navigate to another activity here
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        signUpButton.setOnClickListener {
            // Navigate to SignUpActivity
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
    }
        adminButton.setOnClickListener {
            // Navigate to SignUpActivity
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
            finish() // Optionally, you can finish this activity to prevent going back to it when pressing back button from SignUpActivity
        }

    }
}
