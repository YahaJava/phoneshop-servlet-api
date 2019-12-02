package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART_ATRIBUTE = CartService.class + ".cart";


    private HttpSessionCartService() {
    }

    public static CartService getInstance() {
        return HttpSessionCartServiceHolder.httpSessionCartService;
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
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
        Optional<CartItem> optionalCartItem = findItem(cart, product);
        CartItem cartItem = optionalCartItem.orElse(new CartItem(product, 0));
        if (cartItem.getQuantity() + quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        if (!optionalCartItem.isPresent()) {
            cart.getCartItems().add(cartItem);
        }
        recalculate(cart);
    }

    @Override
    public void update(Cart cart, Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
        Optional<CartItem> cartItem = findItem(cart, product);
        cartItem.ifPresent(item -> item.setQuantity(quantity));
        recalculate(cart);
    }

    @Override
    public void delete(Cart cart, Product product) {
        Optional<CartItem> cartItem = findItem(cart, product);
        cartItem.ifPresent(item -> cart.getCartItems().remove(item));
        recalculate(cart);
    }

    private Optional<CartItem> findItem(Cart cart, Product product) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();
    }

    private void recalculate(Cart cart) {
        cart.setTotalQuantity(cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum());
        cart.setTotalPrice(new BigDecimal(cart.getCartItems().stream()
                .mapToInt(item -> item.getProduct().getPrice().intValue() * item.getQuantity())
                .sum()));
    }

    private static class HttpSessionCartServiceHolder {
        static final HttpSessionCartService httpSessionCartService = new HttpSessionCartService();
    }

}