package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MiniCartServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;

    private MiniCartServlet servlet = new MiniCartServlet();

    @Before
    public void setUp() throws ServletException {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.init(config);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);;
        verify(request).setAttribute(eq("cart"), any(Cart.class));
        verify(requestDispatcher).include(request, response);
    }
    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("cart"), any(Cart.class));
        verify(requestDispatcher).include(request, response);
    }
}