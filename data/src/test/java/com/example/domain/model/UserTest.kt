package com.example.domain.model

import org.junit.Assert.*
import org.junit.Test

class UserTest {

    @Test
    fun `user object should have correct properties`() {
        val username = "john_doe"
        val email = "john@example.com"
        val password = "password123"
        val user = User(username, email, password)
        assertEquals(username, user.username)
        assertEquals(email, user.email)
        assertEquals(password, user.password)
    }
}