import React, { useState, useEffect } from 'react';
import './Dashboard.css';

function Dashboard() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    // Check if user is logged in
    const token = localStorage.getItem('token');
    const userData = localStorage.getItem('user');

    if (!token) {
      // Redirect to login if no token
      window.location.href = '/login';
      return;
    }

    if (userData) {
      setUser(JSON.parse(userData));
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/login';
  };

  if (!user) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <h1>Dashboard</h1>
        <button onClick={handleLogout} className="btn-logout">
          Logout
        </button>
      </header>

      <div className="dashboard-content">
        <div className="welcome-card">
          <h2>Welcome, {user.username || user.email}!</h2>
          <p>You have successfully logged in to your account.</p>
        </div>

        <div className="stats-grid">
          <div className="stat-card">
            <h3>Profile</h3>
            <div className="stat-info">
              <p><strong>Email:</strong> {user.email}</p>
              <p><strong>Username:</strong> {user.username || 'N/A'}</p>
              <p><strong>Member Since:</strong> {new Date().toLocaleDateString()}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
