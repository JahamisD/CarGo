package com.example.mobileapp.ui

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mobileapp.R
import com.example.mobileapp.session.SessionManager
import com.example.mobileapp.ui.auth.LoginActivity
import com.example.mobileapp.ui.auth.RegisterActivity
import com.example.mobileapp.ui.booking.MyBookingsActivity
import com.example.mobileapp.ui.cars.CarsActivity

/**
 * Shared top bar + navigation menu, the Kotlin equivalent of layout/Navbar.js.
 * Every customer-facing screen (except the details of a single flow) extends
 * this so the same "Browse Cars / My Bookings / Login / Register / Logout"
 * options are always available, with items shown/hidden based on session
 * state exactly like {user && (...)} did in the React navbar.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected fun setupToolbar(toolbar: Toolbar, title: String? = null) {
        setSupportActionBar(toolbar)
        if (title != null) {
            supportActionBar?.title = title
        }
        val user = SessionManager.getUser(this)
        if (user != null) {
            supportActionBar?.subtitle = getString(R.string.greeting, user.firstName)
        }
        if (this !is com.example.mobileapp.ui.cars.CarsActivity) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_nav, menu)

        val loggedIn = SessionManager.isLoggedIn(this)
        menu.findItem(R.id.menu_my_bookings)?.isVisible = loggedIn
        menu.findItem(R.id.menu_login)?.isVisible = !loggedIn
        menu.findItem(R.id.menu_register)?.isVisible = !loggedIn
        menu.findItem(R.id.menu_logout)?.isVisible = loggedIn

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.menu_browse_cars -> {
                startActivity(Intent(this, CarsActivity::class.java))
                true
            }
            R.id.menu_my_bookings -> {
                startActivity(Intent(this, MyBookingsActivity::class.java))
                true
            }
            R.id.menu_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                true
            }
            R.id.menu_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                true
            }
            R.id.menu_logout -> {
                SessionManager.logout(this)
                Toast.makeText(this, R.string.logged_out_successfully, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CarsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
