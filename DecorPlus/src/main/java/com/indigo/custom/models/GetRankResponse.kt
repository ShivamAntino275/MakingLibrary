package com.indigo.custom.models

data class GetRankResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean,
    val type: String
) {
    data class Data(
        val code: Int,
        val `data`: List<Data>,
        val message: String
    ) {
        data class Data(
            val bonus_pts: Int,
            val influencerDetails: InfluencerDetails,
            val rank: Int
        ) {
            data class InfluencerDetails(
                val id: String,
                val userDetails: UserDetails
            ) {
                data class UserDetails(
                    val first_name: String,
                    val last_name: String
                )
            }
        }
    }
}