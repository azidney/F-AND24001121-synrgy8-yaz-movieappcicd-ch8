package com.example.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieResponseDTO(
    @SerializedName("dates") val dates: DatesDTO,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<ResultsItemDTO>,
    @SerializedName("total_results") val totalResults: Int
)

@Parcelize
data class ResultsItemDTO(
	@SerializedName("overview") val overview: String,
	@SerializedName("original_language") val originalLanguage: String,
	@SerializedName("original_title") val originalTitle: String,
	@SerializedName("video") val video: Boolean,
	@SerializedName("title") val title: String,
	@SerializedName("genre_ids") val genreIds: List<Int>,
	@SerializedName("poster_path") val posterPath: String,
	@SerializedName("backdrop_path") val backdropPath: String,
	@SerializedName("release_date") val releaseDate: String,
	@SerializedName("popularity") val popularity: Double,
	@SerializedName("vote_average") val voteAverage: Double,
	@SerializedName("id") val id: Int,
	@SerializedName("adult") val adult: Boolean,
	@SerializedName("vote_count") val voteCount: Int
) : Parcelable

data class DatesDTO(
	@SerializedName("maximum") val maximum: String,
	@SerializedName("minimum") val minimum: String
)
