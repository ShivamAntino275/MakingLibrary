package com.indigo.custom.models

data class CreateRefferalsResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean,
    val type: String
) {
    data class Data(
        val message: String,
        val status: Int
    )
}