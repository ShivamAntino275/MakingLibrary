package com.indigo.custom.models


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class SignupResponseModel(
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
        var `data`: Data? = null,
        @SerializedName("message")
        var message: String? = null ,
        @SerializedName("isAcitve")
        var isActive: Boolean? = null
    ) : Parcelable {
        @Parcelize
        data class Data(
            @SerializedName("createdAt")
            var createdAt: String? = null,
            @SerializedName("email")
            var email: String? = null,
            @SerializedName("first_name")
            var firstName: String? = null,
            @SerializedName("id")
            var id: String? = null,
            @SerializedName("is_disable")
            var isDisable: Boolean? = null,
            @SerializedName("last_name")
            var lastName: String? = null,
            @SerializedName("phone_number")
            var phoneNumber: String? = null,
            @SerializedName("refreshToken")
            var refreshToken: String? = null,
            @SerializedName("role")
            var role: String? = null,
            @SerializedName("status")
            var status: String? = null,
            @SerializedName("token")
            var token: String? = null,
            @SerializedName("updatedAt")
            var updatedAt: String? = null,
            @SerializedName("isRegistered")
            var isRegistered: Boolean? = null
        ) : Parcelable
    }
}