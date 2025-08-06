package com.tudorEnterprises.dndapp.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tudorEnterprises.dndapp.dataStorage.CharacterSqlActivity
import com.tudorEnterprises.dndapp.dataStorage.tables.CharacterNameData
import com.tudorEnterprises.dndapp.networking.CharacterHttp
import com.tudorEnterprises.dndapp.services.CharacterRefreshService
import com.tudorEnterprises.dndapp.ui.dialogs.CreateCharacterDialog
import com.tudorEnterprises.dndapp.ui.navigation.GetAppBarTopLoggedIn
import com.tudorEnterprises.dndapp.ui.navigation.GetBottomAppBar
import com.tudorEnterprises.dndapp.ui.navigation.GetCharacterButtons
import com.tudorEnterprises.dndapp.ui.theme.DndApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant

@Composable
fun PlayerLandingScreen(navController: NavController) {
    val context = LocalContext.current
    val sql = CharacterSqlActivity(context)


    DndApplicationTheme {

        var showDialog by remember { mutableStateOf(false) }
        var playerName by remember { mutableStateOf("") }

        val characters by sql.getAllCharacters()
            .collectAsStateWithLifecycle(initialValue = emptyList())

        LaunchedEffect(Unit) {
            while (true) {
                CharacterRefreshService(context, sql).fetchAndUpdateCharacter()
                delay(5000)
            }
        }

        Scaffold(topBar = {
            GetAppBarTopLoggedIn(navController)
        }, bottomBar = {
            GetBottomAppBar()
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
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Player",
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(characters) { characterName ->
                        GetCharacterButtons(
                            characterName,
                            {
                                deleteCharacter(characterName, sql, context)
                            },
                            navController
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CreatePlayerButton { showDialog = true }
                }
            }
        }
        if (showDialog) {
            CreateCharacterDialog(
                onDismiss = { showDialog = false },
                onConfirm = { enteredName ->
                    playerName = enteredName
                    showDialog = false
                    launchCharacterCreation(playerName, context, sql)
                }
            )
        }
    }
}

@Composable
fun CreatePlayerButton(onClick: () -> Unit) {

    ElevatedButton(
        onClick = onClick,
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
                    text = "Create Character",
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}

private fun deleteCharacter(characterNameData: CharacterNameData, sql: CharacterSqlActivity, context: Context) {
    CoroutineScope(Dispatchers.IO).launch {
        if (CharacterHttp(context).deleteCharacter(characterNameData.syncCharacterId))
        {
            sql.deleteCharacterById(characterNameData.syncCharacterId)
            Log.d("DMLandingScreen", "campaign to delete is: ${characterNameData.syncCharacterId}")
        }
    }
}

private fun launchCharacterCreation(
    characterName: String,
    context: Context,
    sql: CharacterSqlActivity
) {
    CoroutineScope(Dispatchers.IO).launch {

        val updateTime = Instant.now().epochSecond

        val syncCharacterId = CharacterHttp(context).newCharacter(
            characterName,
            updateTime
        )

        Log.i("character", "new character ID: $syncCharacterId")

        if (syncCharacterId != null) {
            sql.insertCharacterData(characterName, syncCharacterId, updateTime)
        }
    }
}


@Preview
@Composable
private fun GetScreen() {
    val navController = rememberNavController()
    PlayerLandingScreen(navController)
}