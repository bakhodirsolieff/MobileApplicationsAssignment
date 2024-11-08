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
                    fullNameEditText.error = "Full Name is required"
                }

                !credentialsManager.isValidEmail(email) -> {
                    validEmailEditText.error = "Invalid email format"
                }

                !credentialsManager.isValidPhoneNumber(phone) -> {
                    phoneNumberEditText.error = "Phone number must be at least 10 digits"
                }

                !credentialsManager.isValidPassword(password) -> {
                    strongPasswordEditText.error = "Password must be at least 8 characters"
                }

                !credentialsManager.isTermsAccepted(isTermsAccepted) -> {
                    Toast.makeText(
                        this,
                        "Please accept the Terms and Conditions",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    if (credentialsManager.ValidateCredentialsForSignUp(
                            fullName,
                            email,
                            phone,
                            password,
                            isTermsAccepted
                        )
                    ) {
                        Toast.makeText(
                            this,
                            "You have successfully registered!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Registration failed due to invalid inputs",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        val loginButton = findViewById<View>(R.id.tvRegisterNow)
        loginButton.setOnClickListener {
            val goToReg = Intent(this, CreateAccountActivity::class.java)
            startActivity(goToReg)
        }
    }
}