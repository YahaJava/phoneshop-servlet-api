package com.es.phoneshop.model.product;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.Silent.class)
public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao;

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
        productDao = ArrayListProductDao.getInstance();
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);
        productDao.setProduct(product1);
        productDao.setProduct(product2);
        productDao.setProduct(product3);
    }

    @Test
    public void testGetProductExisting() {
        Product product = productDao.getProduct(1L);
        assertSame(product1, product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductNotExisting() {
        productDao.getProduct(100L);
    }

    @Test
    public void testFindProductsSearch() {

        when(product1.getPrice()).thenReturn(new BigDecimal(100));
        when(product2.getPrice()).thenReturn(new BigDecimal(200));
        when(product3.getPrice()).thenReturn(new BigDecimal(120));

        when(product1.getStock()).thenReturn(50);
        when(product2.getStock()).thenReturn(40);
        when(product3.getStock()).thenReturn(35);

        when(product1.getDescription()).thenReturn("Samsung G7");
        when(product2.getDescription()).thenReturn("Samsung G6");
        when(product3.getDescription()).thenReturn("Apple 8");

        assertTrue(productDao.findProducts("samsung", "price", "asc").size() == 2);

    }

    @Test
    public void testFindProductsSortDescriptionAscending() {

        when(product1.getPrice()).thenReturn(new BigDecimal(100));
        when(product2.getPrice()).thenReturn(new BigDecimal(200));
        when(product3.getPrice()).thenReturn(new BigDecimal(120));

        when(product1.getStock()).thenReturn(50);
        when(product2.getStock()).thenReturn(40);
        when(product3.getStock()).thenReturn(35);

        when(product1.getDescription()).thenReturn("Samsung G7");
        when(product2.getDescription()).thenReturn("Nokia X6");
        when(product3.getDescription()).thenReturn("Apple 8");

        ArrayList<Product> filteredList = (ArrayList) productDao.findProducts("", "description", "asc");

        assertSame(product3.getId(), filteredList.get(0).getId());

    }

    @Test
    public void testFindProductsSortPriceDescending() {

        when(product1.getPrice()).thenReturn(new BigDecimal(200));
        when(product2.getPrice()).thenReturn(new BigDecimal(500));
        when(product3.getPrice()).thenReturn(new BigDecimal(300));

        when(product1.getStock()).thenReturn(50);
        when(product2.getStock()).thenReturn(40);
        when(product3.getStock()).thenReturn(35);


        ArrayList<Product> filteredList = (ArrayList) productDao.findProducts("", "price", "desc");

        assertSame(product2.getId(), filteredList.get(0).getId());
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
