package com.es.phoneshop.model.order;

public interface OrderDAO {
    void addOrder(String secureID,Order order);
    Order getOrder(String secureID);
}
