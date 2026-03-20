package com.example.shoppingmart.repository

import com.example.shoppingmart.model.LoginState
import com.example.shoppingmart.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository {

    suspend fun login(email: String, password: String): LoginState =
        withContext(Dispatchers.IO) {
            mockLogin(email, password)
        }

    private suspend fun mockLogin(email: String, password: String): LoginState {
        kotlinx.coroutines.delay(1500)
        return when {
            email.isBlank() || password.isBlank() ->
                LoginState.Error("Email and password must not be empty.")
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                LoginState.Error("Please enter a valid email address.")
            password.length < 6 ->
                LoginState.Error("Password must be at least 6 characters.")
            email == "user@example.com" && password == "password123" ->
                LoginState.Success(UserModel("mock-uid-001", email, "Demo User"))
            else ->
                LoginState.Error("Invalid email or password.")
        }
    }
}