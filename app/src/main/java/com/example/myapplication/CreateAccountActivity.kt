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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val emailField = findViewById<EditText>(R.id.etEmail)
        val passwordField = findViewById<EditText>(R.id.etPassword)
        val nextButton = findViewById<Button>(R.id.btnNext)
        val rememberMeCheckbox = findViewById<CheckBox>(R.id.cbRememberMe)

        nextButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty()) {
                emailField.error = "Email is required"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordField.error = "Password is required"
                return@setOnClickListener
            }

            if (password.length < 6) {
                passwordField.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            val rememberMe = rememberMeCheckbox.isChecked

            Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show()
        }
        val loginButton = findViewById<View>(R.id.tvRegisterNoww)
        loginButton .setOnClickListener{
            val goToReg = Intent(this, SignUpDetailsActivity::class.java)
            startActivity(goToReg)
        }
    }

}