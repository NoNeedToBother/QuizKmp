package ru.kpfu.itis.quiz.core.network

import ru.kpfu.itis.quiz.core.config.Configuration
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

expect open class HttpEngineFactory() {

    fun createEngine(config: Configuration): HttpClientEngineFactory<HttpClientEngineConfig>

}