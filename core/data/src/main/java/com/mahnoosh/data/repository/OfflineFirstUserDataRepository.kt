package com.mahnoosh.data.repository

import com.mahnoosh.data_store.NewsPreferencesDataSource
import com.mahnoosh.model.DarkThemeConfig
import com.mahnoosh.model.ThemeBrand
import com.mahnoosh.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class OfflineFirstUserDataRepository @Inject constructor(val newsPreferencesDataSource: NewsPreferencesDataSource):UserDataRepository {
    override val userData: Flow<UserData>
        get() = newsPreferencesDataSource.userData

    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) {
        newsPreferencesDataSource.setFollowedTopicIds(followedTopicIds)
    }

    override suspend fun setTopicIdFollowed(followedTopicId: String, followed: Boolean) {
        newsPreferencesDataSource.setTopicIdFollowed(followedTopicId, followed)
    }

    override suspend fun setNewsResourceBookmarked(newsResourceId: String, bookmarked: Boolean) {
        newsPreferencesDataSource.setNewsResourceBookmarked(newsResourceId, bookmarked)
    }

    override suspend fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean) {
       newsPreferencesDataSource.setNewsResourcesViewed(listOf(newsResourceId), viewed)
    }

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        newsPreferencesDataSource.setThemeBrand(themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        newsPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        newsPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        newsPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
    }
}