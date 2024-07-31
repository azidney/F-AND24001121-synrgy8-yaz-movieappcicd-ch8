package com.example.movieapp.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.local.UserPreferences
import com.example.data.repository.UserRepository
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.presentation.ui.viewmodel.MovieViewModel
import com.example.movieapp.presentation.ui.viewmodel.UserViewModel
import com.example.movieapp.presentation.ui.viewmodel.UserViewModelFactory
import com.example.movieapp.presentation.ui.adapter.MovieAdapter
import com.example.domain.model.MovieResponse
import com.example.movieapp.presentation.ui.activities.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import com.example.common.Result
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            UserRepository(
                UserPreferences(
                    this
                )
            )
        )
    }
    private val viewModel: MovieViewModel by viewModels()
    private val adapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.user.observe(this, Observer { user ->
            if (user != null) {
                binding.tvWelcome.text = "Welcome, ${user.username}"
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        setupRecyclerView(adapter)
    }

    private fun setupRecyclerView(adapter: MovieAdapter) {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        observeMovie()
    }

    private fun observeMovie() {
        viewModel.getMovie().observe(this) { result ->
            handleMovieResult(result, adapter)
        }
    }

    private fun handleMovieResult(result: Result<MovieResponse>, adapter: MovieAdapter) {
        when (result) {
            is Result.Loading ->  {
                showLoading(true)
            }
            is Result.Success -> {
                showLoading(false)
                val data = result.data.results
                if (data.isNullOrEmpty()) {
                    showSnackBar("Produk tidak ada")
                } else {
                    adapter.submitList(data)
                }
            }
            is Result.Error -> {
                showLoading(false)
                showSnackBar(result.error)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showSnackBar(messageResId: Any) {
        com.example.common.SnackbarUtils.showWithDismissAction(binding.root, messageResId.toString())
    }
}