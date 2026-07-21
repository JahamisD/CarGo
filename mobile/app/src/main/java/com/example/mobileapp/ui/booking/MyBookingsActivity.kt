package com.example.mobileapp.ui.booking

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.R
import com.example.mobileapp.model.Booking
import com.example.mobileapp.network.ApiClient
import com.example.mobileapp.session.SessionManager
import com.example.mobileapp.ui.BaseActivity
import org.json.JSONArray

/** Kotlin port of page/booking/MyBookings.js */
class MyBookingsActivity : BaseActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var textStatus: TextView
    private lateinit var adapter: BookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

        setupToolbar(findViewById<Toolbar>(R.id.toolbar), getString(R.string.title_my_bookings))

        recycler = findViewById(R.id.recyclerBookings)
        textStatus = findViewById(R.id.textStatus)

        adapter = BookingAdapter(emptyList())
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val user = SessionManager.getUser(this)
        if (user == null) {
            // same as {!user && <h3>Please login first</h3>} in MyBookings.js
            recycler.visibility = View.GONE
            textStatus.visibility = View.VISIBLE
            textStatus.text = getString(R.string.please_login_first)
            return
        }

        fetchBookings(user.userId)
    }

    private fun fetchBookings(userId: Int) {
        textStatus.visibility = View.VISIBLE
        textStatus.text = getString(R.string.loading_bookings)
        recycler.visibility = View.GONE

        ApiClient.get("/bookings/user/$userId", object : ApiClient.JsonCallback {
            override fun onSuccess(body: String) {
                val jsonArray = JSONArray(body)
                val bookings = mutableListOf<Booking>()
                for (i in 0 until jsonArray.length()) {
                    bookings.add(Booking.fromJson(jsonArray.getJSONObject(i)))
                }
                adapter.updateBookings(bookings)

                if (bookings.isEmpty()) {
                    textStatus.visibility = View.VISIBLE
                    textStatus.text = getString(R.string.no_bookings_found)
                    recycler.visibility = View.GONE
                } else {
                    textStatus.visibility = View.GONE
                    recycler.visibility = View.VISIBLE
                }
            }

            override fun onError(message: String) {
                textStatus.visibility = View.VISIBLE
                textStatus.text = message
                recycler.visibility = View.GONE
            }
        })
    }
}
