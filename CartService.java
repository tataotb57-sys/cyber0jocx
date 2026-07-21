package com.mycompany.cyberjocx;

public class CartService {

    private Cart cart = new Cart();

    public void addToCart(Book book) {

        cart.add(book);
    }

    public Cart getCart() {

        return cart;
    }

    public void clearCart() {

        cart.clear();
    }
}
