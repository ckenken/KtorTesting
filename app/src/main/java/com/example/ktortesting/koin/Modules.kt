package com.example.ktortesting.koin

import com.example.ktortesting.datamodel.CIOClient
import com.example.ktortesting.datamodel.RequestClient
import org.koin.dsl.module

val httpModule = module {
    single<RequestClient> {
        CIOClient
    }
}