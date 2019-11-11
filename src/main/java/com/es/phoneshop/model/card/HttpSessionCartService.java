package com.es.phoneshop.model.card;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class HttpSessionCartService implements CartService {
    private static final String CART_ATRIBUTE = CartService.class + ".cart";
    private static CartService instance;

    private Cart cart;

    private HttpSessionCartService() {
        cart = new Cart();
    }

    public static CartService getInstance() {
        if (instance == null) {
            instance = new HttpSessionCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_ATRIBUTE);
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute(CART_ATRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, Product product, int quantity) {
        if (quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
        cart.getCartItems().add(new CartItem(product, quantity));
        recalculate(cart);
    }

    private void recalculate(Cart cart) {
        cart.setTotalQuantity(cart.getCartItems().stream()
                .mapToInt(item -> item.getQuantity())
                .sum());
        cart.setTotalPrice(new BigDecimal(cart.getCartItems().stream()
                .mapToInt(item -> item.getProduct().getPrice().intValue() * item.getQuantity())
                .sum()));
    }
}