package com.mahnoosh.network.data

import com.mahnoosh.network.model.ChangeListDTO
import com.mahnoosh.network.model.NewsResourceDTO
import com.mahnoosh.network.model.TopicDTO
import javax.inject.Inject

class DefaultNewsNetworkDatasource @Inject constructor():NewsNetworkDatasource {
    override suspend fun getTopics(ids: List<String>?): List<TopicDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getNewsResources(ids: List<String>?): List<NewsResourceDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopicChangeList(after: Int?): List<ChangeListDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getNewsResourceChangeList(after: Int?): List<ChangeListDTO> {
        TODO("Not yet implemented")
    }
}