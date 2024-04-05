package com.indigo.custom.models


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ErrorBodyResponse(
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
        @SerializedName("api")
        var api: String? = null,
        @SerializedName("body")
        var body: Body? = null,
        @SerializedName("date")
        var date: String? = null,
        @SerializedName("env")
        var env: String? = null,
        @SerializedName("level")
        var level: String? = null,
        @SerializedName("message")
        var message: String? = null,
        @SerializedName("method")
        var method: String? = null,
        @SerializedName("name")
        var name: String? = null
    ) : Parcelable {
        @Parcelize
        data class Body(
            @SerializedName("city")
            var city: String? = null,
            @SerializedName("district")
            var district: String? = null,
            @SerializedName("email")
            var email: String? = null,
            @SerializedName("first_name")
            var firstName: String? = null,
            @SerializedName("pin_code")
            var pinCode: String? = null,
            @SerializedName("state")
            var state: String? = null
        ) : Parcelable
    }
}