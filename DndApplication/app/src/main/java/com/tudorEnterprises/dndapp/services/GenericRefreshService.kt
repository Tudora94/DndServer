package com.tudorEnterprises.dndapp.services

import android.content.Context
import com.tudorEnterprises.dndapp.dataStorage.CampaignCharacterSqlActivity
import com.tudorEnterprises.dndapp.networking.CampaignHttp

class GenericRefreshService(val context: Context) {

    suspend fun fetchAndUpdateCampaignCharacters(dbCalls: CampaignCharacterSqlActivity, campaignId: Int) {
        val httpCalls = CampaignHttp(context)
        val characterList = httpCalls.getCampaignCharacters(campaignId = campaignId)

        for(character in characterList) {

            dbCalls.insertCharacterData(campaignId, character.charachterName, character.id)
        }
    }
}