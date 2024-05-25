package com.vu.policerecorddatabase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class SignupActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var signInButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signUpButton = findViewById(R.id.signUpButton)
        signInButton = findViewById(R.id.signInButton)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Sign up user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up success, update UI with the signed-in user's information
                        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                        // Optionally, you can add further actions here such as navigating to another activity
                    } else {
                        // If sign up fails, display a message to the user.
                        val errorMessage = when (task.exception) {
                            is FirebaseAuthInvalidCredentialsException -> "Invalid email or password format"
                            is FirebaseAuthUserCollisionException -> "Email is already in use"
                            else -> "Sign up failed. Please try again later."
                        }
                        Toast.makeText(baseContext, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
        }

        signInButton.setOnClickListener {
            // Navigate to SignInActivity
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            finish() // Optionally, you can finish this activity to prevent going back to it when pressing back button from SignInActivity
        }
    }
}
