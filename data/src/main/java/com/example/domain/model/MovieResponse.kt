package com.example.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MovieResponse(
    val dates: Dates,
    val page: Int,
    val totalPages: Int,
    val results: List<ResultsItem>,
    val totalResults: Int
)

@Parcelize
data class ResultsItem(
    val overview: String,
    val originalLanguage: String,
    val originalTitle: String,
    val video: Boolean,
    val title: String,
    val genreIds: List<Int>,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val popularity: Double,
    val voteAverage: Double,
    val id: Int,
    val adult: Boolean,
    val voteCount: Int
): Parcelable

data class Dates(
    val maximum: String,
    val minimum: String
)