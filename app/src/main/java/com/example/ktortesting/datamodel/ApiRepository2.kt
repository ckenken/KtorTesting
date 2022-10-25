package com.example.ktortesting.datamodel

import com.example.ktortesting.struct.SchoolResponse
import io.ktor.http.*
import org.koin.java.KoinJavaComponent.get

object ApiRepository2 {
    private val requestEngine = get<RequestClient>(RequestClient::class.java)

    //    https://api.dtto.com/v3/countries/JP/schools?nextKey=0:600&lang=jp&withPetition=true
    suspend fun getSchools(
        country: String,
        lang: String,
        withPetition: Boolean,
        nextKey: String,
    ): RequestResult<SchoolResponse> {
        val response = requestEngine.client.requestGet<SchoolResponse> {
            url {
                path("v3/countries/$country/schools")
                parameters.appendAll(
                    buildParametersOf(
                        "lang" to lang,
                        "withPetition" to withPetition,
                        "nextKey" to nextKey,
                    )
                )
            }
        }
        return response
    }
}

