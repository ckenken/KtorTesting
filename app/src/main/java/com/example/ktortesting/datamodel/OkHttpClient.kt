package com.example.ktortesting.datamodel

import com.example.ktortesting.FlipperApp
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Named("okhttp")
@Single
class OkHttpClient : RequestClient,
    ProxyProvider by ProxyProviderImpl {
    override val client: HttpClient = HttpClient(
            OkHttp.create {
                addInterceptor(
                    FlipperOkhttpInterceptor(FlipperApp.networkFlipperPlugin, true)
                )
            }
        ) {
            defaultRequest {
                url(baseDomain)
            }
            expectSuccess = true

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
            engine {
                proxy = getProxySettings()
            }
        }
}