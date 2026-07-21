package com.example.mobileapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mobileapp.R
import com.example.mobileapp.model.User
import com.example.mobileapp.network.ApiClient
import com.example.mobileapp.session.SessionManager
import com.example.mobileapp.ui.cars.CarsActivity
import org.json.JSONObject

/**
 * Kotlin port of page/auth/Login.js. Not a BaseActivity/toolbar-menu screen
 * since it's a standalone form, same as the React page renders without
 * needing the navbar's own menu items active on itself.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var textError: TextView
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        textError = findViewById(R.id.textError)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener { submitLogin() }

        findViewById<TextView>(R.id.textGoRegister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun submitLogin() {
        hideError()

        val email = editEmail.text.toString().trim()
        val password = editPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            showError(getString(R.string.wrong_credentials))
            return
        }

        val requestBody = JSONObject()
        requestBody.put("email", email)
        requestBody.put("password", password)

        btnLogin.isEnabled = false

        ApiClient.post("/auth/login", requestBody, object : ApiClient.JsonCallback {
            override fun onSuccess(body: String) {
                btnLogin.isEnabled = true
                try {
                    val json = JSONObject(body)
                    val user = User.fromJson(json.getJSONObject("user"))
                    SessionManager.saveUser(this@LoginActivity, user)

                    val intent = Intent(this@LoginActivity, CarsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } catch (e: Exception) {
                    showError(getString(R.string.wrong_credentials))
                }
            }

            override fun onError(message: String) {
                btnLogin.isEnabled = true
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
