package com.example.mymobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvWelcomeTitle: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvMemberSince: TextView
    private lateinit var btnLogout: Button
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize preferences manager
        preferencesManager = PreferencesManager(this)

        // Check if user is logged in
        if (preferencesManager.getToken() == null) {
            navigateToLogin()
            return
        }

        // Initialize views
        tvWelcomeTitle = findViewById(R.id.tvWelcomeTitle)
        tvEmail = findViewById(R.id.tvEmail)
        tvUsername = findViewById(R.id.tvUsername)
        tvMemberSince = findViewById(R.id.tvMemberSince)
        btnLogout = findViewById(R.id.btnLogout)

        // Load user data
        loadUserData()

        // Set click listener for logout
        btnLogout.setOnClickListener {
            handleLogout()
        }
    }

    private fun loadUserData() {
        val username = preferencesManager.getUsername()
        val email = preferencesManager.getEmail()

        // Update welcome message
        tvWelcomeTitle.text = "Welcome, ${username ?: email}!"

        // Update profile info
        tvEmail.text = email ?: "N/A"
        tvUsername.text = username ?: "N/A"

        // Set member since date to current date
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        tvMemberSince.text = dateFormat.format(Date())
    }

    private fun handleLogout() {
        // Clear user data
        preferencesManager.clearAll()

        // Navigate to login
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
