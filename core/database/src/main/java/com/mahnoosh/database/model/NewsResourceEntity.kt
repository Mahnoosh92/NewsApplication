package com.mahnoosh.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.datetime.Instant


@Entity(
    tableName = "news_resources",
)
data class NewsResourceEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    @ColumnInfo(name = "header_image_url")
    val headerImageUrl: String?,
    @ColumnInfo(name = "publish_date")
    val publishDate: Instant,
    val type: String,
)
