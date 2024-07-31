package com.example.movieapp.presentation.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.movieapp.background.BlurWorker
import com.example.data.local.UserPreferences
import com.example.data.repository.UserRepository
import com.example.movieapp.databinding.ActivityProfileBinding
import com.example.movieapp.presentation.ui.activities.auth.LoginActivity
import com.example.movieapp.presentation.ui.viewmodel.UserViewModel
import com.example.movieapp.presentation.ui.viewmodel.UserViewModelFactory
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import java.io.File
import java.io.FileNotFoundException

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            UserRepository(
                UserPreferences(
                    this
                )
            )
        )
    }
    private val PICK_IMAGE = 1


    companion object {
        const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
        const val PERMISSION_REQUEST_CODE = 101
        const val CHANNEL_ID = "MY_CHANNEL_ID"
        const val NOTIFICATION_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
        userViewModel.user.observe(this, Observer { user ->
            if (user != null) {
                binding.etNewUsername.setText(user.username)
                binding.etNewEmail.setText(user.email)
                binding.etNewPassword.setText(user.password)
            } else {
                Toast.makeText(this@ProfileActivity, "Invalid Credentials", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        binding.btnLogout.setOnClickListener {
            userViewModel.logout()
            Toast.makeText(this@ProfileActivity, "Berhasil Logout", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnEditProfile.setOnClickListener {
            val newUsername = binding.etNewUsername.text.toString()
            val newEmail = binding.etNewEmail.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            userViewModel.editUser(newUsername, newEmail, newPassword)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnUploadPhoto.setOnClickListener {
            if (checkPermission()) {
                openImagePicker()
            } else {
                requestPermission()
            }
        }
    }
    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImagePicker()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            Log.d("ProfileActivity", "Selected Image URI: $selectedImageUri")
            selectedImageUri?.let {
                makeStatusNotification("Blurring image in progress...")
                val outputDir = applicationContext.cacheDir
                val outputFile = File(outputDir, "blurred_image.jpg")
                val outputUri = outputFile.toUri()
                val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
                    .setInputData(workDataOf(KEY_IMAGE_URI to it.toString(), "outputUri" to outputUri.toString()))
                    .build()

                WorkManager.getInstance(this).enqueue(blurRequest)

                WorkManager.getInstance(this).getWorkInfoByIdLiveData(blurRequest.id).observe(this, { workInfo ->
                    Log.d("tes1", "Selected Image URI: $workInfo")
                        val outputData = workInfo.outputData
                    val outputUri = outputData.getString("outputUri")
                    Log.d("testesssss", "Selected Image URI: $outputData")
                    Log.d("testes", "Selected Image URI: $outputUri")
                        outputUri?.let { uri ->
                            try {
                                Log.d("tes2", "Selected Image URI: $selectedImageUri")
                                val inputStream = contentResolver.openInputStream(Uri.parse(uri))
                                val bitmap = BitmapFactory.decodeStream(inputStream)
                                binding.ivProfilePhoto.setImageBitmap(bitmap)
                                NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID)
                                makeStatusNotification("Blurring image completed.")
                            } catch (e: FileNotFoundException) {
                                Log.e("ProfileActivity", "File not found", e)
                            }
                        }

                })
            }
        }
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notif"
            val descriptionText = "oke"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun makeStatusNotification(message: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Image Blur")
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification)
    }
}