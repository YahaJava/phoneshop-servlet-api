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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private Product product;
    @InjectMocks
    private CartPageServlet servlet;

    @Before
    public void setup() {
        when(productDao.getProduct(anyString())).thenReturn(product);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getRequestDispatcher("/WEB-INF/pages/cart.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSuccess() throws ServletException, IOException {
        String[] productCodes = {"sgs", "sgs3","apple"};
        String[] quantitiesStrings = {"15", "4","1"};
        when(request.getParameterValues("productCode")).thenReturn(productCodes);
        when(request.getParameterValues("quantity")).thenReturn(quantitiesStrings);
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        servlet.doPost(request, response);
        verify(response).sendRedirect(request.getRequestURI() + "?update=true");
    }

    @Test
    public void testDoPostFail() throws ServletException, IOException {
        String[] productCodes = {"sgs", "sgs3","apple"};
        String[] quantitiesStrings = {"1000", "gdgdfgdgf","0"};
        when(request.getParameterValues("productCode")).thenReturn(productCodes);
        when(request.getParameterValues("quantity")).thenReturn(quantitiesStrings);
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("error"), any(Map.class));
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }
}