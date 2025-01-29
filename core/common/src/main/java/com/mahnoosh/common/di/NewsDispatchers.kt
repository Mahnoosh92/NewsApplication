package com.mahnoosh.common.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val newsDispatchers: NewsDispatchers)

enum class NewsDispatchers {
    Default,
    IO,
}