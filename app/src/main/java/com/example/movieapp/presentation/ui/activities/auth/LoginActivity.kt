package com.example.movieapp.presentation.ui.activities.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.data.local.UserPreferences
import com.example.data.repository.UserRepository
import com.example.movieapp.databinding.ActivityLoginBinding
import com.example.movieapp.presentation.ui.activities.MainActivity
import com.example.movieapp.presentation.ui.viewmodel.UserViewModel
import com.example.movieapp.presentation.ui.viewmodel.UserViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Tolong isi inputan dengan lengkap", Toast.LENGTH_SHORT).show()
            } else {
                userViewModel.loginUser(username, password).observe(this, Observer { success ->
                    if (success) {
                        Toast.makeText(this@LoginActivity, "Berhasil login", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Username dan password anda salah",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                })
            }
        }
        binding.tvLoginToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}