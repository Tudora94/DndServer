package com.tudorEnterprises.dndapp.refreshWorkers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tudorEnterprises.dndapp.services.CampaignRefreshService

class CampaignRefreshWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val refreshService = CampaignRefreshService(applicationContext)
        return try {
            refreshService.fetchFromServerAndUpdatedDb()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}