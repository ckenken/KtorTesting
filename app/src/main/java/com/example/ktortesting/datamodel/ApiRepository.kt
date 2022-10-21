package com.example.ktortesting.datamodel

import android.util.Log
import com.example.ktortesting.struct.School
import com.example.ktortesting.struct.SchoolResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.java.KoinJavaComponent.get

object ApiRepository {
    private val requestEngine = get<RequestClient>(RequestClient::class.java)

    //    https://api.dtto.com/v3/countries/JP/schools?nextKey=0:600&lang=jp&withPetition=true
    suspend fun getSchools(
        country: String,
        lang: String,
        withPetition: Boolean,
        nextKey: String,
    ): RequestResult<List<School>> {
        try {
            val response = requestEngine.client.get {
                url {
                    path("v3/countries/$country/schools")
                    parameters.append(
                        name = "lang",
                        value = lang,
                    )
                    parameters.append(
                        name = "withPetition",
                        value = withPetition.toString(),
                    )
                    parameters.append(
                        name = "nextKey",
                        value = nextKey,
                    )
                }
            }.body<SchoolResponse>()
            Log.d("ckenken", "response = $response")
            return RequestResult.Success(response.items)
        } catch (t: Throwable) {
            Log.e("ckenken", "error!", t)
            return RequestResult.Failed(t)
        }
    }
}