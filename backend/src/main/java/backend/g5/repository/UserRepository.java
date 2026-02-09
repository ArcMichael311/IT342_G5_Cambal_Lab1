package backend.g5.repository;

import backend.g5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findById(Long id);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
}
