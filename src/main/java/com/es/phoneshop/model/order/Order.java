package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

    private Cart cart;
    private String name;
    private BigDecimal deliveryCost;
    private String date;
    private String address;
    private String payment;

    public Order(Cart cart, String name, BigDecimal deliveryCost, String date, String address, String payment) {
        this.cart = cart;
        this.name = name;
        this.deliveryCost = deliveryCost;
        this.date = date;
        this.address = address;
        this.payment = payment;
    }


    public Cart getCart() {
        return cart;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String getPayment() {
        return payment;
    }
}
