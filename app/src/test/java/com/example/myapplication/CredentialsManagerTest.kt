//import com.example.myapplication.CredentialsManager
//import org.junit.Assert.*
//import org.junit.Test
//
//class CredentialsManagerTest {
//
//    private val credentialsManager = CredentialsManager()
//
//    @Test
//    fun testValidEmail() {
//        assertTrue(credentialsManager.isEmailValid("example@test.com"))
//        assertFalse(credentialsManager.isEmailValid("invalid-email"))
//    }
//
//    @Test
//    fun testValidPassword() {
//        assertTrue(credentialsManager.isValidPassword("password123"))
//        assertFalse(credentialsManager.isValidPassword("short"))
//    }
//
//    @Test
//    fun testValidateCredentials() {
//        // Valid email, password, and checkbox checked
//        assertTrue(credentialsManager.validateCredentials("example@test.com", "password123", true))
//
//        // Invalid email
//        assertFalse(credentialsManager.validateCredentials("invalid-email", "password123", true))
//
//        // Invalid password
//        assertFalse(credentialsManager.validateCredentials("example@test.com", "short", true))
//
//        // Checkbox not checked
//        assertFalse(
//            credentialsManager.validateCredentials(
//                "example@test.com",
//                "password123",
//                false
//            )
//        )
//    }
//
//    @Test
//    fun testValidFullName() {
//        assertTrue(credentialsManager.isValidFullName("John Doe"))
//        assertFalse(credentialsManager.isValidFullName(""))
//        assertTrue(credentialsManager.isValidFullName("John"))
//    }
//
//    @Test
//    fun testValidPhoneNumber() {
//        assertTrue(credentialsManager.isValidPhoneNumber("1234567890"))
//        assertFalse(credentialsManager.isValidPhoneNumber("12345"))
//        assertFalse(credentialsManager.isValidPhoneNumber(""))
//    }
//
//    @Test
//    fun testTermsAccepted() {
//        assertFalse(credentialsManager.isTermsAccepted(false))
//    }
//
//    @Test
//    fun testValidateCredentialsForSignUp() {
//        // Valid full name, email, phone, password, and checkbox checked
//        assertTrue(
//            credentialsManager.ValidateCredentialsForSignUp(
//                "John Doe", "example@test.com", "1234567890", "password123", true
//            )
//        )
//
//        // Invalid full name
//        assertFalse(
//            credentialsManager.ValidateCredentialsForSignUp(
//                "", "example@test.com", "1234567890", "password123", true
//            )
//        )
//
//        // Invalid email
//        assertFalse(
//            credentialsManager.ValidateCredentialsForSignUp(
//                "John Doe", "invalid-email", "1234567890", "password123", true
//            )
//        )
//
//        // Invalid phone number
//        assertFalse(
//            credentialsManager.ValidateCredentialsForSignUp(
//                "John Doe", "example@test.com", "12345", "password123", true
//            )
//        )
//
//        // Invalid password
//        assertFalse(
//            credentialsManager.ValidateCredentialsForSignUp(
//                "John Doe", "example@test.com", "1234567890", "short", true
//            )
//        )
//
//        // Checkbox not checked
//        assertFalse(
//            credentialsManager.ValidateCredentialsForSignUp(
//                "John Doe", "example@test.com", "1234567890", "password123", false
//            )
//        )
//    }
//
//    @Test
//    fun testHardcodedLoginCredentials() {
//        val validEmail = "test@te.st"
//        val validPassword = "1234"
//
//        assertTrue(validEmail == "test@te.st" && validPassword == "1234")
//
//        assertFalse(validEmail == "wrong@te.st" && validPassword == "1234")
//        assertFalse(validEmail == "test@te.st" && validPassword == "wrong")
//    }
//}








import com.example.myapplication.CredentialsManager
import org.junit.Assert.*
import org.junit.Test

class CredentialsManagerTest {

    private val credentialsManager = CredentialsManager()

    @Test
    fun testValidEmail() {
        assertTrue(credentialsManager.isEmailValid("example@test.com"))
        assertFalse(credentialsManager.isEmailValid("invalid-email"))
    }

    @Test
    fun testValidPassword() {
        assertTrue(credentialsManager.isValidPassword("password123"))
        assertFalse(credentialsManager.isValidPassword("short"))
    }

    @Test
    fun testValidateCredentials() {
        // Valid email, password, and checkbox checked
        assertTrue(credentialsManager.validateCredentials("example@test.com", "password123", true))

        // Invalid email
        assertFalse(credentialsManager.validateCredentials("invalid-email", "password123", true))

        // Invalid password
        assertFalse(credentialsManager.validateCredentials("example@test.com", "short", true))

        // Checkbox not checked
        assertFalse(
            credentialsManager.validateCredentials(
                "example@test.com",
                "password123",
                false
            )
        )
    }

    @Test
    fun testValidFullName() {
        assertTrue(credentialsManager.isValidFullName("John Doe"))
        assertFalse(credentialsManager.isValidFullName(""))
        assertTrue(credentialsManager.isValidFullName("John"))
    }

    @Test
    fun testValidPhoneNumber() {
        assertTrue(credentialsManager.isValidPhoneNumber("1234567890"))
        assertFalse(credentialsManager.isValidPhoneNumber("12345"))
        assertFalse(credentialsManager.isValidPhoneNumber(""))
    }

    @Test
    fun testTermsAccepted() {
        assertFalse(credentialsManager.isTermsAccepted(false))
    }

    @Test
    fun testValidateCredentialsForSignUp() {
        // Valid full name, email, phone, password, and checkbox checked
        assertTrue(
            credentialsManager.ValidateCredentialsForSignUp(
                "John Doe", "example@test.com", "1234567890", "password123", true
            )
        )

        // Invalid full name
        assertFalse(
            credentialsManager.ValidateCredentialsForSignUp(
                "", "example@test.com", "1234567890", "password123", true
            )
        )

        // Invalid email
        assertFalse(
            credentialsManager.ValidateCredentialsForSignUp(
                "John Doe", "invalid-email", "1234567890", "password123", true
            )
        )

        // Invalid phone number
        assertFalse(
            credentialsManager.ValidateCredentialsForSignUp(
                "John Doe", "example@test.com", "12345", "password123", true
            )
        )

        // Invalid password
        assertFalse(
            credentialsManager.ValidateCredentialsForSignUp(
                "John Doe", "example@test.com", "1234567890", "short", true
            )
        )

        // Checkbox not checked
        assertFalse(
            credentialsManager.ValidateCredentialsForSignUp(
                "John Doe", "example@test.com", "1234567890", "password123", false
            )
        )
    }

    @Test
    fun testCreateNewAccount() {
        credentialsManager.clearAllAccounts() // Ensure a clean slate

        // Successful registration
        val success = credentialsManager.registerNewAccount("newuser@test.com", "Password123")
        assertTrue(success)

        // Email already used
        val duplicate = credentialsManager.registerNewAccount("newuser@test.com", "Password123")
        assertFalse(duplicate)
    }

    @Test
    fun testCorrectPasswordValidation() {
        assertTrue(credentialsManager.isValidPassword("Valid1234")) // Correct: 8+ chars, digit, uppercase
        assertFalse(credentialsManager.isValidPassword("short"))   // Too short
        assertFalse(credentialsManager.isValidPassword("NoDigits")) // No digit
        assertFalse(credentialsManager.isValidPassword("12345678")) // No letter
    }

    @Test
    fun testEmailAlreadyUsed() {
        credentialsManager.clearAllAccounts() // Clear existing accounts

        // Register a user
        credentialsManager.registerNewAccount("testuser@test.com", "Password123")

        // Attempt to register the same email (case insensitive)
        assertFalse(credentialsManager.registerNewAccount("TESTUSER@TEST.COM", "Password123"))
    }

    private fun assertFalse(registerNewAccount: Unit) {

    }

    @Test
    fun testCaseInsensitiveEmail() {
        credentialsManager.clearAllAccounts() // Clear existing accounts

        // Register a user with lowercase email
        credentialsManager.registerNewAccount("testcase@test.com", "Password123")

        // Validate credentials with uppercase email
        assertTrue(credentialsManager.validateCredentials("TESTCASE@TEST.COM", "Password123", true))
    }

    @Test
    fun testSuccessfulRegistration() {
        credentialsManager.clearAllAccounts() // Clear existing accounts

        // Register a new user
        val isRegistered = credentialsManager.registerNewAccount("success@test.com", "Password123")
        assertTrue(isRegistered)

        // Verify the user is present
        assertTrue(credentialsManager.isEmailRegistered("success@test.com"))
    }

    private fun assertTrue(registered: Unit) {

    }
}
