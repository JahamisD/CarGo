package com.example.mobileapp.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mobileapp.R
import com.example.mobileapp.network.ApiClient
import org.json.JSONObject

/** Kotlin port of page/auth/Register.js */
class RegisterActivity : AppCompatActivity() {

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var textError: TextView
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        editFirstName = findViewById(R.id.editFirstName)
        editLastName = findViewById(R.id.editLastName)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        textError = findViewById(R.id.textError)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener { submitRegister() }

        findViewById<TextView>(R.id.textGoLogin).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun submitRegister() {
        hideError()

        val firstName = editFirstName.text.toString().trim()
        val lastName = editLastName.text.toString().trim()
        val email = editEmail.text.toString().trim()
        val password = editPassword.text.toString()

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showError(getString(R.string.register_failed))
            return
        }

        val requestBody = JSONObject()
        requestBody.put("firstName", firstName)
        requestBody.put("lastName", lastName)
        requestBody.put("email", email)
        requestBody.put("password", password)

        btnRegister.isEnabled = false

        ApiClient.post("/auth/register", requestBody, object : ApiClient.JsonCallback {
            override fun onSuccess(body: String) {
                btnRegister.isEnabled = true
                // registered -> go back to Login, same as navigate('/login') in Register.js
                finish()
            }

            override fun onError(message: String) {
                btnRegister.isEnabled = true
                showError(getString(R.string.register_failed))
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
