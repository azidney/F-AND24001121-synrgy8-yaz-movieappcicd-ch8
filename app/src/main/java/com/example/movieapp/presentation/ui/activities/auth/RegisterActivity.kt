package com.example.movieapp.presentation.ui.activities.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.data.local.UserPreferences
import com.example.data.repository.UserRepository
import com.example.movieapp.databinding.ActivityRegisterBinding
import com.example.movieapp.presentation.ui.viewmodel.UserViewModel
import com.example.movieapp.presentation.ui.viewmodel.UserViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            UserRepository(
                UserPreferences(
                    this
                )
            )
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Tolong isi datanya dengan lengkap", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Berhasil register", Toast.LENGTH_SHORT).show()
                userViewModel.registerUser(username, email, password)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}
