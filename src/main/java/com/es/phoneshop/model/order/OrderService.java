package com.es.phoneshop.model.order;

public interface OrderService {
    String addOrder(Order order);
    Order getOrder(String orderID);
}
