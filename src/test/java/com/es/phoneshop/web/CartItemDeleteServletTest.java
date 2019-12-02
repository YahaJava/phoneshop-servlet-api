package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemDeleteServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ProductDao productDao;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private Product product;
    @InjectMocks
    private CartItemDeleteServlet servlet;

    @Before
    public void setup() {
        when(productDao.getProduct(anyString())).thenReturn(product);
        when(cartService.getCart(request)).thenReturn(cart);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        when(request.getParameter("productCode")).thenReturn("iphone");
        servlet.doPost(request, response);
        verify(cartService).delete(cart, product);
        verify(response).sendRedirect(request.getContextPath() + "/cart?delete=true");
    }
}