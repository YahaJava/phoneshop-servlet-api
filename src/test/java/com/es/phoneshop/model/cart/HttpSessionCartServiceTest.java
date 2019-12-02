package com.es.phoneshop.model.cart;


import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class HttpSessionCartServiceTest {
    private CartService cartService;

    private Cart cart;
    private CartItem cartItem;

    @Mock
    private Product product;

    @Mock
    private Product product1;


    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession httpSession;

    @Before
    public void setUp() {
        cartService = HttpSessionCartService.getInstance();
        when(product.getStock()).thenReturn(20);
        when(product.getPrice()).thenReturn(BigDecimal.TEN);
        when(product1.getStock()).thenReturn(20);
        when(product1.getPrice()).thenReturn(BigDecimal.TEN);
        cart = new Cart();
        cartItem = new CartItem(product, 5);
        List<CartItem> list = new ArrayList<>();
        list.add(cartItem);
        cart.setCartItems(list);
    }

    @Test
    public void testGetCart() {
        when(request.getSession()).thenReturn(httpSession);
        cartService.getCart(request);
        verify(request, times(2)).getSession();
    }

    @Test
    public void testAddExist() {
        cartService.add(cart, product, 10);
        assertSame(cartItem.getQuantity(), 15);
    }

    @Test
    public void testAddNoneExist() {
        cartService.add(cart, product1, 10);
        assertEquals(cart.getCartItems().size(), 2);
    }

    @Test(expected = OutOfStockException.class)
    public void testAddNoEnoughtStock() {
        cartService.add(cart, product1, 30);
    }

    @Test
    public void testUpdateSuccess() {
        cartService.update(cart, product, 20);
        assertSame(cartItem.getQuantity(), 20);
    }

    @Test(expected = OutOfStockException.class)
    public void testUpdateNoEnoughtStock() {
        cartService.update(cart, product, 30);
    }

    @Test
    public void testDelete() {
        cartService.delete(cart, product);
        assertSame(cart.getCartItems().size(), 0);
    }

}
