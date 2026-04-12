package com.example.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

  
    public void addToCart(Long userId, Long bookId, int quantity) {
      
        Optional<Cart> existingCartItem = cartRepository.findByUserIdAndBookId(userId, bookId);

        if (existingCartItem.isPresent()) {
         
            Cart cart = existingCartItem.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            cartRepository.save(cart);
        } else {
          
            Cart cart = new Cart();
            cart.setBookId(bookId);
            cart.setUserId(userId); 
            cart.setQuantity(quantity);
            cartRepository.save(cart);
        }
    }

    public List<Cart> getCartItems(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void removeFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }
}
