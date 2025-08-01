package com.tudorEnterprises.dndapp.services

import android.content.Context
import com.tudorEnterprises.dndapp.dataStorage.CampaignCharacterSqlActivity
import com.tudorEnterprises.dndapp.networking.CampaignHttp

class GenericRefreshService(val context: Context) {

    suspend fun fetchAndUpdateCampaignCharacters(dbCalls: CampaignCharacterSqlActivity, campaignId: Int) {
        val httpCalls = CampaignHttp(context)
        val characterList = httpCalls.getCampaignCharacters(campaignId = campaignId)

        for(character in characterList) {

            //check if the character already exists in the database with same update time

//            if(!dbCalls.validateCharacterExists(campaignId, character.id, character.updateTime)){
                dbCalls.insertCharacterData(campaignId, character.charachterName, character.id, character.updateTime)
//            }
        }
    }
}