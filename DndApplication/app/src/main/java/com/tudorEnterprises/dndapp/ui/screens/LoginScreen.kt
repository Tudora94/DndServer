package com.tudorEnterprises.dndapp.ui.screens

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.dataModels.requests.LoginRequest
import com.tudorEnterprises.dndapp.objects.RetroFitHttpClient
import com.tudorEnterprises.dndapp.ui.Dialogs.LoadingDialog
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTop
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.navigation.GetCreateUserButton
import com.tudorEnterprises.dndapp.ui.navigation.GetLoginButton
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(onCreateUserClick: () -> Unit, navController: NavController) {

    MainLoginWindow(onCreateUserClick = onCreateUserClick, navController = navController)
}

@Composable
private fun MainLoginWindow(debugVersion: String? = null, onCreateUserClick: () -> Unit, navController: NavController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    fun loginRequest(navController: NavController, showDialog: (Boolean, String?) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            showDialog(true, "Logging in...") // Show spinner

            try {
                val response = withContext(Dispatchers.IO) {
                    RetroFitHttpClient.api.login(LoginRequest(username, password))
                }

                val message = if (response.isSuccessful) {
                    val body = response.body()
                    "$body? ?: Login Successful"
                } else {
                    "Login Failed: ${response.code()} - ${response.errorBody()?.string()}"
                }

                showDialog(false, message) // Stop spinner, show message

//                if (response.isSuccessful) {
//       //TODO do something here eventually to nav to new page
//                }
            } catch (e: Exception) {
                showDialog(false, "Error: ${e.message}")
            }
        }
    }

    fun showLoginDialog(loading: Boolean, message: String?) {
        showDialog = true
        isLoading = loading
        if (message != null) {
            dialogMessage = message
        }
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

                    if (showDialog) {
                        LoadingDialog(isLoading, dialogMessage) { showDialog = false }
                    }

                    GetLoginButton {
                        loginRequest(navController, ::showLoginDialog)
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
    val navController = rememberNavController()
    MainLoginWindow("TestVersion", {}, navController)
}
