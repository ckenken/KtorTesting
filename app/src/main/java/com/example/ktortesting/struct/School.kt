package com.example.ktortesting.struct

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Model of a school.
 *
 * @author Hiking
 */
@Serializable
@Parcelize
data class School(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("abbr") val abbr: String? = null,
    @SerialName("domain") val domain: String? = null,
    @SerialName("zone") @Zone val zone: String? = null,
    @SerialName("aliasLong") val aliasLong: String? = null,
    @SerialName("aliasShort") val aliasShort: String? = null,
    @SerialName("petition") val petition: PetitionInfo? = null
) : Parcelable {
    @Target(AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
    annotation class Zone
}