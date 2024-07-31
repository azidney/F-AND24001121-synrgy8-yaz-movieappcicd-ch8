package com.example.movieapp.background

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import androidx.core.net.toUri
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.movieapp.presentation.ui.activities.ProfileActivity.Companion.KEY_IMAGE_URI
import java.io.File
import java.io.FileOutputStream

class BlurWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val inputUriString = inputData.getString(KEY_IMAGE_URI)

        if (inputUriString.isNullOrEmpty()) {
            return Result.failure()
        }

        val inputUri = Uri.parse(inputUriString)

        val inputStream = applicationContext.contentResolver.openInputStream(inputUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val blurredBitmap = blurBitmap(bitmap)

        val outputUriString = saveBitmapToFile(blurredBitmap)

        val outputData = workDataOf("outputUri" to outputUriString)
        return Result.success(outputData)
    }

    private fun blurBitmap(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        paint.alpha = 80
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return output
    }

    private fun saveBitmapToFile(bitmap: Bitmap): String {
        val outputDir = applicationContext.cacheDir
        val outputFile = File(outputDir, "blurred_image.jpg")
        val outputStream = FileOutputStream(outputFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return outputFile.toUri().toString()
    }
}