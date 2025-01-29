package com.mahnoosh.data_store

import androidx.datastore.core.DataMigration
import com.mahnoosh.newsapplication.core.datastore.UserPreferences
import com.mahnoosh.newsapplication.core.datastore.copy

object IntToStringIdsMigration : DataMigration<UserPreferences> {
    override suspend fun cleanUp() = Unit

    override suspend fun shouldMigrate(currentData: UserPreferences) =
        !currentData.hasDoneIntToStringIdMigration

    override suspend fun migrate(currentData: UserPreferences) =
        currentData.copy {
            // Migrate topic ids
            deprecatedFollowedTopicIds.clear()
            deprecatedFollowedTopicIds.addAll(
                currentData.deprecatedIntFollowedTopicIdsList.map(Int::toString),
            )
            deprecatedIntFollowedTopicIds.clear()

            // Migrate author ids
            deprecatedFollowedAuthorIds.clear()
            deprecatedFollowedAuthorIds.addAll(
                currentData.deprecatedIntFollowedAuthorIdsList.map(Int::toString),
            )
            deprecatedIntFollowedAuthorIds.clear()

            // Mark migration as complete
            hasDoneIntToStringIdMigration = true
        }
}