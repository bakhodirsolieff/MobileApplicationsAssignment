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
    private val credentialsManager: CredentialsManager = CredentialsManager()

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
            val rememberMe = rememberMeCheckbox.isChecked

            when {
                email.isEmpty() -> setError(emailField, R.string.error_email_required)
                !credentialsManager.isEmailValid(email) -> setError(
                    emailField,
                    R.string.error_invalid_email
                )

                password.isEmpty() -> setError(passwordField, R.string.error_password_required)
                !credentialsManager.isValidPassword(password) -> setError(
                    passwordField,
                    R.string.error_password_invalid
                )

                !rememberMe -> showToast(R.string.error_remember_me_required)
                else -> showToast(R.string.success_signed_in)
            }
        }

        val loginButton = findViewById<View>(R.id.tvRegisterNoww)
        loginButton.setOnClickListener {
            val goToReg = Intent(this, SignUpDetailsActivity::class.java)
            startActivity(goToReg)
        }
    }

    private fun setError(field: EditText, errorResId: Int) {
        field.error = getString(errorResId)
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
    }

}