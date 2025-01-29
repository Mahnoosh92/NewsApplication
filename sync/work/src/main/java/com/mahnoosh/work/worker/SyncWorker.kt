package com.mahnoosh.work.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.mahnoosh.data.Synchronizer
import com.mahnoosh.data.UserRepositoryOne
import com.mahnoosh.data.UserRepositoryTwo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val userRepositoryOne: UserRepositoryOne,
    val userRepositoryTwo: UserRepositoryTwo
) : CoroutineWorker(appContext, workerParams), Synchronizer {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val syncedSuccessfully = awaitAll(
            async { userRepositoryOne.sync() },
            async { userRepositoryTwo.sync() }
        ).all { it }
        if (syncedSuccessfully)
            Result.success()
        else
            Result.retry()

    }
    companion object {
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(SyncWorker::class.delegatedData())
            .build()
    }
}