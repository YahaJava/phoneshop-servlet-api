package com.es.phoneshop.model.order;

import java.util.HashMap;
import java.util.Map;

public class HashMapOrderDAO implements OrderDAO {

    private Map<String,Order> orders;

    private HashMapOrderDAO() {
        orders = new HashMap<>();
    }

    public static HashMapOrderDAO getInstance() {
        return HashMapOrderDAOHolder.hashMapOrderDAO;
    }

    @Override
    public void addOrder(String secureID, Order order) {
        orders.put(secureID,order);
    }

    @Override
    public Order getOrder(String secureID) {
        return orders.get(secureID);
    }

    private static class HashMapOrderDAOHolder {
        static final HashMapOrderDAO hashMapOrderDAO = new HashMapOrderDAO();
    }

}
