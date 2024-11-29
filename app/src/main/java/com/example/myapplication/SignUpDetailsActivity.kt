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

class SignUpDetailsActivity : AppCompatActivity() {

    private val credentialsManager = CredentialsManager

    private val fullNameField: TextInputEditText
        get() = findViewById(R.id.fullName)

    private val fullNameLayout: TextInputLayout
        get() = findViewById(R.id.textInputFullName)

    private val emailField: TextInputEditText
        get() = findViewById(R.id.validEmail)

    private val emailLayout: TextInputLayout
        get() = findViewById(R.id.textInputValidEmail)

    private val phoneField: TextInputEditText
        get() = findViewById(R.id.phoneNumber)

    private val phoneLayout: TextInputLayout
        get() = findViewById(R.id.textInputPhoneNumber)

    private val passwordField: TextInputEditText
        get() = findViewById(R.id.strongPassword)

    private val passwordLayout: TextInputLayout
        get() = findViewById(R.id.textInputStrongPassword)

    private val termsCheckBox: CheckBox
        get() = findViewById(R.id.agreementCheck)

    private val nextButton: Button
        get() = findViewById(R.id.btnNext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        nextButton.setOnClickListener { onNextButtonClick() }

        findViewById<TextView>(R.id.tvRegisterNow).setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
    }

    private fun onNextButtonClick() {
        val fullName = fullNameField.text.toString().trim()
        val email = emailField.text.toString().trim()
        val phone = phoneField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        val isTermsAccepted = termsCheckBox.isChecked

        clearAllErrors()

        when {
            !credentialsManager.isValidFullName(fullName) -> setError(
                fullNameLayout,
                R.string.error_name_required
            )

            !credentialsManager.isEmailValid(email) -> setError(
                emailLayout,
                R.string.error_invalid_email
            )

            !credentialsManager.isValidPhoneNumber(phone) -> setError(
                phoneLayout,
                R.string.error_phone_number_required
            )

            !credentialsManager.isValidPassword(password) -> setError(
                passwordLayout,
                R.string.error_password_invalid
            )

            !isTermsAccepted -> showToast(R.string.error_terms_and_conditions_required)
            credentialsManager.isEmailAlreadyUsed(email) -> setError(
                emailLayout,
                R.string.error_email_already_used
            )

            else -> registerUserAndProceed(email, password)
        }
    }

    private fun registerUserAndProceed(email: String, password: String) {
        credentialsManager.registerUser(email, password)
        showToast(R.string.success_registration)
        startActivity(Intent(this, CreateAccountActivity::class.java))
        finish()
    }

    private fun setError(layout: TextInputLayout, errorResId: Int?) {
        layout.error = errorResId?.let { getString(it) }
    }

    private fun clearAllErrors() {
        setError(fullNameLayout, null)
        setError(emailLayout, null)
        setError(phoneLayout, null)
        setError(passwordLayout, null)
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
    }
}