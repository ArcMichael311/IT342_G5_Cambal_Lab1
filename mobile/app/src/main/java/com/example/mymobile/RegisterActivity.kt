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

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvError: TextView
    private lateinit var tvLoginLink: TextView
    private lateinit var apiService: ApiService
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize views
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvError = findViewById(R.id.tvError)
        tvLoginLink = findViewById(R.id.tvLoginLinkClickable)

        // Initialize services
        apiService = ApiService(this)
        preferencesManager = PreferencesManager(this)

        // Set click listeners
        btnRegister.setOnClickListener {
            handleRegister()
        }

        tvLoginLink.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun handleRegister() {
        // Clear previous error
        tvError.visibility = View.GONE
        tvError.text = ""

        // Get input values
        val username = etUsername.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // Validate inputs
        when {
            username.isEmpty() -> {
                showError("Please enter a username")
                return
            }
            email.isEmpty() -> {
                showError("Please enter an email")
                return
            }
            password.isEmpty() -> {
                showError("Please enter a password")
                return
            }
            password != confirmPassword -> {
                showError("Passwords do not match")
                return
            }
            password.length < 6 -> {
                showError("Password must be at least 6 characters")
                return
            }
        }

        // Disable button while loading
        btnRegister.isEnabled = false
        btnRegister.text = "Registering..."

        // Make API call
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.register(username, email, password)
                
                withContext(Dispatchers.Main) {
                    if (response != null && response.token != null) {
                        // Save user data
                        preferencesManager.saveToken(response.token)
                        preferencesManager.saveUser(
                            response.userId ?: 0,
                            response.username ?: username,
                            response.email ?: email
                        )

                        // Navigate to dashboard
                        val intent = Intent(this@RegisterActivity, DashboardActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        showError(response?.message ?: "Registration failed")
                        btnRegister.isEnabled = true
                        btnRegister.text = "Register"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("Failed to connect to server: ${e.message}")
                    btnRegister.isEnabled = true
                    btnRegister.text = "Register"
                }
            }
        }
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
