package com.mahnoosh.work.status

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.mahnoosh.utils.SyncManager
import com.mahnoosh.work.initializers.SYNC_WORK_NAME
import com.mahnoosh.work.worker.SyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map

class WorkManagerSyncManager @Inject constructor(@ApplicationContext val context: Context) :
    SyncManager {
    override val isSyncing: Flow<Boolean>
        get() = WorkManager
            .getInstance(context)
            .getWorkInfosForUniqueWorkFlow(SYNC_WORK_NAME)
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    override fun requestSync() {
        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            SyncWorker.startUpSyncWork(),
        )
    }
}

private fun List<WorkInfo>.anyRunning() = any { it.state == WorkInfo.State.RUNNING }
