package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.common.Result
import com.example.domain.model.MovieResponse
interface MovieRepository {
    fun getMovie(): LiveData<Result<MovieResponse>>
}
