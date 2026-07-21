package com.example.mobileapp.model

import org.json.JSONObject

/** Mirrors the car objects returned by GET /cars and GET /cars/{id} */
data class Car(
    val carId: Int,
    val brand: String,
    val model: String,
    val year: Int,
    val details: String,
    val pricePerDay: Double,
    val imageUrl: String?
) {
    companion object {
        fun fromJson(o: JSONObject): Car {
            return Car(
                carId = o.optInt("carId"),
                brand = o.optString("brand"),
                model = o.optString("model"),
                year = o.optInt("year"),
                details = o.optString("details"),
                pricePerDay = o.optDouble("pricePerDay", 0.0),
                imageUrl = if (o.isNull("imageUrl")) null else o.optString("imageUrl")
            )
        }
    }
}
