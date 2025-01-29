
package com.mahnoosh.database.di


import com.mahnoosh.database.NewsDatabase
import com.mahnoosh.database.dao.NewsResourceDao
import com.mahnoosh.database.dao.NewsResourceFtsDao
import com.mahnoosh.database.dao.RecentSearchQueryDao
import com.mahnoosh.database.dao.TopicDao
import com.mahnoosh.database.dao.TopicFtsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesTopicsDao(
        database: NewsDatabase,
    ): TopicDao = database.topicDao()

    @Provides
    fun providesNewsResourceDao(
        database: NewsDatabase,
    ): NewsResourceDao = database.newsResourceDao()

    @Provides
    fun providesTopicFtsDao(
        database: NewsDatabase,
    ): TopicFtsDao = database.topicFtsDao()

    @Provides
    fun providesNewsResourceFtsDao(
        database: NewsDatabase,
    ): NewsResourceFtsDao = database.newsResourceFtsDao()

    @Provides
    fun providesRecentSearchQueryDao(
        database: NewsDatabase,
    ): RecentSearchQueryDao = database.recentSearchQueryDao()
}
