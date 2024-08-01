package com.example.movieapp.presentation.ui.adapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.ResultsItem
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieAdapterTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var adapter: MovieAdapter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        adapter = MovieAdapter()
    }

    @Test
    fun testGetItemCount() {
        val items = listOf(
            ResultsItem("Overview 1", "en", "Title 1", false, "Title 1", listOf(), "poster_path_1", "", "2024-01-01", 7.5, 7.0, 1, false, 100),
            ResultsItem("Overview 2", "en", "Title 2", false, "Title 2", listOf(), "poster_path_2", "", "2024-02-02", 8.0, 8.0, 2, false, 200)
        )
        adapter.submitList(items)
        assertEquals(items.size, adapter.itemCount)
    }
}