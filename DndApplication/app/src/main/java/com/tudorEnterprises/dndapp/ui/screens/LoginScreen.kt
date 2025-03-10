package com.tudorEnterprises.dndapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tudorEnterprises.dndapp.dataModels.requests.LoginRequest
import com.tudorEnterprises.dndapp.objects.RetroFitHttpClient
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTop
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.navigation.GetCreateUserButton
import com.tudorEnterprises.dndapp.ui.navigation.GetLoginButton
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onCreateUserClick: () -> Unit) {

    MainLoginWindow(onCreateUserClick = onCreateUserClick)
}

@Composable
private fun MainLoginWindow(debugVersion: String? = null, onCreateUserClick: () -> Unit) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    fun loginRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetroFitHttpClient.api.login(LoginRequest(username, password))

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("LoginScreen", "Token: ${body?.token}")
                } else {
                    Log.e("LoginScreen", "Login Failed: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("LoginScreen", "Error: ${e.message}")
            }
        }

        Log.d("LoginScreen", "username: $username, password: $password")
    }

    DndApplicationTheme {
        Scaffold(topBar = {
            GetAppBarTop()
        }, bottomBar = {
            GetBottomAppBar(debugVersion)
        }) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Login Page",
                )
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Spacer(modifier = Modifier.height(18.dp))
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp)
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp)


                    )
                    Spacer(modifier = Modifier.height(18.dp))

                    GetLoginButton {
                        loginRequest()
                    }
                    Spacer(modifier = Modifier.height(18.dp))

                }
                GetCreateUserButton { onCreateUserClick() }
            }

        }
    }
}

@Preview
@Composable
private fun LoginPreview(){
    MainLoginWindow("TestVersion") {}
}
