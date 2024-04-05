package com.indigo.custom.networks

sealed class NetworkProcess<out T>() {
    class Success< T>(val data: T) : NetworkProcess<T>()
    class Error<T>(val message: String,val data: T? = null,val errorCode:Int?=null) : NetworkProcess<T>()
    object Loading : NetworkProcess<Nothing>()
}