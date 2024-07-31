package com.example.data.remote.api

import com.example.data.remote.response.MovieResponseDTO
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("upcoming?language=en-US&page=1")
    suspend fun getMovie(): MovieResponseDTO
}