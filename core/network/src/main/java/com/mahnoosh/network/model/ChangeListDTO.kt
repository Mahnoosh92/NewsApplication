package com.mahnoosh.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangeListDTO(
    val id: String,
    val changeListVersion: Int,
    val isDelete: Boolean,
)
