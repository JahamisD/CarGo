package com.example.mobileapp.ui.cars

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.R
import com.example.mobileapp.model.Car
import com.example.mobileapp.network.ApiClient
import com.example.mobileapp.ui.BaseActivity
import org.json.JSONArray

/**
 * Kotlin port of page/cars/Cars.js. This is the launcher / landing screen,
 * matching the "/" -> "/cars" redirect in App.js.
 */
class CarsActivity : BaseActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var editBrand: EditText
    private lateinit var btnSearch: Button
    private lateinit var textStatus: TextView
    private lateinit var adapter: CarAdapter

    private var allCars: List<Car> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cars)

        setupToolbar(findViewById<Toolbar>(R.id.toolbar), getString(R.string.available_cars))

        recycler = findViewById(R.id.recyclerCars)
        editBrand = findViewById(R.id.editBrandSearch)
        btnSearch = findViewById(R.id.btnSearch)
        textStatus = findViewById(R.id.textStatus)

        adapter = CarAdapter(emptyList()) { car ->
            val intent = Intent(this, CarDetailsActivity::class.java)
            intent.putExtra(CarDetailsActivity.EXTRA_CAR_ID, car.carId)
            startActivity(intent)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnSearch.setOnClickListener { applyBrandFilter() }

        fetchCars()
    }

    override fun onResume() {
        super.onResume()
        // refresh the toolbar's greeting/menu in case login state changed since we left this screen
        invalidateOptionsMenu()
    }

    private fun fetchCars() {
        textStatus.visibility = View.VISIBLE
        textStatus.text = getString(R.string.loading_cars)
        recycler.visibility = View.GONE

        ApiClient.get("/cars", object : ApiClient.JsonCallback {
            override fun onSuccess(body: String) {
                val jsonArray = JSONArray(body)
                val cars = mutableListOf<Car>()
                for (i in 0 until jsonArray.length()) {
                    cars.add(Car.fromJson(jsonArray.getJSONObject(i)))
                }
                allCars = cars
                applyBrandFilter()
            }

            override fun onError(message: String) {
                textStatus.visibility = View.VISIBLE
                textStatus.text = message
                recycler.visibility = View.GONE
            }
        })
    }

    /** mirrors the client-side .filter((car) => car.brand.includes(brand)) in Cars.js */
    private fun applyBrandFilter() {
        val query = editBrand.text.toString().trim()

        val filtered = if (query.isEmpty()) {
            allCars
        } else {
            allCars.filter { it.brand.contains(query, ignoreCase = true) }
        }

        adapter.updateCars(filtered)

        if (filtered.isEmpty()) {
            textStatus.visibility = View.VISIBLE
            textStatus.text = getString(R.string.no_cars_found)
            recycler.visibility = View.GONE
        } else {
            textStatus.visibility = View.GONE
            recycler.visibility = View.VISIBLE
        }
    }
}
