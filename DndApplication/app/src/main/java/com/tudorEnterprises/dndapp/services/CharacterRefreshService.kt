package com.tudorEnterprises.dndapp.services

import android.content.Context
import com.tudorEnterprises.dndapp.dataStorage.CharacterSqlActivity
import com.tudorEnterprises.dndapp.networking.CharacterHttp
import kotlinx.coroutines.flow.first

class CharacterRefreshService(val context: Context, val dbCalls: CharacterSqlActivity) {
    private val httpCalls = CharacterHttp(context)

    suspend fun fetchAndUpdateCharacter() {
        val characterList = httpCalls.getCharacters()
        if (characterList?.success == true) {
            val localCharacters = dbCalls.getAllCharacters()
            val serverIds = characterList.players.map() { it.id}.toSet()

           val charactersToDelete =
               localCharacters.first().filter { it.syncCharacterId !in serverIds }
            if(charactersToDelete.isNotEmpty()) {
                for(character in charactersToDelete) {
                    dbCalls.deleteCharacterById(character.syncCharacterId)
                }
            }

            for(character in characterList.players) {
                val localCharacter = dbCalls.getCharacterById(characterId = character.id)
                var localCharacterUpdateTime: Long = 0
                if(localCharacter != null) {
                    localCharacterUpdateTime = localCharacter.updateTime
                }
                if(character.updateTime > localCharacterUpdateTime) {
                    dbCalls.upsertCharacter(character)
                }
            }
        }
    }
}