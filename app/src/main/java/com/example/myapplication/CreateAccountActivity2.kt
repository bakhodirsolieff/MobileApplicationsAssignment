package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateAccountActivity2 : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account_screen2)
        val fullNameEditText = findViewById<EditText>(R.id.fullname)
        val validEmailEditText = findViewById<EditText>(R.id.validEmail)
        val phoneNumberEditText = findViewById<EditText>(R.id.phoneNumber)
        val strongPasswordEditText = findViewById<EditText>(R.id.strongPassword)
        val termsAndConditionsCheckBox = findViewById<CheckBox>(R.id.Agreement)
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
                Toast.makeText(this, "Please accept the Terms and Conditions", Toast.LENGTH_SHORT).show()
            }
        }



    }
}