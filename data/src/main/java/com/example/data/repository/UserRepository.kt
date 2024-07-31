package com.example.data.repository

import com.example.data.local.UserPreferences
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userPreferences: UserPreferences) {

    val user: Flow<User?> = userPreferences.user

    suspend fun saveUser(user: User) {
        userPreferences.saveUser(user)
    }

    suspend fun clearUser() {
        userPreferences.clearUser()
    }

    suspend fun editUser(username: String?, email: String?, password: String?) {
        userPreferences.editUser(username, email, password)
    }
}