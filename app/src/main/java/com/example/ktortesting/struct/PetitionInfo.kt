package com.example.ktortesting.struct

import android.os.Parcelable
import com.example.ktortesting.struct.PetitionInfo.Status.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Shared object model from these API:
 * - GET /validate/identity/status
 * - GET /schoolPetition/{schoolId}
 *
 * @author Evan Hou
 */
@Serializable
@Parcelize
data class PetitionInfo(
    @SerialName("status") val status: Status,
    @SerialName("collectedCount") val collectedCount: Int,
    @SerialName("schoolId") val schoolId: String? = null,
    @SerialName("schoolName") val schoolName: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("signed") val signed: Boolean? = null
) : Parcelable {

    /**
     * Backend returning values:
     * [Open]       open for collecting signatures.
     * [Available]  the school is available.
     *
     * App declared value:
     * [NotSigned]: there's no signed petition yet.
     */
    @Serializable
    enum class Status {
        @SerialName("open") Open,
        @SerialName("available") Available,
        @SerialName("not_signed") NotSigned
    }
}