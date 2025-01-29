package com.mahnoosh.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.mahnoosh.database.model.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Query(
        value = """
        SELECT * FROM topics
        WHERE id = :topicId
    """,
    )
    fun getTopicEntity(topicId: String): Flow<TopicEntity>

    @Query(value = "SELECT * FROM topics")
    fun getTopicEntities(): Flow<List<TopicEntity>>

    @Query(value = "SELECT * FROM topics")
    suspend fun getOneOffTopicEntities(): List<TopicEntity>

    @Query(
        value = """
        SELECT * FROM topics
        WHERE id IN (:ids)
    """,
    )
    fun getTopicEntities(ids: Set<String>): Flow<List<TopicEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreTopics(topicEntities: List<TopicEntity>): List<Long>

    @Upsert
    suspend fun upsertTopics(entities: List<TopicEntity>)

    @Query(
        value = """
            DELETE FROM topics
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteTopics(ids: List<String>)
}