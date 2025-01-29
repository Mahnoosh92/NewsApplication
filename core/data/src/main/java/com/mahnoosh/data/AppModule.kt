package com.mahnoosh.data

import android.content.Context
import androidx.room.Room
import com.mahnoosh.data.repository.OfflineFirstUserDataRepository
import com.mahnoosh.data.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository

    companion object {
        @Singleton
        @Provides
        fun provideYourDatabase(
            @ApplicationContext app: Context
        ) = Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            "your_db_name"
        ).build() // The reason we can construct a database for the repo

        @Singleton
        @Provides
        fun provideYourDao(db: UserDatabase) = db.userDao()
    }
}