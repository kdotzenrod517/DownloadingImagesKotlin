package com.kdotz.downloadingimageskotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView

    fun downloadImage(view: View) {
        val task = ImageDownloader(this)
        val myImage: Bitmap
        try {
            myImage =
                task.execute("https://tr.rbxcdn.com/ca6eff30c3c33c6765cd565089d14080/420/420/Decal/Png")
                    .get()!!
            imageView.setImageBitmap(myImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
    }

    companion object {
        class ImageDownloader internal constructor(context: MainActivity) : AsyncTask<String, Void, Bitmap?>() {

            override fun doInBackground(vararg urls: String?): Bitmap? {

                try {
                    val url = URL(urls[0])
                    val connection = url.openConnection() as HttpURLConnection
                    connection.connect()
                    val file = connection.inputStream
                    return BitmapFactory.decodeStream(file)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }

            }
        }
    }
}
