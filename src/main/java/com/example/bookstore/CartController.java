package com.example.bookstore;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    
    // 1. INJECT THE SERVICE LAYER (REPLACES CartRepository)
    @Autowired
    private CartService cartService;

    @Autowired
    private BookRepository bookRepository; // Still needed to fetch book details

    private static final Long GUEST_USER_ID = 1L; 

    private Long getUserId() {
        // NOTE: In a real app, this would get the ID from the Principal/SecurityContext
        return GUEST_USER_ID; 
    }
    
    // --- 1. /addToCart (POST) ---
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam Long bookId, Principal principal) { 
        Long userId = getUserId();
        // CALL SERVICE METHOD
        cartService.addToCart(userId, bookId, 1); 
        return "redirect:/cart"; 
    }
    
    // --- 2. /buyNow (POST) ---
    @PostMapping("/buyNow")
    public String buyNow(@RequestParam Long bookId, Principal principal) {
        Long userId = getUserId();
        // CALL SERVICE METHOD
        cartService.addToCart(userId, bookId, 1); 
        return "redirect:/cart"; 
    }

    // --- 3. /removeFromCart (POST) - Fixes the 404 Not Found error ---
    // Maps the missing endpoint and uses the CartService method to delete the cart item.
    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam Long cartId) {
        cartService.removeFromCart(cartId);
        return "redirect:/cart";
    }

    // --- 4. /cart (GET) ---
    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal) {
        
        Long userId = getUserId();
        
        // CALL SERVICE METHOD TO GET CART ITEMS
        List<Cart> carts = cartService.getCartItems(userId); // Uses CartService
        List<CartItemView> viewItems = new ArrayList<>(); 
        
        double subtotal = 0.0;
        int totalItems = 0;

        for (Cart cart : carts) {
            Optional<Book> bookOpt = bookRepository.findById(cart.getBookId());
            
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                CartItemView viewItem = new CartItemView(cart, book); 
                viewItems.add(viewItem);
                
                subtotal += viewItem.getItemTotal();
                totalItems += cart.getQuantity();
            } else {
                System.err.println("Cart item skipped: Book ID " + cart.getBookId() + " not found.");
            }
        }

        model.addAttribute("cartItems", viewItems); 
        model.addAttribute("totalItems", totalItems); 
        model.addAttribute("subtotal", subtotal);
        
        return "cart"; 
    }
}
