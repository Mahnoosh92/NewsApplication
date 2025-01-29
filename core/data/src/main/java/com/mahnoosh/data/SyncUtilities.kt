package com.mahnoosh.data

import android.util.Log
import kotlin.coroutines.cancellation.CancellationException

interface Synchronizer {
    suspend fun Syncable.sync() = this@sync.syncWith(this@Synchronizer)
}

interface Syncable {
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}

private suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Log.i(
        "suspendRunCatching",
        "Failed to evaluate a suspendRunCatchingBlock. Returning failure Result",
        exception,
    )
    Result.failure(exception)
}

suspend fun <T> Synchronizer.changeListSync(
    dataFetcher: suspend () -> List<T>,
    modelUpdater: suspend (List<T>) -> Unit,
) = suspendRunCatching {
    // Using the change list, pull down and save the changes (akin to a git pull)
    modelUpdater(dataFetcher())
}.isSuccess