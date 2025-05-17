package com.example.ktortesting.datamodel

import com.example.ktortesting.struct.SchoolResponse
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

object ApiRepository2 : KoinComponent {
    private val requestEngine: RequestClient by inject(named("okhttp"))

    //    https://api.dtto.com/v3/countries/JP/schools?nextKey=0:600&lang=jp&withPetition=true
    suspend fun getSchools(
        country: String,
        lang: String,
        withPetition: Boolean,
        nextKey: String,
    ): RequestResult<String> = requestEngine.client.requestGet {
        url {
            path("v3/countries/$country/schools")
            parameters.appendAll(
                buildParametersOf(
                    "lang" to lang,
                    "withPetition" to withPetition,
                    "nextKey" to nextKey,
                ),
            )
        }
    }
}

