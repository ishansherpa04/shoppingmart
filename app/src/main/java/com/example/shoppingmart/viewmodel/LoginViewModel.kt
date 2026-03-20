package com.example.shoppingmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingmart.model.LoginState
import com.example.shoppingmart.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = LoginRepository()

    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    fun login(email: String, password: String) {
        if (email.trim().isBlank()) {
            _loginState.value = LoginState.Error("Please enter your email.")
            return
        }
        if (password.trim().isBlank()) {
            _loginState.value = LoginState.Error("Please enter your password.")
            return
        }
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            val result = repository.login(email.trim(), password.trim())
            _loginState.postValue(result)
        }
    }

    fun resetState() { _loginState.value = LoginState.Idle }
}