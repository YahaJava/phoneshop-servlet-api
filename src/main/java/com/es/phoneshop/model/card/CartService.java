package com.es.phoneshop.model.card;

import com.es.phoneshop.model.product.Product;

public interface CartService
{
    Cart getCart();
    void add(Product product, int quantity);
}