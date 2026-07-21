package com.example.mobileapp.session

import android.content.Context
import android.content.SharedPreferences
import com.example.mobileapp.model.User
import org.json.JSONObject

/**
 * Equivalent of localStorage.setItem('user', ...) / getItem / removeItem
 * in App.js, backed by SharedPreferences instead of the browser's storage.
 */
object SessionManager {

    private const val PREFS_NAME = "cargo_prefs"
    private const val KEY_USER = "user"

    private fun prefs(context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(context: Context, user: User) {
        prefs(context).edit().putString(KEY_USER, user.toJson().toString()).apply()
    }

    fun getUser(context: Context): User? {
        val json = prefs(context).getString(KEY_USER, null) ?: return null
        return try {
            User.fromJson(JSONObject(json))
        } catch (e: Exception) {
            null
        }
    }

    fun isLoggedIn(context: Context): Boolean = getUser(context) != null

    fun logout(context: Context) {
        prefs(context).edit().remove(KEY_USER).apply()
    }
}
