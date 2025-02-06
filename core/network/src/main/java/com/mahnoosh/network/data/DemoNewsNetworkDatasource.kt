package com.mahnoosh.network.data

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import com.mahnoosh.common.di.Dispatcher
import com.mahnoosh.common.di.NewsDispatchers
import com.mahnoosh.network.demo.DemoAssetManager
import com.mahnoosh.network.model.ChangeListDTO
import com.mahnoosh.network.model.NewsResourceDTO
import com.mahnoosh.network.model.TopicDTO
import com.mahnoosh.network.utils.JvmUnitTestDemoAssetManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.BufferedReader
import javax.inject.Inject

class DemoNewsNetworkDataSource @Inject constructor(
    @Dispatcher(NewsDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: DemoAssetManager = JvmUnitTestDemoAssetManager,
) : NewsNetworkDatasource {

    companion object {
        private const val NEWS_ASSET = "news.json"
        private const val TOPICS_ASSET = "topics.json"
    }

    override suspend fun getTopics(ids: List<String>?): List<TopicDTO> {
        return getDataFromJsonFile<TopicDTO>(TOPICS_ASSET)
    }

    override suspend fun getNewsResources(ids: List<String>?): List<NewsResourceDTO> {
        return getDataFromJsonFile<NewsResourceDTO>(NEWS_ASSET)
    }

    override suspend fun getTopicChangeList(after: Int?): List<ChangeListDTO> {
        return getTopics().mapToChangeList(TopicDTO::id)
    }

    override suspend fun getNewsResourceChangeList(after: Int?): List<ChangeListDTO> {
        return getNewsResources().mapToChangeList(NewsResourceDTO::id)
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend inline fun <reified T> getDataFromJsonFile(fileName: String): List<T> =
        withContext(ioDispatcher) {
            assets.open(fileName).use { inputStream ->
                if (SDK_INT <= M) {
                    /**
                     * On API 23 (M) and below we must use a workaround to avoid an exception being
                     * thrown during deserialization. See:
                     * https://github.com/Kotlin/kotlinx.serialization/issues/2457#issuecomment-1786923342
                     */
                    /**
                     * On API 23 (M) and below we must use a workaround to avoid an exception being
                     * thrown during deserialization. See:
                     * https://github.com/Kotlin/kotlinx.serialization/issues/2457#issuecomment-1786923342
                     */
                    inputStream.bufferedReader().use(BufferedReader::readText)
                        .let(networkJson::decodeFromString)
                } else {
                    networkJson.decodeFromStream(inputStream)
                }
            }
        }

    private fun <T> List<T>.mapToChangeList(idGetter: (T) -> String): List<ChangeListDTO> =
        mapIndexed { index, item ->
            ChangeListDTO(
                id = idGetter(item),
                changeListVersion = index,
                isDelete = false,
            )
        }
}
