package com.indigo.custom.models

data class CreateRefferalsRequest(
    var phone_number: String? = null,
    var name: String? = null,
    var category_name: String = "contractor"
)