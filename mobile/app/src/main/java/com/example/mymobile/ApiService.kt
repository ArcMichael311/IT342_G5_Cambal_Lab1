package com.example.mymobile

import android.content.Context
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

data class AuthResponse(
    val token: String?,
    val userId: Long?,
    val username: String?,
    val email: String?,
    val message: String?
)

class ApiService(private val context: Context) {

    // Update this URL to match your backend server
    // For Android emulator, use 10.0.2.2 instead of localhost
    // For physical device, use your computer's IP address
    private val BASE_URL = "http://10.0.2.2:8080/api/auth"

    fun register(username: String, email: String, password: String): AuthResponse? {
        return try {
            val url = URL("$BASE_URL/register")
            val connection = url.openConnection() as HttpURLConnection
            
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.doInput = true

            // Create JSON request body
            val jsonBody = JSONObject()
            jsonBody.put("username", username)
            jsonBody.put("email", email)
            jsonBody.put("password", password)

            // Write request body
            val writer = OutputStreamWriter(connection.outputStream)
            writer.write(jsonBody.toString())
            writer.flush()
            writer.close()

            // Read response
            val responseCode = connection.responseCode
            val inputStream = if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream
            } else {
                connection.errorStream
            }

            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            reader.close()

            // Parse response
            val jsonResponse = JSONObject(response.toString())
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                AuthResponse(
                    token = jsonResponse.optString("token"),
                    userId = jsonResponse.optLong("userId"),
                    username = jsonResponse.optString("username"),
                    email = jsonResponse.optString("email"),
                    message = null
                )
            } else {
                AuthResponse(
                    token = null,
                    userId = null,
                    username = null,
                    email = null,
                    message = jsonResponse.optString("message", "Registration failed")
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResponse(
                token = null,
                userId = null,
                username = null,
                email = null,
                message = "Network error: ${e.message}"
            )
        }
    }

    fun login(email: String, password: String): AuthResponse? {
        return try {
            val url = URL("$BASE_URL/login")
            val connection = url.openConnection() as HttpURLConnection
            
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.doInput = true

            // Create JSON request body
            val jsonBody = JSONObject()
            jsonBody.put("email", email)
            jsonBody.put("password", password)

            // Write request body
            val writer = OutputStreamWriter(connection.outputStream)
            writer.write(jsonBody.toString())
            writer.flush()
            writer.close()

            // Read response
            val responseCode = connection.responseCode
            val inputStream = if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream
            } else {
                connection.errorStream
            }

            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            reader.close()

            // Parse response
            val jsonResponse = JSONObject(response.toString())
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                AuthResponse(
                    token = jsonResponse.optString("token"),
                    userId = jsonResponse.optLong("userId"),
                    username = jsonResponse.optString("username"),
                    email = jsonResponse.optString("email"),
                    message = null
                )
            } else {
                AuthResponse(
                    token = null,
                    userId = null,
                    username = null,
                    email = null,
                    message = jsonResponse.optString("message", "Login failed")
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResponse(
                token = null,
                userId = null,
                username = null,
                email = null,
                message = "Network error: ${e.message}"
            )
        }
    }
}
