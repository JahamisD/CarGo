package com.example.mobileapp.model

import org.json.JSONObject

/** Mirrors the "user" object returned by POST /auth/login */
data class User(
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: String
) {
    companion object {
        fun fromJson(o: JSONObject): User {
            return User(
                userId = o.optInt("userId"),
                firstName = o.optString("firstName"),
                lastName = o.optString("lastName"),
                email = o.optString("email"),
                role = o.optString("role", "CUSTOMER")
            )
        }
    }

    fun toJson(): JSONObject {
        val o = JSONObject()
        o.put("userId", userId)
        o.put("firstName", firstName)
        o.put("lastName", lastName)
        o.put("email", email)
        o.put("role", role)
        return o
    }
}
