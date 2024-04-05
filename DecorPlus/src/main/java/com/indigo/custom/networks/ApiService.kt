package com.indigo.custom.networks

import com.indigo.custom.models.ContestResponseModel
import com.indigo.custom.models.CreateRefferalsRequest
import com.indigo.custom.models.CreateRefferalsResponse
import com.indigo.custom.models.GetAllreferralResponse
import com.indigo.custom.models.PointEarnedModelResponse
import com.indigo.custom.models.GetRankResponse
import com.indigo.custom.models.RankResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.POST

interface ApiService {

    @GET(pointEarned)
    suspend fun pointEarned(@Header(AUTHORIZATION) authToken: String?= TOKEN): Response<PointEarnedModelResponse>

    @GET(contestDetails)
    suspend fun contestDetails(@Header(AUTHORIZATION) authToken: String?= TOKEN): Response<ContestResponseModel>

    @GET(CONTEST_RANK)
    suspend fun contestRank(@Header(AUTHORIZATION) authToken: String?= TOKEN, @Query(contest_id) contestId:String): Response<RankResponseModel>

    @GET(getAllreferrals)
    suspend fun  getAllReferrals(@Header(AUTHORIZATION) authToken: String? = TOKEN, @Query(PAGE) page:Int= 1, @Query(LIMIT) limit:Int= 10, @Query(sort_Order) sortOrder:String= desc, @Query(sort_field) sortField:String= updatedAt): Response<GetAllreferralResponse>

    @POST(createReferrals)
    suspend fun createReferrals(@Header(AUTHORIZATION) authToken: String? = TOKEN , @Body request: CreateRefferalsRequest):Response<CreateRefferalsResponse>
}