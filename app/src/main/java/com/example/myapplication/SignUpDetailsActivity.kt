package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpDetailsActivity : AppCompatActivity() {

    private lateinit var credentialsManager: CredentialsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        credentialsManager = CredentialsManager()
        val fullNameEditText = findViewById<EditText>(R.id.fullName)
        val validEmailEditText = findViewById<EditText>(R.id.validEmail)
        val phoneNumberEditText = findViewById<EditText>(R.id.phoneNumber)
        val strongPasswordEditText = findViewById<EditText>(R.id.strongPassword)
        val termsAndConditionsCheckBox = findViewById<CheckBox>(R.id.agreementCheck)
        val nextButton = findViewById<Button>(R.id.btnNext)

        nextButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString().trim()
            val email = validEmailEditText.text.toString().trim()
            val phone = phoneNumberEditText.text.toString().trim()
            val password = strongPasswordEditText.text.toString().trim()
            val isTermsAccepted = termsAndConditionsCheckBox.isChecked

            when {
                !credentialsManager.isValidFullName(fullName) -> {
                    showError(fullNameEditText, getString(R.string.error_name_required))
                }

                !credentialsManager.isEmailValid(email) -> {
                    showError(validEmailEditText, getString(R.string.error_invalid_email))
                }

                !credentialsManager.isValidPhoneNumber(phone) -> {
                    showError(phoneNumberEditText, getString(R.string.error_phone_number_required))
                }

                !credentialsManager.isValidPassword(password) -> {
                    showError(strongPasswordEditText, getString(R.string.error_password_invalid))
                }

                !credentialsManager.isTermsAccepted(isTermsAccepted) -> {
                    showToast(getString(R.string.error_terms_and_conditions_required))
                }

                else -> {
                    validateAndProceed(fullName, email, phone, password, isTermsAccepted)
                }
            }
        }
        val loginButton = findViewById<View>(R.id.tvRegisterNow)
        loginButton.setOnClickListener {
            val goToReg = Intent(this, CreateAccountActivity::class.java)
            startActivity(goToReg)
        }
    }

    private fun validateAndProceed(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        isTermsAccepted: Boolean
    ) {
        val message = if (
            credentialsManager.ValidateCredentialsForSignUp(
                fullName,
                email,
                phone,
                password,
                isTermsAccepted
            )
        ) {
            getString(R.string.success_signed_in)
        } else {
            getString(R.string.error_email_required)
        }
        showToast(message)
    }

    private fun showError(editText: EditText, message: String) {
        editText.error = message
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}