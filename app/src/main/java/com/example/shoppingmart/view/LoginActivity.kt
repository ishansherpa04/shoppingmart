package com.example.shoppingmart.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingmart.view.SignUpActivity
import com.example.shoppingmart.model.LoginState
import com.example.shoppingmart.ui.theme.ShoppingMartTheme
import com.example.shoppingmart.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingMartTheme {
                val loginState by loginViewModel.loginState.observeAsState(LoginState.Idle)

                LaunchedEffect(loginState) {
                    if (loginState is LoginState.Success) {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                        loginViewModel.resetState()
                    }
                }

                LoginScreen(
                    loginState = loginState,
                    onLoginClick = { email, password ->
                        loginViewModel.login(email, password)
                    },
                    onSignUpClick = {
                        startActivity(Intent(this, SignUpActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    loginState: LoginState,
    onLoginClick: (email: String, password: String) -> Unit,
    onSignUpClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val pink = Color(0xFFE91E63)
    val isLoading = loginState is LoginState.Loading
    val errorMessage = (loginState as? LoginState.Error)?.message

    Column(
        modifier = Modifier.Companion.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Text("ShoppingMart", fontSize = 28.sp, fontWeight = FontWeight.Companion.Bold, color = pink)
        Spacer(Modifier.Companion.height(32.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") }, modifier = Modifier.Companion.fillMaxWidth(),
            singleLine = true, enabled = !isLoading
        )
        Spacer(Modifier.Companion.height(12.dp))

        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.Companion.fillMaxWidth(), singleLine = true, enabled = !isLoading
        )
        Spacer(Modifier.Companion.height(8.dp))

        if (errorMessage != null) {
            Text(
                errorMessage, color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.Companion.fillMaxWidth().padding(vertical = 4.dp)
            )
        }
        Spacer(Modifier.Companion.height(12.dp))

        Button(
            onClick = { onLoginClick(email, password) },
            modifier = Modifier.Companion.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = pink),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Companion.White,
                    strokeWidth = 2.dp, modifier = Modifier.Companion.size(20.dp)
                )
            } else {
                Text("Log In")
            }
        }
        Spacer(Modifier.Companion.height(16.dp))

        TextButton(onClick = onSignUpClick, enabled = !isLoading) {
            Text("Don't have an account? Sign Up", color = pink)
        }
    }
}