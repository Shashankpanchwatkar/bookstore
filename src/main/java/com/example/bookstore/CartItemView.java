package com.example.bookstore;


public class CartItemView {
    private final Cart cart;
    private final Book book;
    private final Double itemTotal;

    public CartItemView(Cart cart, Book book) {
        this.cart = cart;
        this.book = book;
        
       
        Double price = book.getPrice();
        Integer quantity = cart.getQuantity();
        
        
        this.itemTotal = (price != null && quantity != null) 
                         ? price * quantity : 0.0;
    }

  
    public Cart getCart() { return cart; }
    public Book getBook() { return book; }
    public Double getItemTotal() { return itemTotal; }
}
