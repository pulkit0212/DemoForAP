package com.ap.demo.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class ImageCacheManager(private val cacheDir: File) {

    private val memoryCache: LruCache<String, Bitmap>
    private val diskCacheDir: File

    init {
        // Initialize memory cache with 1/8th of the maximum available memory
        val maxMemory = Runtime.getRuntime().maxMemory().toInt()
        val cacheSize = maxMemory / 8
        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.byteCount / 1024 // Convert bytes to kilobytes
            }
        }

        // Initialize disk cache directory
        diskCacheDir = File(cacheDir, "images")
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs()
        }
    }

    suspend fun loadImage(url: String): Bitmap? {
        return withContext(Dispatchers.IO){
            // Check memory cache
            val bitmap = memoryCache.get(url)
            if (bitmap != null) {
                return@withContext bitmap
            }

            // Check disk cache
            val file = File(diskCacheDir, url.hashCode().toString())
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                if (bitmap != null) {
                    // Update memory cache
                    memoryCache.put(url, bitmap)
                    return@withContext bitmap
                }
            }

            try {
                // Load image from URL
                val inputStream = URL(url).openStream()
                val newBitmap = BitmapFactory.decodeStream(inputStream)

                // Save image to disk cache
                saveToDiskCache(newBitmap, url.hashCode().toString())

                // Update memory cache
                memoryCache.put(url, newBitmap)

                newBitmap
            }
            catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    }

    private fun saveToDiskCache(bitmap: Bitmap, fileName: String) {
        val file = File(diskCacheDir, fileName)
        FileOutputStream(file).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        }
    }
}