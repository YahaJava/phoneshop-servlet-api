package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.Silent.class)
public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;


    @Before
    public void setUp() {
        productDao =new ArrayListProductDao(new ArrayList<>(Arrays.asList(product1,product2,product3)));

        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);
    }
    @Test
    public void testGetProductExisting() {
        Product product = productDao.getProduct(1L);
        assertSame(product1,product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductNotExisting() {
        productDao.getProduct(100L);
    }

    @Test
    public void testFindProductsNoResults() {

        when(product1.getPrice()).thenReturn(new BigDecimal(100));
        when(product2.getPrice()).thenReturn(null);
        when(product3.getPrice()).thenReturn(new BigDecimal(120));

        when(product1.getStock()).thenReturn(50);
        when(product2.getStock()).thenReturn(40);
        when(product3.getStock()).thenReturn(0);

        assertTrue( productDao.findProducts().size() == 1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteProduct() {
        productDao.delete(product1.getId());
        productDao.getProduct(product1.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteProductNull() {
        productDao.delete(100L);
    }
    @Test
    public void testSaveProduct() {
        when(product4.getId()).thenReturn(14L);
        productDao.save(product4);
        assertSame(productDao.getProduct(product4.getId()), product4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductThatExist() {
        productDao.save(product3);
    }





}
