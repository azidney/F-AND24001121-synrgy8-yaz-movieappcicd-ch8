package com.example.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.data.remote.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton
import com.example.common.Result
import com.example.data.remote.mapper.toDomain
import com.example.data.utils.ApiError
import com.example.domain.model.MovieResponse
import com.example.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val application: Application
) : MovieRepository {
    override fun getMovie(): LiveData<Result<MovieResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getMovie()
            emit(Result.Success(response.toDomain()))
        } catch (e: HttpException) {
            Log.e("error", e.toString())
            emit(ApiError.handleHttpException(e))
        } catch (exception: IOException) {
            Log.e("errorx", exception.toString())
            emit(Result.Error("Network error occurred. Please check your internet connection"))
        } catch (exception: Exception) {
            Log.e("errory", exception.toString())
            emit(Result.Error(exception.message ?: "Unknown Error"))
        }
    }
}
