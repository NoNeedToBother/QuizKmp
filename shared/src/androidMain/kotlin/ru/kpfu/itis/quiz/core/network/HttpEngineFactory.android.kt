package ru.kpfu.itis.quiz.core.network

import ru.kpfu.itis.quiz.core.config.Configuration
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.config
import io.ktor.client.engine.okhttp.OkHttp

actual open class HttpEngineFactory actual constructor() {
    actual fun createEngine(config: Configuration): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp.config {
        config {
            retryOnConnectionFailure(true)
        }
    }
}
