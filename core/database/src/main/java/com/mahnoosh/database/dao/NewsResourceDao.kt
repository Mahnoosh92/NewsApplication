package com.mahnoosh.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mahnoosh.database.model.NewsResourceEntity
import com.mahnoosh.database.model.NewsResourceTopicCrossRef
import com.mahnoosh.database.model.PopulatedNewsResource
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsResourceDao {

    @Transaction
    @Query(
        """
    SELECT * FROM news_resources
    WHERE
        CASE WHEN :useFilterNewsIds
             THEN id IN (:filterNewsIds) 
             ELSE 1
        END
      AND
        CASE WHEN :useFilterTopicIds
            THEN id IN( SELECT news_resource_id FROM news_resources_topics WHERE topic_id IN (:filterTopicIds))
            ELSE 1
        END
      ORDER BY publish_date DESC  
        """
    )
    fun getNewsResources(
        useFilterTopicIds: Boolean = false,
        filterTopicIds: Set<String> = emptySet(),
        useFilterNewsIds: Boolean = false,
        filterNewsIds: Set<String> = emptySet(),
    ): Flow<List<PopulatedNewsResource>>

    @Transaction
    @Query(
        """
        SELECT id FROM news_resources
        WHERE
            CASE WHEN :useFilterNewsIds
                 THEN id IN (:filterNewsIds)
                 ELSE 1
            END
        AND
            CASE WHEN :useFilterTopicIds
                 THEN id IN (SELECT news_resource_id FROM news_resources_topics WHERE topic_id IN (:filterTopicIds))
                 ELSE 1
            END
        ORDER BY publish_date DESC
    """
    )

    fun getNewsResourceIds(
        useFilterTopicIds: Boolean = false,
        filterTopicIds: Set<String> = emptySet(),
        useFilterNewsIds: Boolean = false,
        filterNewsIds: Set<String> = emptySet(),
    ): Flow<List<String>>

    @Upsert
    suspend fun upsertNewsResources(newsResourceEntities: List<NewsResourceEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreTopicCrossRefEntities(
        newsResourceTopicCrossReferences: List<NewsResourceTopicCrossRef>,
    )

    @Query(
        value = """
            DELETE FROM news_resources
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteNewsResources(ids: List<String>)
}