
package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDefaultService;
import com.es.phoneshop.model.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;


public class CheckoutPageServlet extends HttpServlet {

    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        orderService = OrderDefaultService.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart =  cartService.getCart(request);
        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String address = request.getParameter("address");
        String payment = request.getParameter("payment");
        BigDecimal deliveryCost = cart.getTotalPrice().divide(new BigDecimal(20));
        Order order = new Order(cart, name,deliveryCost,date,address, payment);
        try {
            String safeId = orderService.addOrder(order);
            request.getRequestDispatcher("/order/overview/" + safeId).forward(request, response);
        }
        catch (IllegalArgumentException e){
            String error = "Fill all empty fields";
            request.setAttribute("error", error);
            request.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(request, response);
        }
    }
}

