package com.example.bookstore;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    
    // NEW METHOD: Finds an existing cart item by user and book ID
    Optional<Cart> findByUserIdAndBookId(Long userId, Long bookId);
}
