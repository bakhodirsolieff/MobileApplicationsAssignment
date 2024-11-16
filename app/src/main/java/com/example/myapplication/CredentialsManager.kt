package com.example.myapplication

class CredentialsManager {

    private val emailPattern = ("[a-zA-Z0-9\\+\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][0-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][0-zA-Z0-9\\-]{0,25}" +
            ")+").toRegex()

    fun isEmailValid(email: String): Boolean {
        return email.matches(emailPattern)
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun validateCredentials(email: String, password: String, isCheckboxChecked: Boolean): Boolean {
        return isEmailValid(email) && isValidPassword(password) && isCheckboxChecked
    }

    fun isValidFullName(fullName: String): Boolean {
        return fullName.isNotEmpty()
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.isNotEmpty() && phoneNumber.length >= 9
    }

    fun isTermsAccepted(isChecked: Boolean): Boolean {
        return isChecked
    }

    fun ValidateCredentialsForSignUp(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
        isTermsAccepted: Boolean
    ): Boolean {
        return isValidFullName(fullName) &&
                isEmailValid(email) &&
                isValidPhoneNumber(phoneNumber) &&
                isValidPassword(password) &&
                isTermsAccepted(isTermsAccepted)
    }
}
