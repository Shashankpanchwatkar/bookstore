package com.example.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    /**
     * Adds an item to the cart. If the item already exists for the user, 
     * the quantity is incremented. Otherwise, a new cart entry is created.
     */
    public void addToCart(Long userId, Long bookId, int quantity) {
        // 1. Check if the item already exists in the user's cart
        // This uses the NEW method from the CartRepository in the Canvas.
        Optional<Cart> existingCartItem = cartRepository.findByUserIdAndBookId(userId, bookId);

        if (existingCartItem.isPresent()) {
            // 2. Item exists: Increment the quantity and save
            Cart cart = existingCartItem.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            cartRepository.save(cart);
        } else {
            // 3. Item is new: Create a new cart entry and save
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
