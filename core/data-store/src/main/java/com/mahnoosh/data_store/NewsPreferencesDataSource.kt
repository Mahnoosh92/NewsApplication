package com.mahnoosh.data_store


import android.util.Log
import androidx.datastore.core.DataStore
import com.mahnoosh.model.DarkThemeConfig
import com.mahnoosh.model.ThemeBrand
import com.mahnoosh.model.UserData
import com.mahnoosh.newsapplication.core.datastore.DarkThemeConfigProto
import com.mahnoosh.newsapplication.core.datastore.ThemeBrandProto
import com.mahnoosh.newsapplication.core.datastore.UserPreferences
import com.mahnoosh.newsapplication.core.datastore.UserPreferencesKt
import com.mahnoosh.newsapplication.core.datastore.copy
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map
import java.io.IOException

class NewsPreferencesDataSource @Inject constructor(private val userPreferences: DataStore<UserPreferences>) {
    val userData = userPreferences.data
        .map {
            UserData(
                bookmarkedNewsResources = it.bookmarkedNewsResourceIdsMap.keys,
                viewedNewsResources = it.viewedNewsResourceIdsMap.keys,
                followedTopics = it.followedTopicIdsMap.keys,
                themeBrand = when (it.themeBrand) {
                    null,
                    ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                    ThemeBrandProto.UNRECOGNIZED,
                    ThemeBrandProto.THEME_BRAND_DEFAULT,
                        -> ThemeBrand.DEFAULT

                    ThemeBrandProto.THEME_BRAND_ANDROID -> ThemeBrand.ANDROID
                },
                darkThemeConfig = when (it.darkThemeConfig) {
                    null,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                    DarkThemeConfigProto.UNRECOGNIZED,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                        ->
                        DarkThemeConfig.FOLLOW_SYSTEM

                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                        DarkThemeConfig.LIGHT

                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                },
                useDynamicColor = it.useDynamicColor,
                shouldHideOnboarding = it.shouldHideOnboarding,
            )
        }

    suspend fun setFollowedTopicIds(topicIds: Set<String>) {
        try {
            userPreferences.updateData {
                it.copy {
                    followedTopicIds.clear()
                    followedTopicIds.putAll(topicIds.associateWith { true })
                    updateShouldHideOnboardingIfNecessary()
                }
            }
        } catch (ioException: IOException) {
            Log.e("Preferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setTopicIdFollowed(topicId: String, followed: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    if (followed) {
                        followedTopicIds.put(topicId, followed)
                    } else {
                        followedTopicIds.remove(topicId)
                    }
                    updateShouldHideOnboardingIfNecessary()
                }
            }
        } catch (ioException: IOException) {
            Log.e("Preferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.themeBrand = when (themeBrand) {
                        ThemeBrand.DEFAULT -> ThemeBrandProto.THEME_BRAND_DEFAULT
                        ThemeBrand.ANDROID -> ThemeBrandProto.THEME_BRAND_ANDROID
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("Preferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.useDynamicColor = useDynamicColor
                }
            }
        } catch (ioException: IOException) {
            Log.e("Preferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM ->
                        DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM

                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }

    suspend fun setNewsResourceBookmarked(newsResourceId: String, bookmarked: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    if (bookmarked) {
                        this.bookmarkedNewsResourceIds.put(newsResourceId, bookmarked)
                    } else {
                        this.bookmarkedNewsResourceIds.remove(newsResourceId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("Preferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setNewsResourcesViewed(newsResourceIds: List<String>, viewed: Boolean) {
        try {
            userPreferences.updateData { prefs ->
                prefs.copy {
                    newsResourceIds.forEach { id ->
                        if (viewed) {
                            this.viewedNewsResourceIds.put(id, true)
                        } else {
                            this.viewedNewsResourceIds.remove(id)
                        }
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("Preferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun getChangeListVersions() = userPreferences.data.map {
        ChangeListVersions(
            topicVersion = it.topicChangeListVersion,
            newsResourceVersion = it.newsResourceChangeListVersion,
        )

    }

    suspend fun updateChangeListVersion(update: ChangeListVersions.() -> ChangeListVersions) {
        try {
            userPreferences.updateData { prefs ->
                val updatedVersion = update(
                    ChangeListVersions(
                        topicVersion = prefs.topicChangeListVersion,
                        newsResourceVersion = prefs.newsResourceChangeListVersion
                    )
                )
                prefs.copy {
                    this.topicChangeListVersion = updatedVersion.topicVersion
                    this.newsResourceChangeListVersion = updatedVersion.newsResourceVersion
                }
            }
        } catch (ioException: IOException) {
            Log.e("Preferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.shouldHideOnboarding = shouldHideOnboarding
                }
            }
        } catch (ioException: IOException) {

        }
    }
}

private fun UserPreferencesKt.Dsl.updateShouldHideOnboardingIfNecessary() {
    if (followedTopicIds.isEmpty() && followedAuthorIds.isEmpty()) {
        shouldHideOnboarding = false
    }
}


