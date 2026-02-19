package com.example.mymobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check if user is logged in
        val preferencesManager = PreferencesManager(this)
        
        if (preferencesManager.getToken() != null) {
            // User is logged in, go to Dashboard
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        } else {
            // User is not logged in, go to Login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        
        finish()
    }
}