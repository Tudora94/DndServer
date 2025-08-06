package com.tudorEnterprises.dndapp.networking

import android.content.Context
import android.util.Log
import com.tudorEnterprises.dndapp.dataModels.requests.AddPlayerToCampaignRequest
import com.tudorEnterprises.dndapp.dataModels.requests.CreateCharacterRequest
import com.tudorEnterprises.dndapp.dataModels.responses.AddPlayerToCampaignResponse
import com.tudorEnterprises.dndapp.dataModels.responses.GetCharacterResponse
import com.tudorEnterprises.dndapp.objects.RetroFitHttpCharacterClient
import com.tudorEnterprises.dndapp.objects.SecureStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CharacterHttp(val context: Context) {
    private val characterService = RetroFitHttpCharacterClient.create(context)

    suspend fun newCharacter(characterName: String, updateTime: Long): Int? {
        val userId = SecureStorage.getUserId(context).toInt()
        val response = withContext(Dispatchers.IO) {
            characterService.createCharacter(
                CreateCharacterRequest(
                    userId,
                    characterName,
                    updateTime
                )
            )
        }

        return if (response.isSuccessful) {
            if (response.body()?.success == true) {
                response.body()?.playerId
            } else {
                null
            }
        } else {
            null
        }
    }

    suspend fun deleteCharacter(characterId: Int): Boolean {
        //TODO pass in updateTime, to ensure item is updated correctly
        Log.d(this::class.java.simpleName, "character sent for deletion $characterId")
        val response = withContext(Dispatchers.IO) {
            characterService.deleteCharacterById(
                SecureStorage.getUserId(context).toInt(),
                characterId
            )
        }
        return response.body()?.success == true
    }

    suspend fun getCharacters(): GetCharacterResponse? {
        var response: Response<GetCharacterResponse> = Response.success(
            GetCharacterResponse(
                success = false,
                players = emptyList()
            )
        )
        try {
            response = withContext(Dispatchers.IO) {
                characterService.getCharactersForUser(SecureStorage.getUserId(context).toInt())
            }

        } catch (ex: Exception) {
            Log.d(this::class.java.simpleName, "getCharacters failed with exception: $ex")
        }
        return response.body()
    }

    suspend fun addCharacterToCampaign(updateTime: Long, characterId: Int, roomCode: String) : AddPlayerToCampaignResponse? {
        val request = AddPlayerToCampaignRequest(userId = SecureStorage.getUserId(context).toInt(), updateTime = updateTime, characterId = characterId, roomCode = roomCode)

        val response = withContext(Dispatchers.IO) {
            characterService.addCharacterToCampaign(request)
        }
        if (response.isSuccessful && response.body() != null){
            return response.body()
        }
        return AddPlayerToCampaignResponse()
    }
}