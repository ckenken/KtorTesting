package com.example.ktortesting.struct

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SchoolResponse(
    @SerialName("list") val items: List<School>,
    @SerialName("nextKey") val nextKey: String? = null
)