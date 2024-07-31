package com.example.data.utils

import android.util.Log
import com.example.common.Result
import com.example.data.remote.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException

object ApiError {
    fun handleHttpException(exception: HttpException): Result.Error {
        val errorBody = exception.response()?.errorBody()?.string()
        if (errorBody != null) {
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            val errorMessage = errorResponse.message
            Log.e("ApiError", errorMessage)
            return Result.Error(errorMessage)
        }
        return Result.Error("An unknown error occurred")
    }
}