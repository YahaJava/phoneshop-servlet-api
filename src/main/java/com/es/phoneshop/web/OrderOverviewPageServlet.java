package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDefaultService;
import com.es.phoneshop.model.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {

    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        orderService = OrderDefaultService.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer url = req.getRequestURL();
        String orderId = url.substring(url.lastIndexOf("/")+1);
        Order order = orderService.getOrder(orderId);
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(req,resp);
    }
}