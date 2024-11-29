package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CreateAccountActivity : AppCompatActivity() {
    private val credentialsManager = CredentialsManager

    private val emailField: TextInputEditText
        get() = findViewById(R.id.etEmail)

    private val emailLayout: TextInputLayout
        get() = findViewById(R.id.textInputEmail)

    private val passwordField: TextInputEditText
        get() = findViewById(R.id.etPassword)

    private val passwordLayout: TextInputLayout
        get() = findViewById(R.id.textInputPassword)

    private val nextButton: Button
        get() = findViewById(R.id.btnNext)

    private val rememberMeCheckbox: CheckBox
        get() = findViewById(R.id.cbRememberMe)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        nextButton.setOnClickListener { onNextButtonClick() }

        findViewById<TextView>(R.id.tvRegisterNoww).setOnClickListener {
            startActivity(Intent(this, SignUpDetailsActivity::class.java))
        }
    }

    private fun onNextButtonClick() {
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        val isRememberMeChecked = rememberMeCheckbox.isChecked

        clearAllErrors()

        when {
            email.isEmpty() -> setError(emailLayout, R.string.error_email_required)
            !credentialsManager.isEmailValid(email) -> setError(
                emailLayout,
                R.string.error_invalid_email
            )

            password.isEmpty() -> setError(passwordLayout, R.string.error_password_required)
            !credentialsManager.isValidPassword(password) -> setError(
                passwordLayout,
                R.string.error_password_invalid
            )

            !isRememberMeChecked -> showToast(R.string.error_remember_me_required)
            credentialsManager.isHardcodedCredentials(email, password) -> navigateToMainActivity()
            credentialsManager.validateCredentials(
                email,
                password
            ) -> showToast(R.string.success_signed_in)

            else -> showToast(R.string.error_invalid_credentials)
        }
    }

    private fun setError(layout: TextInputLayout, errorResId: Int) {
        layout.error = getString(errorResId)
    }

    private fun clearAllErrors() {
        setError(emailLayout, null)
        setError(passwordLayout, null)
    }

    private fun setError(layout: TextInputLayout, errorResId: Int?) {
        layout.error = errorResId?.let { getString(it) }
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        showToast(R.string.success_signed_in)
        startActivity(Intent(this, MainActivity::class.java))
    }
}