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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        val fullNameEditText = findViewById<EditText>(R.id.fullName)
        val validEmailEditText = findViewById<EditText>(R.id.validEmail)
        val phoneNumberEditText = findViewById<EditText>(R.id.phoneNumber)
        val strongPasswordEditText = findViewById<EditText>(R.id.strongPassword)
        val termsAndConditionsCheckBox = findViewById<CheckBox>(R.id.agreementCheck)
        val nextButton = findViewById<Button>(R.id.btnNext)

        nextButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val validEmail = validEmailEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val strongPassword = strongPasswordEditText.text.toString()
            val isTermsAccepted = termsAndConditionsCheckBox.isChecked

            if (isTermsAccepted) {
                Toast.makeText(this, "You have successfully registered!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please accept the Terms and Conditions", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val loginButton = findViewById<View>(R.id.tvRegisterNow)
        loginButton .setOnClickListener{
            val goToReg = Intent(this, CreateAccountActivity::class.java)
            startActivity(goToReg)
        }

    }
}