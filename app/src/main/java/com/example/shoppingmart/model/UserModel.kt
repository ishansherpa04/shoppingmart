package com.example.shoppingmart.model

data class UserModel(
    val uid: String,
    val email: String,
    val displayName: String? = null
)

sealed class LoginState {
    object Idle    : LoginState()
    object Loading : LoginState()
    data class Success(val user: UserModel) : LoginState()
    data class Error(val message: String)   : LoginState()
}
