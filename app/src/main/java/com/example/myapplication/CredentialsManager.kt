//package com.example.myapplication
//
//class CredentialsManager {
//
//    private val emailPattern = ("[a-zA-Z0-9\\+\\%\\-\\+]{1,256}" +
//            "\\@" +
//            "[a-zA-Z0-9][0-zA-Z0-9\\-]{0,64}" +
//            "(" +
//            "\\." +
//            "[a-zA-Z0-9][0-zA-Z0-9\\-]{0,25}" +
//            ")+").toRegex()
//
//    fun isEmailValid(email: String): Boolean {
//        return email.matches(emailPattern)
//    }
//
//    fun isValidPassword(password: String): Boolean {
//        return password.length >= 8
//    }
//
//    fun validateCredentials(email: String, password: String, isCheckboxChecked: Boolean): Boolean {
//        return isEmailValid(email) && isValidPassword(password) && isCheckboxChecked
//    }
//
//    fun isValidFullName(fullName: String): Boolean {
//        return fullName.isNotEmpty()
//    }
//
//    fun isHardcodedCredentials(email: String, password: String): Boolean {
//        val hardcodedEmail = "test@te.st"
//        val hardcodedPassword = "1234"
//        return email == hardcodedEmail && password == hardcodedPassword
//    }
//
//    fun isValidPhoneNumber(phoneNumber: String): Boolean {
//        val phonePattern = "^[0-9]{9,}$".toRegex()
//        return phoneNumber.matches(phonePattern)
//    }
//
//    fun isTermsAccepted(isChecked: Boolean): Boolean {
//        return isChecked
//    }
//
//    fun ValidateCredentialsForSignUp(
//        fullName: String,
//        email: String,
//        phoneNumber: String,
//        password: String,
//        isTermsAccepted: Boolean
//    ): Boolean {
//        return isValidFullName(fullName) &&
//                isEmailValid(email) &&
//                isValidPhoneNumber(phoneNumber) &&
//                isValidPassword(password) &&
//                isTermsAccepted(isTermsAccepted)
//    }
//


package com.example.myapplication

class CredentialsManager {

    private val emailPattern = ("[a-zA-Z0-9\\+\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+").toRegex()

    private val registeredAccounts = mutableMapOf<String, String>() // Stores emails and their passwords

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

    fun isHardcodedCredentials(email: String, password: String): Boolean {
        val hardcodedEmail = "test@te.st"
        val hardcodedPassword = "1234"
        return email == hardcodedEmail && password == hardcodedPassword
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phonePattern = "^[0-9]{9,}$".toRegex()
        return phoneNumber.matches(phonePattern)
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

    /**
     * Clears all registered accounts.
     */
    fun clearAllAccounts() {
        registeredAccounts.clear()
    }

    /**
     * Registers a new account.
     */
    fun registerNewAccount(email: String, password: String): Boolean {
        val normalizedEmail = email.lowercase()

        if (isEmailRegistered(normalizedEmail)) {
            return false // Email already registered
        }

        if (!isEmailValid(email) || !isValidPassword(password)) {
            return false // Invalid email or password
        }

        registeredAccounts[normalizedEmail] = password
        return true // Registration successful
    }

    /**
     * Checks if an email is already registered.
     */
    fun isEmailRegistered(email: String): Boolean {
        return registeredAccounts.containsKey(email.lowercase())
    }
}

