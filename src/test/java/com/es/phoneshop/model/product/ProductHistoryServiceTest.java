package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductHistoryServiceTest {
    private ProductHistoryService productHistoryService;
    private List<Product> recentProducts = new ArrayList<>();

    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession httpSession;

    @Before
    public void setUp() {
        productHistoryService = ProductHistoryService.getInstance();
        when(request.getSession()).thenReturn(httpSession);
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);
        when(product3.getId()).thenReturn(4L);
        ArrayList<Product> list = new ArrayList<>();
        list.add(product1);
        productHistoryService.setRecentProducts(list);
    }

    @Test
    public void testGetRecentProducts() {
        productHistoryService.getRecentProducts(request);
        verify(request, times(2)).getSession();
    }

    @Test
    public void testSave() {
        productHistoryService.save(product2, request);
        productHistoryService.save(product3, request);
        recentProducts = productHistoryService.getRecentProducts();
        assertEquals(recentProducts.size(), 2);
    }

    @Test
    public void testSaveAlreadyHave() {
        productHistoryService.save(product4, request);
        productHistoryService.save(product4, request);
        recentProducts = productHistoryService.getRecentProducts();
        assertEquals(recentProducts.size(), 1);
    }

    @Test
    public void testSaveMoreThanThree() {
        productHistoryService.save(product1, request);
        productHistoryService.save(product2, request);
        productHistoryService.save(product3, request);
        productHistoryService.save(product4, request);
        recentProducts = productHistoryService.getRecentProducts();
        assertEquals(recentProducts.size(), 3);
        assertEquals(recentProducts.get(0), product2);
    }

}
