package com.mahnoosh.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "topicsFts")
@Fts4
data class TopicFtsEntity(

    @ColumnInfo(name = "topicId")
    val topicId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "shortDescription")
    val shortDescription: String,

    @ColumnInfo(name = "longDescription")
    val longDescription: String,
)