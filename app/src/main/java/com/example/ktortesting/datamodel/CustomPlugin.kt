package com.example.ktortesting.datamodel

import android.util.Log
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

@KtorDsl
public class CustomPlugin private constructor() {
    public companion object Plugin : HttpClientPlugin<CustomPlugin, CustomPlugin> {
        override val key: AttributeKey<CustomPlugin> = AttributeKey("DigestCustomPlugin")

        override fun prepare(block: CustomPlugin.() -> Unit): CustomPlugin {
            return CustomPlugin().apply(block)
        }

        @OptIn(InternalAPI::class)
        override fun install(plugin: CustomPlugin, scope: HttpClient) {
            scope.plugin(HttpSend).intercept { request ->
                Log.d("ckenken", "scope.plugin(HttpSend) -2")
                execute(request)
            }

            // request
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                Log.d("ckenken", "requestPipeline before")
            }
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                Log.d("ckenken", "requestPipeline State")
            }
            scope.requestPipeline.intercept(HttpRequestPipeline.Transform) {
                Log.d("ckenken", "requestPipeline Transform")
            }
            scope.requestPipeline.intercept(HttpRequestPipeline.Render) {
                Log.d("ckenken", "requestPipeline Render")
            }
            scope.requestPipeline.intercept(HttpRequestPipeline.Send) {
                Log.d("ckenken", "requestPipeline Send")
            }

            val tempPhase = PipelinePhase("tempPhase")
            scope.sendPipeline.insertPhaseAfter(HttpSendPipeline.State, tempPhase)
            scope.sendPipeline.intercept(tempPhase) {
                Log.d("ckenken", "custom phase after send.state")
            }

            scope.plugin(HttpSend).intercept { request ->
                Log.d("ckenken", "scope.plugin(HttpSend) -1")
                execute(request)
            }

            // Send
            scope.sendPipeline.intercept(HttpSendPipeline.Before) {
                Log.d("ckenken", "sendPipeline Before")
            }

            scope.sendPipeline.intercept(HttpSendPipeline.State) {
                Log.d("ckenken", "sendPipeline State")
            }
            scope.sendPipeline.intercept(HttpSendPipeline.Monitoring) {
                Log.d("ckenken", "sendPipeline Monitoring")
            }
            scope.sendPipeline.intercept(HttpSendPipeline.Engine) {
                Log.d("ckenken", "sendPipeline Engine")
            }
            scope.sendPipeline.intercept(HttpSendPipeline.Receive) {
                Log.d("ckenken", "sendPipeline Receive")
            }

            scope.plugin(HttpSend).intercept { request ->
                Log.d("ckenken", "scope.plugin(HttpSend) 0")
                execute(request)
            }

            // receive
            scope.receivePipeline.intercept(HttpReceivePipeline.Before) {
                Log.d("ckenken", "receivePipeline Before")
            }
            scope.receivePipeline.intercept(HttpReceivePipeline.State) {
                Log.d("ckenken", "receivePipeline State")
            }
            scope.receivePipeline.intercept(HttpReceivePipeline.After) {
                Log.d("ckenken", "receivePipeline After")
            }

            scope.plugin(HttpSend).intercept { request ->
                Log.d("ckenken", "scope.plugin(HttpSend) 1")
                execute(request)
            }

            // response
            scope.responsePipeline.intercept(HttpResponsePipeline.Receive) {
                Log.d("ckenken", "responsePipeline Receive")
            }
            scope.responsePipeline.intercept(HttpResponsePipeline.Parse) {
                Log.d("ckenken", "responsePipeline Parse")
            }
            scope.responsePipeline.intercept(HttpResponsePipeline.Transform) {
                Log.d("ckenken", "responsePipeline Transform")
            }
            scope.responsePipeline.intercept(HttpResponsePipeline.State) {
                Log.d("ckenken", "responsePipeline State")
            }
            scope.responsePipeline.intercept(HttpResponsePipeline.After) {
                Log.d("ckenken", "responsePipeline After")
            }

            scope.plugin(HttpSend).intercept { request ->
                Log.d("ckenken", "scope.plugin(HttpSend) 2")
                execute(request)
            }
        }
    }
}

/**
 * Install [CustomPlugin] plugin.
 */
public fun HttpClientConfig<*>.CustomPlugin(block: CustomPlugin.() -> Unit) {
    install(CustomPlugin, block)
}
