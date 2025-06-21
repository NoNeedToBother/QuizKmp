package ru.kpfu.itis.quiz.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpEngineFactory()
    }
    single {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    single {
        buildHttpClient(
            engine = get<HttpEngineFactory>().createEngine(get()),
            json = get(),
        )
    }
}

private const val BASE_URL = "opentdb.com"

private fun buildHttpClient(
    engine: HttpClientEngineFactory<HttpClientEngineConfig>,
    json: Json,
) = HttpClient(engine) {
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.BODY
    }
    install(ContentNegotiation) {
        json(json)
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 5000
        requestTimeoutMillis = 10000
        socketTimeoutMillis = 10000
    }
    defaultRequest {
        url {
            this.host = BASE_URL
            this.protocol = URLProtocol.HTTPS
        }
    }
}