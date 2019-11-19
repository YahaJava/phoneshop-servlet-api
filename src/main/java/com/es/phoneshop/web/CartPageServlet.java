package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartPageServlet extends HttpServlet {

    private CartService cartService;
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productCodes = request.getParameterValues("productCode");
        String[] quantitiesStrings = request.getParameterValues("quantity");
        Map<Product, String> errorMap = new HashMap<>();
        for (int i = 0; i < productCodes.length; i++) {
            Product product = productDao.getProduct(productCodes[i]);
            try {
                Locale locale = request.getLocale();
                String quantityString = quantitiesStrings[i];
                int quantity = NumberFormat.getNumberInstance(locale).parse(quantityString).intValue();
                Cart cart = cartService.getCart(request);
                cartService.update(cart, product, quantity);
            } catch (ParseException e) {
                errorMap.put(product, "Not a number");
            } catch (OutOfStockException e) {
                errorMap.put(product, "Not enought stock. Availible: " + e.getValidStock());
            } catch (IllegalArgumentException e) {
                errorMap.put(product, "Incorrect number");
            }
        }

        if (!errorMap.isEmpty()) {
            request.setAttribute("error", errorMap);
            showPage(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?update=true");
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }
}



