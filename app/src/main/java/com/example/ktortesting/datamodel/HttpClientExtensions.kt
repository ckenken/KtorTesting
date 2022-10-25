package com.example.ktortesting.datamodel

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*

suspend inline fun <reified T> HttpClient.requestGet(
    block: HttpRequestBuilder.() -> Unit,
): RequestResult<T> {
    return try {
        RequestResult.Success(get(HttpRequestBuilder().apply(block)).body())
    } catch (t: Throwable) {
        RequestResult.Failed(t)
    }
}

fun buildParametersOf(
    vararg pairs: Pair<String, Any?>,
) = buildParametersOf(pairs.toMap())

fun buildParametersOf(
    map: Map<String, Any?>,
): StringValues = StringValuesBuilderImpl().apply {
    map.mapNotNull { (key, value) ->
        append(key, value.toString())
    }
}.build()
