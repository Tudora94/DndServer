package com.tudorEnterprises.dndapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.dataModels.requests.CreateUserRequest
import com.tudorEnterprises.dndapp.objects.RetroFitHttpClient
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTop
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreateUserScreen(debugVersion: String? = null, navController: NavController) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    fun registerUserRequest() {

        if(password != confirmPassword) {
            Log.d("CreateUserScreen", "Passwords do not match")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetroFitHttpClient.api.register(CreateUserRequest(email, username, password, confirmPassword))

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("CreateUserScreen", "body: ${body?.message}")
                } else {
                    Log.e("CreateUserScreen", "Register Failed: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("CreateUserScreen", "Error: ${e.message}")
            }
        }
    }

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create a New User",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.height(18.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp)
                )
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
                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp)
                )
                Spacer(modifier = Modifier.height(18.dp))
                ElevatedButton(
                    onClick = {
                        registerUserRequest()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp, start = 30.dp, end = 30.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(25),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row {
                            Text(
                                text = "Create User",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = { navController.popBackStack() }, // Go back to previous screen
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Back")
            }
        }
    }
}

@Preview
@Composable
private fun ViewCreateUserScreen() {
    val navController = rememberNavController()
    CreateUserScreen("TestVersion", navController)
}