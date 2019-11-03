package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testGetProductNotNull() {
        assertNotNull(productDao.getProduct(10L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductNull() {
        assertNotNull(productDao.getProduct(100L));
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testDeleteProduct() {
        productDao.delete(2L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteProductNull() {
        productDao.delete(100L);
    }
    @Test
    public void testSaveProduct() {

        Product product = mock(Product.class);
        when(product.getId()).thenReturn(14L);
        productDao.save(product);
        assertNotNull(productDao.getProduct(14L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductNotSaved() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(4L);
        productDao.save(product);
        assertNotNull(productDao.getProduct(4L));
    }

}
