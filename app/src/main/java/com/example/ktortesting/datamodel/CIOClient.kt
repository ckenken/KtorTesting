package com.example.ktortesting.datamodel

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object CIOClient : RequestClient,
    ProxyProvider by ProxyProviderImpl {
    override val client: HttpClient = HttpClient(CIO) {
        expectSuccess = true

        defaultRequest {
            url(baseDomain)
        }

        install(Auth) {
            bearer {

            }
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }

        install(HttpTimeout) {
            this.requestTimeoutMillis
        }

        engine {
            this.pipelining
            proxy = getProxySettings()
        }
    }.apply {
        setupInterceptors(this)
    }

    private fun setupInterceptors(client: HttpClient) {
        client.plugin(HttpSend).intercept { request ->
            Log.d("ckenken", "interceptor 1")
            execute(request)
        }

        client.plugin(HttpSend).intercept { request ->
            Log.d("ckenken", "interceptor 2")
            execute(request)
        }
    }
}