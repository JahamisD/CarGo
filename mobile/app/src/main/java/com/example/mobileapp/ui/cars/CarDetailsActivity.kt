package com.example.mobileapp.ui.cars

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.mobileapp.R
import com.example.mobileapp.model.Car
import com.example.mobileapp.network.ApiClient
import com.example.mobileapp.session.SessionManager
import com.example.mobileapp.ui.BaseActivity
import com.example.mobileapp.ui.auth.LoginActivity
import com.example.mobileapp.util.ImageLoader
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Kotlin port of page/cars/CarDetails.js: loads one car by id, then lets a
 * logged-in user pick a start/end date and submit a booking.
 */
class CarDetailsActivity : BaseActivity() {

    companion object {
        const val EXTRA_CAR_ID = "extra_car_id"
    }

    private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private lateinit var progress: View
    private lateinit var scrollContent: ScrollView
    private lateinit var imageCar: ImageView
    private lateinit var textTitle: TextView
    private lateinit var textBrand: TextView
    private lateinit var textModel: TextView
    private lateinit var textYear: TextView
    private lateinit var textDetails: TextView
    private lateinit var textPrice: TextView
    private lateinit var textError: TextView
    private lateinit var textStartDate: TextView
    private lateinit var textEndDate: TextView
    private lateinit var textTotal: TextView
    private lateinit var btnConfirmBooking: Button

    private var car: Car? = null
    private val startCalendar: Calendar = Calendar.getInstance()
    private val endCalendar: Calendar = Calendar.getInstance()
    private var startDateString: String? = null
    private var endDateString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        setupToolbar(findViewById<Toolbar>(R.id.toolbar), getString(R.string.title_car_details))

        progress = findViewById(R.id.progressLoading)
        scrollContent = findViewById(R.id.scrollContent)
        imageCar = findViewById(R.id.imageCar)
        textTitle = findViewById(R.id.textTitle)
        textBrand = findViewById(R.id.textBrand)
        textModel = findViewById(R.id.textModel)
        textYear = findViewById(R.id.textYear)
        textDetails = findViewById(R.id.textDetails)
        textPrice = findViewById(R.id.textPrice)
        textError = findViewById(R.id.textError)
        textStartDate = findViewById(R.id.textStartDate)
        textEndDate = findViewById(R.id.textEndDate)
        textTotal = findViewById(R.id.textTotal)
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking)

        textStartDate.setOnClickListener { showDatePicker(isStart = true) }
        textEndDate.setOnClickListener { showDatePicker(isStart = false) }
        btnConfirmBooking.setOnClickListener { bookCar() }

        val carId = intent.getIntExtra(EXTRA_CAR_ID, -1)
        if (carId == -1) {
            Toast.makeText(this, "Missing car id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        fetchCar(carId)
    }

    private fun fetchCar(carId: Int) {
        progress.visibility = View.VISIBLE
        scrollContent.visibility = View.GONE

        ApiClient.get("/cars/$carId", object : ApiClient.JsonCallback {
            override fun onSuccess(body: String) {
                car = Car.fromJson(JSONObject(body))
                bindCar()
                progress.visibility = View.GONE
                scrollContent.visibility = View.VISIBLE
            }

            override fun onError(message: String) {
                progress.visibility = View.GONE
                Toast.makeText(this@CarDetailsActivity, message, Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun bindCar() {
        val c = car ?: return
        ImageLoader.load(imageCar, c.imageUrl, R.drawable.ic_car_placeholder)
        textTitle.text = getString(R.string.brand_label_short, c.brand, c.model)
        textBrand.text = getString(R.string.brand_label, c.brand)
        textModel.text = getString(R.string.model_label, c.model)
        textYear.text = getString(R.string.year_label, c.year)
        textDetails.text = getString(R.string.details_label, c.details)
        textPrice.text = getString(R.string.price_label, "$${c.pricePerDay}")
    }

    private fun showDatePicker(isStart: Boolean) {
        val calendar = if (isStart) startCalendar else endCalendar

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth, 0, 0, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val formatted = apiDateFormat.format(calendar.time)

                if (isStart) {
                    startDateString = formatted
                    textStartDate.text = formatted
                } else {
                    endDateString = formatted
                    textEndDate.text = formatted
                }

                recalculateTotal()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    /** mirrors the days/total calculation in CarDetails.js */
    private fun recalculateTotal() {
        val c = car ?: return
        val start = startDateString
        val end = endDateString

        if (start == null || end == null) {
            textTotal.visibility = View.GONE
            return
        }

        val startMillis = apiDateFormat.parse(start)?.time ?: return
        val endMillis = apiDateFormat.parse(end)?.time ?: return
        var days = TimeUnit.MILLISECONDS.toDays(endMillis - startMillis)
        if (days < 1) days = 0

        if (days > 0) {
            val total = days * c.pricePerDay
            textTotal.visibility = View.VISIBLE
            textTotal.text = "$days day(s) × $${c.pricePerDay} = $${total}"
        } else {
            textTotal.visibility = View.GONE
        }
    }

    private fun bookCar() {
        hideError()
        val c = car ?: return

        val user = SessionManager.getUser(this)
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }

        val start = startDateString
        val end = endDateString
        if (start == null || end == null) {
            showError(getString(R.string.pick_valid_dates))
            return
        }

        val startMillis = apiDateFormat.parse(start)?.time ?: 0
        val endMillis = apiDateFormat.parse(end)?.time ?: 0
        val days = TimeUnit.MILLISECONDS.toDays(endMillis - startMillis)
        if (days <= 0) {
            showError(getString(R.string.pick_valid_dates))
            return
        }

        val requestBody = JSONObject()
        requestBody.put("customerId", user.userId)
        requestBody.put("carId", c.carId)
        requestBody.put("startDate", start)
        requestBody.put("endDate", end)

        btnConfirmBooking.isEnabled = false

        ApiClient.post("/bookings", requestBody, object : ApiClient.JsonCallback {
            override fun onSuccess(body: String) {
                btnConfirmBooking.isEnabled = true
                Toast.makeText(this@CarDetailsActivity, R.string.booked_successfully, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@CarDetailsActivity, CarsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

            override fun onError(message: String) {
                btnConfirmBooking.isEnabled = true
                showError(message)
            }
        })
    }

    private fun showError(message: String) {
        textError.text = message
        textError.visibility = View.VISIBLE
    }

    private fun hideError() {
        textError.visibility = View.GONE
    }
}
