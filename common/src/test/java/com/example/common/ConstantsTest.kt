package com.example.common

import org.junit.Assert.*
import org.junit.Test

class ConstantsTest {

    @Test
    fun testConstants() {
        assertEquals("https://api.themoviedb.org/3/movie/", Constants.BASE_URL)
        assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyYjEwOTVmMDcyZGJiNjBiMGM0ZmQzNjg0OGYyNTQwNSIsInN1YiI6IjY2NDZjYWFiZjYwZmZlZWVmMjkyMTQwOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zwMrSKwwH_xdpZrWxelBt3Ql94nPg8nayXBL8OuBpdM", Constants.TOKEN)
        assertEquals("https://image.tmdb.org/t/p/w500", Constants.IMG_URL)
    }
}

