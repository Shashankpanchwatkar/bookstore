package com.example.bookstore;

// This class combines Cart data and Book data for the view layer
public class CartItemView {
    private final Cart cart;
    private final Book book;
    private final Double itemTotal;

    public CartItemView(Cart cart, Book book) {
        this.cart = cart;
        this.book = book;
        
        // CRITICAL FIX: Explicitly check object getters for null before multiplication.
        Double price = book.getPrice();
        Integer quantity = cart.getQuantity();
        
        // If either price or quantity is null, the item total is zero.
        this.itemTotal = (price != null && quantity != null) 
                         ? price * quantity : 0.0;
    }

    // Getters for Thymeleaf access
    public Cart getCart() { return cart; }
    public Book getBook() { return book; }
    public Double getItemTotal() { return itemTotal; }
}
