package com.example.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("status_code")
    val status: String,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("status_message")
    val message: String
)