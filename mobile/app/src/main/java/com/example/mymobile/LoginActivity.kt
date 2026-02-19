package com.example.mymobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvError: TextView
    private lateinit var tvRegisterLink: TextView
    private lateinit var apiService: ApiService
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvError = findViewById(R.id.tvError)
        tvRegisterLink = findViewById(R.id.tvRegisterLink)

        // Initialize services
        apiService = ApiService(this)
        preferencesManager = PreferencesManager(this)

        // Check if user is already logged in
        if (preferencesManager.getToken() != null) {
            navigateToDashboard()
            return
        }

        // Set click listeners
        btnLogin.setOnClickListener {
            handleLogin()
        }

        tvRegisterLink.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun handleLogin() {
        // Clear previous error
        tvError.visibility = View.GONE
        tvError.text = ""

        // Get input values
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        // Validate inputs
        when {
            email.isEmpty() -> {
                showError("Please enter your email")
                return
            }
            password.isEmpty() -> {
                showError("Please enter your password")
                return
            }
        }

        // Disable button while loading
        btnLogin.isEnabled = false
        btnLogin.text = "Logging in..."

        // Make API call
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.login(email, password)
                
                withContext(Dispatchers.Main) {
                    if (response != null && response.token != null) {
                        // Save user data
                        preferencesManager.saveToken(response.token)
                        preferencesManager.saveUser(
                            response.userId ?: 0,
                            response.username ?: "",
                            response.email ?: email
                        )

                        // Navigate to dashboard
                        navigateToDashboard()
                    } else {
                        showError(response?.message ?: "Login failed")
                        btnLogin.isEnabled = true
                        btnLogin.text = "Login"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("Failed to connect to server: ${e.message}")
                    btnLogin.isEnabled = true
                    btnLogin.text = "Login"
                }
            }
        }
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}
