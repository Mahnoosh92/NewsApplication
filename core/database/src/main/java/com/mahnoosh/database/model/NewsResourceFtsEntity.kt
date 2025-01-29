package com.mahnoosh.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "newsResourcesFts")
@Fts4
data class NewsResourceFtsEntity(

    @ColumnInfo(name = "newsResourceId")
    val newsResourceId: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "content")
    val content: String,
)