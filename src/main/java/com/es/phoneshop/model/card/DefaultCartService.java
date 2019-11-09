package com.es.phoneshop.model.card;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

public class DefaultCartService implements CartService
{
    private static CartService instance;

    private Cart cart;

    private DefaultCartService() {
        cart = new Cart();
    }

    public static synchronized CartService getInstance() {
        if (instance == null) {
            instance = new DefaultCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void add(Product product, int quantity) {
        if (quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
        Cart cart = this.getCart();
        getCart().getCartItems().add(new CartItem(product, quantity));
        recalculate(cart);
    }

    private void recalculate(Cart cart) {
        cart.setTotalQuantity(cart.getCartItems().stream().mapToInt(item -> item.getQuantity()).sum());
    }
}