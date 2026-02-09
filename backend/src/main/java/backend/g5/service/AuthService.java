package backend.g5.service;

import backend.g5.dto.AuthResponse;
import backend.g5.dto.LoginRequest;
import backend.g5.dto.RegisterRequest;
import backend.g5.entity.User;
import backend.g5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private TokenProvider tokenProvider;
    
    // In-memory token blacklist for logout
    private Map<String, Boolean> tokenBlacklist = new HashMap<>();
    
    /**
     * Register a new user
     */
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, "Email already exists", null, null, null);
        }
        
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse(null, "Username already exists", null, null, null);
        }
        
        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        
        // Save user
        User savedUser = userRepository.save(user);
        
        // Generate token
        String token = tokenProvider.generateToken(savedUser);
        
        return new AuthResponse(
                token,
                "User registered successfully",
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }
    
    /**
     * Login user
     */
    public AuthResponse login(LoginRequest request) {
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        
        if (userOptional.isEmpty()) {
            return new AuthResponse(null, "Invalid email or password", null, null, null);
        }
        
        User user = userOptional.get();
        
        // Check password
        if (!encoder.check(request.getPassword(), user.getPassword())) {
            return new AuthResponse(null, "Invalid email or password", null, null, null);
        }
        
        // Generate token
        String token = tokenProvider.generateToken(user);
        
        return new AuthResponse(
                token,
                "Login successful",
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
    
    /**
     * Logout user by blacklisting token
     */
    public Map<String, String> logout(String token) {
        Map<String, String> response = new HashMap<>();
        
        if (token != null && tokenProvider.validateToken(token)) {
            tokenBlacklist.put(token, true);
            response.put("message", "Logout successful");
            return response;
        }
        
        response.put("message", "Invalid token");
        return response;
    }
    
    /**
     * Check if token is blacklisted
     */
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.containsKey(token);
    }
}
