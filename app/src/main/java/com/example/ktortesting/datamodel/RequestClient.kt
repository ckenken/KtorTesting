package com.example.ktortesting.datamodel

import io.ktor.client.*

interface RequestClient {
    val baseDomain: String
        get() = "https://api.dtto.com/"
    val client: HttpClient
}