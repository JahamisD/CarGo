package com.example.mobileapp.model

import org.json.JSONObject

/**
 * Mirrors the nested "car" summary object inside each booking returned by
 * GET /bookings/user/{userId}. Field names differ from Car.kt
 * (carBrand/carName/carPrice instead of brand/model/pricePerDay) because
 * that's what the backend's booking serializer sends.
 */
data class BookingCarInfo(
    val carBrand: String,
    val carName: String,
    val carPrice: Double
) {
    companion object {
        fun fromJson(o: JSONObject): BookingCarInfo {
            return BookingCarInfo(
                carBrand = o.optString("carBrand"),
                carName = o.optString("carName"),
                carPrice = o.optDouble("carPrice", 0.0)
            )
        }
    }
}

data class Booking(
    val bookId: Int,
    val bookingDateStart: String,
    val bookingDateEnd: String,
    val status: String,
    val car: BookingCarInfo
) {
    companion object {
        fun fromJson(o: JSONObject): Booking {
            return Booking(
                bookId = o.optInt("bookId"),
                bookingDateStart = o.optString("bookingDateStart"),
                bookingDateEnd = o.optString("bookingDateEnd"),
                status = o.optString("status"),
                car = BookingCarInfo.fromJson(o.getJSONObject("car"))
            )
        }
    }
}
