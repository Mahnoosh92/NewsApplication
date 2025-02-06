package com.mahnoosh.network.data

import com.mahnoosh.network.model.ChangeListDTO
import com.mahnoosh.network.model.NewsResourceDTO
import com.mahnoosh.network.model.TopicDTO

interface NewsNetworkDatasource {
    suspend fun getTopics(ids: List<String>? = null): List<TopicDTO>

    suspend fun getNewsResources(ids: List<String>? = null): List<NewsResourceDTO>

    suspend fun getTopicChangeList(after: Int? = null): List<ChangeListDTO>

    suspend fun getNewsResourceChangeList(after: Int? = null): List<ChangeListDTO>
}