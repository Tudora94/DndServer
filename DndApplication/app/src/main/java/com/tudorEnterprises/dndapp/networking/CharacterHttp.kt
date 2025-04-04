package com.tudorEnterprises.dndapp.networking

import android.content.Context
import com.tudorEnterprises.dndapp.dataModels.requests.CreateCharacterRequest
import com.tudorEnterprises.dndapp.objects.RetroFitHttpCharacterClient
import com.tudorEnterprises.dndapp.objects.SecureStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterHttp(val context: Context) {
    private val characterService = RetroFitHttpCharacterClient.create(context)

    suspend fun newCharacter(characterName: String, updateTime: Long) : Int? {
        val userId = SecureStorage.getUserId(context).toInt()
        val response = withContext(Dispatchers.IO) {
            characterService.createCharacter(CreateCharacterRequest(userId, characterName, updateTime))
        }

        return if (response.isSuccessful) {
            if(response.body()?.success == true) {
                response.body()?.playerId
            } else {
                null
            }
        } else {
            null
        }
    }
}