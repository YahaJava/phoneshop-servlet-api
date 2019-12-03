package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.HttpSessionCartService;

import java.util.UUID;

public class OrderDefaultService implements OrderService {

    private OrderDefaultService() {
    }

    public static OrderService getInstance() {
        return OrderDefaultServiceHolder.orderDefaultService;
    }

    @Override
    public String addOrder(Order order) {
        checkEmptyFields(order);
        UUID id = UUID.randomUUID();
        String secureID = "" + id;
        HashMapOrderDAO hashMapOrderDAO = HashMapOrderDAO.getInstance();
        hashMapOrderDAO.addOrder(secureID, order);
        return secureID;
    }

    private void checkEmptyFields(Order order) {
        if (order.getName().equals("") || order.getDate().equals("") || order.getAddress().equals("")) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Order getOrder(String orderID) {
        HashMapOrderDAO hashMapOrderDAO = HashMapOrderDAO.getInstance();
        Order order = hashMapOrderDAO.getOrder(orderID);
        return order;
    }

    private static class OrderDefaultServiceHolder {
        static final OrderDefaultService orderDefaultService = new OrderDefaultService();
    }
}
