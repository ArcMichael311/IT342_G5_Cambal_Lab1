# IT342 G5 Cambal Lab1 - Setup Guide

## Project Overview
This is a full-stack application with:
- **Backend**: Spring Boot (Java) with MySQL database
- **Web**: React.js frontend
- **Mobile**: Android app (Kotlin) with RelativeLayout

## Database Setup (MySQL Workbench)

### 1. Create Database
Open MySQL Workbench and run:
```sql
CREATE DATABASE dbit342_g5_cambal;
USE dbit342_g5_cambal;
```

### 2. Configure Database Connection
The backend is already configured to connect to MySQL in `application.properties`:
- **Database**: dbit342_g5_cambal
- **Username**: root
- **Password**: 123456
- **Port**: 3306

**Important**: If your MySQL username or password is different, update the file:
`backend/src/main/resources/application.properties`

### 3. Auto-Create Tables
The application uses Hibernate with `ddl-auto=update`, so the `users` table will be created automatically when you run the backend.

## Running the Application

### Backend (Spring Boot)
1. Open terminal in the project root
2. Navigate to backend folder:
   ```bash
   cd backend
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```
4. Backend will start on `http://localhost:8080`

### Web (React)
1. Open a new terminal
2. Navigate to web folder:
   ```bash
   cd web
   ```
3. Install dependencies (first time only):
   ```bash
   npm install
   ```
4. Start the development server:
   ```bash
   npm start
   ```
5. Web app will open at `http://localhost:3000`

### Mobile (Android)
1. Open the `mobile` folder in Android Studio
2. Sync Gradle files
3. Connect an Android device or start an emulator
4. **Important**: Update the API URL in `ApiService.kt`:
   - For **Emulator**: Use `http://10.0.2.2:8080/api/auth` (already configured)
   - For **Physical Device**: Use your computer's IP address (e.g., `http://192.168.1.100:8080/api/auth`)
5. Run the app

## Features

### Web & Mobile Features
✅ **Register** - Create new account with username, email, and password
✅ **Login** - Sign in with email and password
✅ **Dashboard** - View user profile information
✅ **Logout** - Sign out and clear session

### Mobile UI/UX
- Uses **RelativeLayout** for all screens
- Matches web design with dark red theme (#4a0000)
- Same input validations as web
- Persistent user session using SharedPreferences

## API Endpoints

### POST /api/auth/register
Request:
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123"
}
```

Response:
```json
{
  "token": "jwt-token-here",
  "message": "User registered successfully",
  "userId": 1,
  "username": "johndoe",
  "email": "john@example.com"
}
```

### POST /api/auth/login
Request:
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

Response:
```json
{
  "token": "jwt-token-here",
  "message": "Login successful",
  "userId": 1,
  "username": "johndoe",
  "email": "john@example.com"
}
```

## Troubleshooting

### MySQL Connection Issues
- Ensure MySQL is running
- Verify credentials in `application.properties`
- Check if database `dbit342_g5_cambal` exists

### Mobile Cannot Connect to Backend
- Make sure backend is running on port 8080
- For emulator: Use `http://10.0.2.2:8080`
- For physical device: 
  1. Find your computer's local IP (e.g., `ipconfig` on Windows, `ifconfig` on Mac/Linux)
  2. Update `BASE_URL` in `ApiService.kt`
  3. Ensure phone and computer are on the same network

### Port Already in Use
If port 8080 is already in use, change it in `application.properties`:
```properties
server.port=8081
```
Then update the API URL in web and mobile apps.

## Project Structure

```
IT342_G5_Cambal_Lab1/
├── backend/               # Spring Boot backend
│   └── src/main/
│       ├── java/backend/g5/
│       │   ├── controller/    # REST controllers
│       │   ├── entity/        # JPA entities
│       │   ├── repository/    # Data repositories
│       │   ├── service/       # Business logic
│       │   ├── dto/           # Data transfer objects
│       │   └── config/        # Security configuration
│       └── resources/
│           └── application.properties  # Database config
│
├── web/                   # React.js frontend
│   └── src/
│       └── components/
│           ├── Login.js
│           ├── Register.js
│           └── Dashboard.js
│
└── mobile/                # Android app
    └── app/src/main/
        ├── java/com/example/mymobile/
        │   ├── LoginActivity.kt
        │   ├── RegisterActivity.kt
        │   ├── DashboardActivity.kt
        │   ├── ApiService.kt
        │   └── PreferencesManager.kt
        └── res/layout/
            ├── activity_login.xml
            ├── activity_register.xml
            └── activity_dashboard.xml
```

## Color Scheme
- Primary: #4a0000 (Dark Red)
- Primary Dark: #6b0000 (Darker Red)
- Background: #F9F9F9 (Light Gray)
- White: #FFFFFF

## Technologies Used
- **Backend**: Spring Boot, Spring Security, JWT, JPA/Hibernate, MySQL
- **Web**: React.js, CSS
- **Mobile**: Kotlin, Android SDK, RelativeLayout, Coroutines
