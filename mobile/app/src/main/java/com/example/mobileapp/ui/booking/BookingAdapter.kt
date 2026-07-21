package com.example.mobileapp.ui.booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.R
import com.example.mobileapp.model.Booking

/** Renders each booking card, equivalent to the .map((booking) => (...)) block in MyBookings.js */
class BookingAdapter(private var bookings: List<Booking>) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textCarTitle)
        val start: TextView = view.findViewById(R.id.textStart)
        val end: TextView = view.findViewById(R.id.textEnd)
        val status: TextView = view.findViewById(R.id.textStatus)
        val price: TextView = view.findViewById(R.id.textPrice)
    }

    fun updateBookings(newBookings: List<Booking>) {
        bookings = newBookings
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        val context = holder.itemView.context

        holder.title.text = context.getString(
            R.string.brand_label_short,
            booking.car.carBrand,
            booking.car.carName
        )
        holder.start.text = context.getString(R.string.booking_start, booking.bookingDateStart)
        holder.end.text = context.getString(R.string.booking_end, booking.bookingDateEnd)
        holder.status.text = context.getString(R.string.booking_status, booking.status)
        holder.price.text = context.getString(R.string.price_per_day, "$${booking.car.carPrice}")
    }

    override fun getItemCount(): Int = bookings.size
}
