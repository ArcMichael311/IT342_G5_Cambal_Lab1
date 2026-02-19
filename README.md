# IT342_G5_Cambal_Lab1

Full-stack Authentication System with Web and Mobile Applications

## Project Components

1. **Backend** - Spring Boot REST API with MySQL database
2. **Web** - React.js frontend application  
3. **Mobile** - Android app with RelativeLayout UI

## Quick Start

### Prerequisites
- Java 17 or higher
- Node.js and npm
- MySQL Server 8.0
- Android Studio (for mobile)

### Setup Instructions

Detailed setup instructions available in [docs/SETUP_GUIDE.md](docs/SETUP_GUIDE.md)

**Quick Setup:**

1. **Database**: Create MySQL database `dbit342_g5_cambal`
2. **Backend**: Run `cd backend && ./mvnw spring-boot:run`
3. **Web**: Run `cd web && npm install && npm start`
4. **Mobile**: Open in Android Studio and run

## Features

- User Registration with validation
- User Login with JWT authentication
- User Dashboard with profile information
- Session management
- MySQL database integration
- Responsive web design
- Material Design mobile UI with RelativeLayout

## Technology Stack

**Backend:**
- Spring Boot 4.0.2
- Spring Security
- Spring Data JPA
- MySQL 8.0
- JWT Authentication

**Web:**
- React.js
- CSS3
- Fetch API

**Mobile:**
- Kotlin
- Android SDK
- RelativeLayout
- Coroutines
- SharedPreferences

## Database Configuration

Database: `dbit342_g5_cambal`
- Username: `root`
- Password: `123456`
- Port: `3306`

Tables are auto-created by Hibernate.

## API Endpoints

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout

## Team

Group 5 - Cambal
- Developer: Michael Raymund B. Cambal

## License

Educational Project - IT342
