package com.example.galery.ui.utility

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Environment
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class DownloadsImage(
    private val callback: (File) -> Unit,
    private val textCallback: (String) -> Unit
) : AsyncTask<String?, Void?, Void?>() {
    override fun doInBackground(vararg strings: String?): Void? {
        var url: URL? = null
        try {
            url = URL(strings[0])
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        var bm: Bitmap? = null
        try {
            bm = BitmapFactory.decodeStream(url?.openConnection()?.getInputStream())
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val path: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES.toString() + "/AndroidDvlpr") //Creates app specific folder
        if (!path.exists()) {
            path.mkdirs()
        }
        val imageFile = File(path, System.currentTimeMillis().toString() + ".png")
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(imageFile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            bm?.compress(Bitmap.CompressFormat.PNG, 100, out) // Compress Image
            out?.flush()
            out?.close()
            callback(imageFile)

        } catch (e: Exception) {
        }
        return null
    }

    override fun onPostExecute(aVoid: Void?) {
        super.onPostExecute(aVoid)
        textCallback("Image Saved!")
    }
}