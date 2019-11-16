
package com.es.phoneshop.model.cart;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems;
    private int totalQuantity;
    private BigDecimal totalPrice;

    public Cart(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Cart() {
        this.cartItems = new ArrayList<CartItem>();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}

