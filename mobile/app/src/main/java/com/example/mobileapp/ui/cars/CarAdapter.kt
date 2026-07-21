package com.example.mobileapp.ui.cars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.R
import com.example.mobileapp.model.Car
import com.example.mobileapp.util.ImageLoader

/** Renders each car card, equivalent to the .map((car) => (<div className="card">...)) block in Cars.js */
class CarAdapter(
    private var cars: List<Car>,
    private val onViewDetails: (Car) -> Unit
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageCar)
        val title: TextView = view.findViewById(R.id.textTitle)
        val year: TextView = view.findViewById(R.id.textYear)
        val details: TextView = view.findViewById(R.id.textDetails)
        val price: TextView = view.findViewById(R.id.textPrice)
        val viewDetailsButton: View = view.findViewById(R.id.btnViewDetails)
    }

    fun updateCars(newCars: List<Car>) {
        cars = newCars
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        val context = holder.itemView.context

        holder.title.text = context.getString(R.string.brand_label_short, car.brand, car.model)
        holder.year.text = context.getString(R.string.year_label, car.year)
        holder.details.text = car.details
        holder.price.text = context.getString(R.string.price_per_day, "$${car.pricePerDay}")

        ImageLoader.load(holder.image, car.imageUrl, R.drawable.ic_car_placeholder)

        val clickListener = View.OnClickListener { onViewDetails(car) }
        holder.viewDetailsButton.setOnClickListener(clickListener)
        holder.itemView.setOnClickListener(clickListener)
    }

    override fun getItemCount(): Int = cars.size
}
