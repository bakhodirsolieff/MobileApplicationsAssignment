package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var credentialsManager: CredentialsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        credentialsManager = CredentialsManager()

        val emailField = findViewById<EditText>(R.id.etEmail)
        val passwordField = findViewById<EditText>(R.id.etPassword)
        val nextButton = findViewById<Button>(R.id.btnNext)
        val rememberMeCheckbox = findViewById<CheckBox>(R.id.cbRememberMe)

        nextButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val rememberMe = rememberMeCheckbox.isChecked

            when {
                email.isEmpty() -> emailField.error = "Email is required"
                !credentialsManager.isValidEmail(email) -> emailField.error = "Invalid email format"
                password.isEmpty() -> passwordField.error = "Password is required"
                !credentialsManager.isValidPassword(password) -> passwordField.error =
                    "Password must be at least 8 characters"

                !rememberMe -> Toast.makeText(
                    this,
                    "Please check the 'Remember me' box",
                    Toast.LENGTH_SHORT
                ).show()

                else -> {
                    Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val loginButton = findViewById<View>(R.id.tvRegisterNoww)
        loginButton.setOnClickListener {
            val goToReg = Intent(this, SignUpDetailsActivity::class.java)
            startActivity(goToReg)
        }
    }
}