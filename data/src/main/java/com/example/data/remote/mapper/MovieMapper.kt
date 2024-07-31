package com.example.data.remote.mapper

import com.example.data.remote.response.DatesDTO
import com.example.data.remote.response.MovieResponseDTO
import com.example.data.remote.response.ResultsItemDTO
import com.example.domain.model.Dates
import com.example.domain.model.MovieResponse
import com.example.domain.model.ResultsItem

fun MovieResponseDTO.toDomain(): MovieResponse {
    return MovieResponse(
        dates = dates.toDomain(),
        page = page,
        totalPages = totalPages,
        results = results.map { it.toDomain() },
        totalResults = totalResults
    )
}

fun ResultsItemDTO.toDomain(): ResultsItem {
    return ResultsItem(
        overview = overview,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        video = video,
        title = title,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        id = id,
        adult = adult,
        voteCount = voteCount
    )
}

fun DatesDTO.toDomain(): Dates {
    return Dates(
        maximum = maximum,
        minimum = minimum
    )
}