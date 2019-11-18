package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART_ATRIBUTE = CartService.class + ".cart";

    private Cart cart;

    private HttpSessionCartService() {
        cart = new Cart();
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
        Optional<CartItem> cartItem = findItem(cart, product);
        if (cartItem.isPresent()) {
            checkEnoughtStock1(quantity,product);
            checkEnoughtStock2(cartItem,quantity,product);
            cartItem.get().setQuantity(cartItem.get().getQuantity() + quantity);
        } else {
            checkEnoughtStock1(quantity,product);
            cart.getCartItems().add(new CartItem(product, quantity));
        }
        recalculate(cart);
    }

    private void checkEnoughtStock1(int quantity,Product product){
        if (quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
    }

    private void checkEnoughtStock2(Optional<CartItem> cartItem,int quantity,Product product){
        if (cartItem.get().getQuantity() + quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
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