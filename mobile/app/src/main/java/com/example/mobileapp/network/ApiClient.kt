package com.example.mobileapp.network

import android.os.Handler
import android.os.Looper
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Small dependency-free HTTP helper (java.net.HttpURLConnection + org.json,
 * both built into the Android SDK - no Retrofit/OkHttp/Gson needed).
 *
 * Every call runs on a background thread and delivers the result back on
 * the main thread via the callback, same shape as the fetch(...).then(...)
 * chains in the original React pages.
 */
object ApiClient {

    private val executor: ExecutorService = Executors.newCachedThreadPool()
    private val mainHandler = Handler(Looper.getMainLooper())

    interface JsonCallback {
        fun onSuccess(body: String)
        fun onError(message: String)
    }

    fun get(path: String, callback: JsonCallback) {
        run(path, "GET", null, callback)
    }

    fun post(path: String, jsonBody: JSONObject, callback: JsonCallback) {
        run(path, "POST", jsonBody.toString(), callback)
    }

    private fun run(path: String, method: String, body: String?, callback: JsonCallback) {
        executor.execute {
            var connection: HttpURLConnection? = null
            try {
                val url = URL(ApiConfig.BASE_URL + path)
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = method
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Accept", "application/json")

                if (body != null) {
                    connection.doOutput = true
                    OutputStreamWriter(connection.outputStream, StandardCharsets.UTF_8).use { writer ->
                        writer.write(body)
                        writer.flush()
                    }
                }

                val status = connection.responseCode
                val stream = if (status in 200..299) connection.inputStream else connection.errorStream
                val responseText = readStream(stream)

                if (status in 200..299) {
                    postSuccess(callback, responseText)
                } else {
                    postError(callback, extractErrorMessage(responseText, status))
                }

            } catch (e: IOException) {
                postError(callback, "Network error: could not reach the server. Check ApiConfig.BASE_URL and that the backend is running.")
            } catch (e: Exception) {
                postError(callback, e.message ?: "Unknown error")
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun readStream(stream: java.io.InputStream?): String {
        if (stream == null) return ""
        val reader = BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8))
        val sb = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            sb.append(line)
        }
        reader.close()
        return sb.toString()
    }

    private fun extractErrorMessage(responseText: String, status: Int): String {
        return try {
            val o = JSONObject(responseText)
            o.optString("message", "Request failed ($status)")
        } catch (e: JSONException) {
            "Request failed ($status)"
        }
    }

    private fun postSuccess(callback: JsonCallback, body: String) {
        mainHandler.post { callback.onSuccess(body) }
    }

    private fun postError(callback: JsonCallback, message: String) {
        mainHandler.post { callback.onError(message) }
    }
}
