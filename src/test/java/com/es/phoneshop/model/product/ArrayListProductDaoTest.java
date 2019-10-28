package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

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

    @Test
    public void testGetProductNull() {
        assertNotNull(productDao.getProduct(100L));
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testDeleteProduct() {
        productDao.delete(2L);
    }

    @Test
    public void testDeleteProductNull() {
        productDao.delete(100L);
    }
    @Test
    public void testSaveProduct() {
        productDao.save(new Product(14L, "iphone7", "Apple iPhone 7", new BigDecimal(1500), Currency.getInstance("USD"), 20, "no image"));
        assertNotNull(productDao.getProduct(14L));
    }

    @Test
    public void testSaveProductNotSaved() {
        productDao.save(new Product(2L, "iphone7", "Apple iPhone 7", new BigDecimal(1500), Currency.getInstance("USD"), 20, "no image"));
        assertNotNull(productDao.getProduct(2L));
    }

}
