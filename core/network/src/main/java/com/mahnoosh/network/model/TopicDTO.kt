package com.mahnoosh.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TopicDTO(
    val id: String,
    val name: String = "",
    val shortDescription: String = "",
    val longDescription: String = "",
    val url: String = "",
    val imageUrl: String = "",
    val followed: Boolean = false,
)
