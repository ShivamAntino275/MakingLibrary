package com.indigo.custom.models


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.indigo.custom.recycleradapter.AbstractModel
import kotlinx.parcelize.RawValue

@Parcelize
data class ContestResponseModel(
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
        @SerializedName("data")
        var `data`: List<ContestData>? = null,
        @SerializedName("message")
        var message: String? = null,
        @SerializedName("statusCode")
        var statusCode: Int? = null
    ) : Parcelable {
        @Parcelize
        data class ContestData(
            @SerializedName("achieved_target")
            var achievedTarget: String? = null,
            @SerializedName("bonus_pts")
            var bonusPts: String? = null,
            @SerializedName("contestDetails")
            var contestDetails: ContestDetails? = null,
            @SerializedName("currentContestSlabDetails")
            var currentContestSlabDetails: CurrentContestSlabDetails? = null,
            @SerializedName("currentPrizeDetails")
            var currentPrizeDetails: CurrentPrizeDetails? = null,
            @SerializedName("id")
            var id: String? = null,
            @SerializedName("rank")
            var rank: String? = null,
            var isSelected:Boolean=false
        ) : Parcelable, AbstractModel() {
            @Parcelize
            data class ContestDetails(
                @SerializedName("bonus_type")
                var bonusType: String? = null,
                @SerializedName("contest_name")
                var contestName: String? = null,
                @SerializedName("contestSlabDetails")
                var contestSlabDetails: List<ContestSlabDetail>? = null,
                @SerializedName("end_date")
                var endDate: String? = null,
                @SerializedName("gameStyleDetails")
                var gameStyleDetails: GameStyleDetails? = null,
                @SerializedName("id")
                var id: String? = null,
                @SerializedName("number_of_slabs")
                var numberOfSlabs: @RawValue Any? = null,
                @SerializedName("prizeDetails")
                var prizeDetails: List<PrizeDetail>? = null,
                @SerializedName("promotion_basis")
                var promotionBasis: String? = null,
                @SerializedName("start_date")
                var startDate: String? = null,
                @SerializedName("terms")
                var terms: String? = null,
                @SerializedName("contest_status")
                var contestStatus: String? = null
            ) : Parcelable {
                @Parcelize
                data class ContestSlabDetail(
                    @SerializedName("bonus_type")
                    var bonusType: String? = null,
                    @SerializedName("bonus_value")
                    var bonusValue: String? = null,
                    @SerializedName("is_dynamic")
                    var isDynamic: Boolean? = null,
                    @SerializedName("id")
                    var id: String? = null,
                    @SerializedName("slab_number")
                    var slabNumber: String? = null,
                    @SerializedName("target_type")
                    var targetType: String? = null,
                    @SerializedName("target_value")
                    var targetValue: String? = null,
                    var isAchieved:Boolean =false,
                    var isCurrent:Boolean =false,
                    var pointsEarned:String="",
                    var additional:String?=null
                ) : Parcelable,AbstractModel()

                @Parcelize
                data class GameStyleDetails(
                    @SerializedName("createdAt")
                    var createdAt: String? = null,
                    @SerializedName("id")
                    var id: String? = null,
                    @SerializedName("name")
                    var name: String? = null,
                    @SerializedName("type")
                    var type: String? = null,
                    @SerializedName("updatedAt")
                    var updatedAt: String? = null
                ) : Parcelable

                @Parcelize
                data class PrizeDetail(
                    @SerializedName("description")
                    var description: String? = null,
                    @SerializedName("prize_image")
                    var prizeImage: String? = null,
                    @SerializedName("prize_number")
                    var prize_number: String? = null,
                    @SerializedName("total_winners")
                    var total_winners: String? = null,
                    @SerializedName("prize_name")
                    var prizeName: String? = null,
                    var isEven:Boolean=false,
                    var isCurrent: Boolean=false,
                    var isAchieved: Boolean=false,


                ) : Parcelable,AbstractModel()
            }

            @Parcelize
            data class CurrentContestSlabDetails(
                @SerializedName("slab_number")
                var slabNumber: Int? = null
            ) : Parcelable

            @Parcelize
            data class CurrentPrizeDetails(
                @SerializedName("description")
                var description: String? = null,
                @SerializedName("prize_image")
                var prizeImage:@RawValue Any? = null,
                @SerializedName("prize_name")
                var prizeName: String? = null ,
                @SerializedName("prize_number")
                var prizeNumber: Int? = null
            ) : Parcelable
        }
    }
}