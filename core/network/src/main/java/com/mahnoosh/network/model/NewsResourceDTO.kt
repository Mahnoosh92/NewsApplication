package com.mahnoosh.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class NewsResourceDTO(
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String,
    val publishDate: Instant,
    val type: String,
    val topics: List<String> = listOf(),
)
