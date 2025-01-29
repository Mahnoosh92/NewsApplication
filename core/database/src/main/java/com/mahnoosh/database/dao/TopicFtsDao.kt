package com.mahnoosh.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahnoosh.database.model.TopicFtsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicFtsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(topics: List<TopicFtsEntity>)

    @Query("SELECT topicId FROM topicsFts WHERE topicsFts MATCH :query")
    fun searchAllTopics(query: String): Flow<List<String>>

    @Query("SELECT count(*) FROM topicsFts")
    fun getCount(): Flow<Int>
}