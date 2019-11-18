package com.es.phoneshop.web;


import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPriceHistoryServletTest {

    private ArrayListProductDao productDao;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Product product;
    @Mock
    private ServletConfig config;

    private ProductPriceHistoryServlet servlet = new ProductPriceHistoryServlet();


    @Before
    public void setup() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        when(product.getCode()).thenReturn("sgs3");
        productDao.getAllProducts().clear();
        productDao.save(product);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.init(config);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/sgs3");
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetNotFound() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/notfound");
        servlet.doGet(request, response);
        verify(response).sendError(eq(404));
    }
}

