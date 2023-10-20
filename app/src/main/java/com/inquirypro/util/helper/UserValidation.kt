package com.inquirypro.util.helper

import android.util.Patterns
import com.inquirypro.exception.UserValidationException


class UserValidation {
    companion object {
        const val INVALID_FIRST_NAME = "Name must start with an uppercase letter, followed by lowercase letters."
        const val INVALID_LAST_NAME = "Surname must start with an uppercase letter, followed by lowercase letters."
        const val INVALID_EMAIL = "Invalid email format. Please provide a valid email address."
        const val INVALID_PASSWORD = "Password must be at least 8 characters long, contain at least 1 uppercase letter, and at least 2 digits."

        private const val USER_FIRSTNAME_REGEX = "[A-Z][a-z]+"
        private const val USER_LASTNAME_REGEX = "[A-Z][a-z]+"
        private val emailPattern = Patterns.EMAIL_ADDRESS
        private const val PASSWORD_REGEX = "^(?=.*[0-9].*[0-9])(?=.*[A-Z]).{8,}\$"

        @Throws(UserValidationException::class)
        fun validateUserInput(firstName: String, lastName: String, email: String, password: String) {
            validateFirstName(firstName)
            validateLastName(lastName)
            validateEmail(email)
            validatePassword(password)
        }

        private fun validateFirstName(firstName: String) {
            if (!firstName.matches(USER_FIRSTNAME_REGEX.toRegex())) {
                throw IllegalArgumentException(INVALID_FIRST_NAME)
            }
        }

        private fun validateLastName(lastName: String) {
            if (!lastName.matches(USER_LASTNAME_REGEX.toRegex())) {
                throw IllegalArgumentException(INVALID_LAST_NAME)
            }
        }

        private fun validateEmail(email: String) {
            if (!emailPattern.matcher(email).matches()) {
                throw IllegalArgumentException(INVALID_EMAIL)
            }
        }

        private fun validatePassword(password: String) {
            if (!password.matches(PASSWORD_REGEX.toRegex())) {
                throw IllegalArgumentException(INVALID_PASSWORD)
            }
        }
    }
}