package com.indigo.custom.models

import com.indigo.custom.recycleradapter.AbstractModel

data class GetAllreferralResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean,
    val type: String
) {
    data class Data(
        val `data`: List<ReferalData>,
        val message: String,
        val status: Int
    ):AbstractModel() {
        data class ReferalData(
            val createdAt: String,
            val depot_id: String,
            val id: String,
            val influencerDetails: InfluencerDetails,
            val primary_dealer_id: String,
            val refereeDetails: RefereeDetails,
            val referee_id: String,
            val referralLeadsTrackDetails: ReferralLeadsTrackDetails,
            val referral_date: String,
            val referrer_id: String,
            val staff_id: String,
            val updatedAt: String
        ):AbstractModel() {
            data class InfluencerDetails(
                val id: String,
                val influencerCategoryDetails: InfluencerCategoryDetails,
                val userDetails: UserDetails
            ) {
                data class InfluencerCategoryDetails(
                    val name: String
                )

                data class UserDetails(
                    val createdAt: String,
                    val email: String,
                    val first_name: String,
                    val id: String,
                    val is_disable: Boolean,
                    val last_name: String,
                    val password: String,
                    val phone_number: String,
                    val role_id: String,
                    val status: String,
                    val updatedAt: String
                )
            }

            data class RefereeDetails(
                val address: Any,
                val city: Any,
                val email: Any,
                val id: String,
                val influencer_category_id: String,
                val name: String,
                val phone_number: String,
                val pin_code: Any,
                val state: Any
            )

            data class ReferralLeadsTrackDetails(
                val id: String,
                val referral_lead_id: String,
                val status: String,
                val sub_status: Any
            )
        }
    }
}