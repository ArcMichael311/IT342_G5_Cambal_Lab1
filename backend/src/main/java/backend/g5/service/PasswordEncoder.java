package backend.g5.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public PasswordEncoder() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }
    
    /**
     * Encode the raw password
     */
    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
    
    /**
     * Check if the raw password matches the encoded hash
     */
    public boolean check(String password, String hash) {
        return bCryptPasswordEncoder.matches(password, hash);
    }
}
