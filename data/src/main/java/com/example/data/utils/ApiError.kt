package com.example.data.utils

import android.util.Log
import com.example.common.Result
import com.google.gson.Gson
import retrofit2.HttpException

object ApiError{
    fun handleHttpException(exception: HttpException): Result.Error {
        val jsonInString = exception.response()?.errorBody()?.string()
        val errorBody = Gson().fromJson(jsonInString, com.example.data.remote.response.ErrorResponse::class.java)
        val errorMessage = errorBody.message
        Log.e("qweqe", errorMessage)
        return Result.Error(errorMessage)
    }
}

