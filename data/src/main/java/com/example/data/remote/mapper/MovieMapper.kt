package com.example.data.remote.mapper

import com.example.data.remote.response.DatesDTO
import com.example.data.remote.response.MovieResponseDTO
import com.example.data.remote.response.ResultsItemDTO
import com.example.domain.model.Dates
import com.example.domain.model.MovieResponse
import com.example.domain.model.ResultsItem

fun MovieResponseDTO?.toDomain(): MovieResponse {
    return MovieResponse(
        dates = this?.dates?.toDomain() ?: Dates(maximum = "", minimum = ""),
        page = this?.page ?: 0,
        totalPages = this?.totalPages ?: 0,
        results = this?.results?.map { it.toDomain() } ?: emptyList(),
        totalResults = this?.totalResults ?: 0
    )
}

fun ResultsItemDTO?.toDomain(): ResultsItem {
    return ResultsItem(
        overview = this?.overview ?: "",
        originalLanguage = this?.originalLanguage ?: "",
        originalTitle = this?.originalTitle ?: "",
        video = this?.video ?: false,
        title = this?.title ?: "",
        genreIds = this?.genreIds ?: emptyList(),
        posterPath = this?.posterPath ?: "",
        backdropPath = this?.backdropPath ?: "",
        releaseDate = this?.releaseDate ?: "",
        popularity = this?.popularity ?: 0.0,
        voteAverage = this?.voteAverage ?: 0.0,
        id = this?.id ?: 0,
        adult = this?.adult ?: false,
        voteCount = this?.voteCount ?: 0
    )
}

fun DatesDTO?.toDomain(): Dates {
    return Dates(
        maximum = this?.maximum ?: "",
        minimum = this?.minimum ?: ""
    )
}