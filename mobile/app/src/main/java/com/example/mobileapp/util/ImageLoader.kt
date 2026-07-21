package com.example.mobileapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Tiny dependency-free image loader (no Glide/Coil) with a small in-memory
 * cache, used to render car.imageUrl the way <img src={car.imageUrl}/> does
 * in the React cards.
 */
object ImageLoader {

    private val executor: ExecutorService = Executors.newCachedThreadPool()
    private val mainHandler = Handler(Looper.getMainLooper())
    private val cache = LinkedHashMap<String, Bitmap>()
    private const val MAX_CACHE_ENTRIES = 40

    fun load(imageView: ImageView, url: String?, placeholderResId: Int) {
        imageView.setImageResource(placeholderResId)
        if (url.isNullOrBlank()) return

        // tag the view so a fast-scrolling RecyclerView doesn't apply a stale result
        imageView.tag = url

        synchronized(cache) {
            cache[url]?.let {
                imageView.setImageBitmap(it)
                return
            }
        }

        executor.execute {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
                connection.doInput = true
                connection.connect()

                val bitmap = BitmapFactory.decodeStream(connection.inputStream)
                connection.disconnect()

                if (bitmap != null) {
                    synchronized(cache) {
                        if (cache.size >= MAX_CACHE_ENTRIES) {
                            val oldestKey = cache.keys.firstOrNull()
                            if (oldestKey != null) cache.remove(oldestKey)
                        }
                        cache[url] = bitmap
                    }

                    mainHandler.post {
                        if (imageView.tag == url) {
                            imageView.setImageBitmap(bitmap)
                        }
                    }
                }
            } catch (e: Exception) {
                // keep placeholder on failure
            }
        }
    }
}
