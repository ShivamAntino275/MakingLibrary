package com.indigo.custom.models


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.indigo.custom.recycleradapter.AbstractModel

@Parcelize
data class RankResponseModel(
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
        var `data`: List<RankModelData>? = null,
        @SerializedName("message")
        var message: String? = null
    ) : Parcelable {
        @Parcelize
        data class RankModelData(
            @SerializedName("bonus_pts")
            var bonusPts: String? = null,
            @SerializedName("influencerDetails")
            var influencerDetails: InfluencerDetails? = null,
            @SerializedName("rank")
            var rank: String? = null
        ) : Parcelable,AbstractModel() {
            @Parcelize
            data class InfluencerDetails(
                @SerializedName("id")
                var id: String? = null,
                @SerializedName("userDetails")
                var userDetails: UserDetails? = null
            ) : Parcelable {
                @Parcelize
                data class UserDetails(
                    @SerializedName("first_name")
                    var firstName: String? = null,
                    @SerializedName("last_name")
                    var lastName: String? = null
                ) : Parcelable
            }
        }
    }
}