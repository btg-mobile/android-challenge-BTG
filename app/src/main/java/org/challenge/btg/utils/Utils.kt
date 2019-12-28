package org.challenge.btg.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.widget.ImageView
import java.io.File
import java.net.URL

class NetworkCachedTask(private val imageView: ImageView) : AsyncTask<String, Void, File?>() {
    override fun doInBackground(vararg urls: String): File? {
        val urlOfImage = Global.movieDBBaseImage+urls[0]
        val imageFileName = Global.pathCache + File.pathSeparator + urls[1]+".png"
        return try {
            if (!File(imageFileName).exists()) {
                val inputStream = URL(urlOfImage).openStream()
                val bmpStream = BitmapFactory.decodeStream(inputStream)
                File(imageFileName).writeBitmap(bmpStream, Bitmap.CompressFormat.PNG, 85)
            }
            File(imageFileName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: File?) {
        if(result!=null){
            imageView.setImageURI(Uri.fromFile(result))
        }
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }
}