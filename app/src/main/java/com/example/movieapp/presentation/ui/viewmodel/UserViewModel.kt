package com.example.movieapp.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.User
import com.example.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user: LiveData<User?> = userRepository.user.asLiveData()

    fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.saveUser(User(username, email, password))
        }
    }
    fun editUser(username: String?, email: String?, password: String?) {
        viewModelScope.launch {
            userRepository.editUser(username, email, password)
        }
    }


    fun loginUser(username: String, password: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            userRepository.user.collect { user ->
                if (user != null && user.username == username && user.password == password) {
                    result.postValue(true)
                } else {
                    result.postValue(false)
                }
            }
        }
        return result
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clearUser()
        }
    }
}