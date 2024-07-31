package com.example.movieapp.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel()  {
    fun getMovie() = getMoviesUseCase()
}