package com.indigo.custom.networks

import com.google.gson.Gson
import com.indigo.custom.models.ErrorBodyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class Repository {
    suspend fun <T> makeApiCall(apiCall: suspend () -> Response<T>): Flow<NetworkProcess<T>> =
        flow {
            try {
                val response = apiCall()
                emit(NetworkProcess.Loading)
                when {
                    response.code() in 100..199 -> {}
                    response.isSuccessful -> {
                        val body = response.body()
                        body?.let {
                            emit(NetworkProcess.Success(body))
                        }
                    }
                    response.code() in 300..399 -> {
                    }
                    response.code() == 401 -> {
                        val errorBody = Gson().fromJson(response.errorBody()?.string(),ErrorBodyResponse::class.java)
                        emit(NetworkProcess.Error(errorBody.message?:"Something went Wrong", null,401))
                    }
                    response.code() == 404 -> {

                    }
                    response.code() in 500..599 -> {
                    }
                    else -> {
                        val errorBody = Gson().fromJson(response.errorBody()?.string(),ErrorBodyResponse::class.java)
                        emit(NetworkProcess.Error(errorBody.message?:"Something went Wrong", null))
                    }
                }

            } catch (e: Exception) {
                emit(NetworkProcess.Error(e.message.toString(), null))
            }
        }.flowOn(Dispatchers.IO)


}