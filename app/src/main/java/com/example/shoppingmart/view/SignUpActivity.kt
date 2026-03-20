package com.example.shoppingmart.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingmart.ui.theme.ShoppingMartTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingMartTheme {
                SignUpScreen(
                    onSignUpClick = {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    },
                    onLoginClick = {
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun SignUpScreen(onSignUpClick: () -> Unit, onLoginClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val pink = Color(0xFFE91E63)

    Column(
        modifier = Modifier.Companion.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Text(
            "Create Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Companion.Bold,
            color = pink
        )
        Spacer(modifier = Modifier.Companion.height(32.dp))

        OutlinedTextField(
            value = username, onValueChange = { username = it },
            label = { Text("Username") }, modifier = Modifier.Companion.fillMaxWidth()
        )
        Spacer(modifier = Modifier.Companion.height(12.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") }, modifier = Modifier.Companion.fillMaxWidth()
        )
        Spacer(modifier = Modifier.Companion.height(12.dp))

        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.Companion.fillMaxWidth()
        )
        Spacer(modifier = Modifier.Companion.height(12.dp))

        OutlinedTextField(
            value = confirmPassword, onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.Companion.fillMaxWidth()
        )
        Spacer(modifier = Modifier.Companion.height(20.dp))

        Button(
            onClick = onSignUpClick,
            modifier = Modifier.Companion.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = pink)
        ) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.Companion.height(16.dp))

        TextButton(onClick = onLoginClick) {
            Text("Already have an account? Log In", color = pink)
        }
    }
}