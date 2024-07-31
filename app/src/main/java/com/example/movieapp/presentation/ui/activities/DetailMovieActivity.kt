package com.example.movieapp.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityDetailMovieBinding
import com.example.domain.model.ResultsItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataMovie: ResultsItem? = intent.getParcelableExtra(STORY_INTENT_DATA) as? ResultsItem
        dataMovie?.let { displayMovieDetails(it) }
    }

    private fun displayMovieDetails(data: ResultsItem) {
        with(binding) {
            Glide.with(this@DetailMovieActivity)
                .load("${com.example.common.Constants.IMG_URL}${data.posterPath}")
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(posterImageView)

            titleTextView.text = data.title
            overviewTextView.text = data.overview
            adultTextView.text = "Adult: ${data.adult}"
            releaseDateTextView.text = "Release Date: ${data.releaseDate}"
            voteAverageTextView.text = "Vote Average: ${data.voteAverage}"
            voteCountTextView.text = "Vote Average: ${data.voteCount}"
        }
    }

    companion object {
        const val STORY_INTENT_DATA = "STORY_INTENT_DATA"
    }
}


