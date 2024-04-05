package com.indigo.custom.models

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class PointEarnedModelResponse(
    @SerializedName("data")
    var `data`: Data? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("success")
    var success: Boolean? = null,
    @SerializedName("type")
    var type: String? = null
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("code")
        var code: Int? = null,
        @SerializedName("data")
        var `data`: PointEarnData? = null,
        @SerializedName("message")
        var message: String? = null
    ) : Parcelable {
        @Parcelize
        data class PointEarnData(
            @SerializedName("totalPointsMonth")
            var totalPointsMonth: String? = null,
            @SerializedName("totalPointsYear")
            var totalPointsYear: String? = null,
            @SerializedName("valuePerPoint")
            var valuePerPoint: String? = null
        ) : Parcelable
    }
}