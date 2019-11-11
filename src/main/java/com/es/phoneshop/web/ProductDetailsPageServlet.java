package com.es.phoneshop.web;


import com.es.phoneshop.model.card.Cart;
import com.es.phoneshop.model.card.CartService;
import com.es.phoneshop.model.card.HttpSessionCartService;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductHistory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;
    private CartService cartService;
    private ProductHistory productHistory;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        productHistory = ProductHistory.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = getCode(request);
        try {
            Product product = productDao.getProduct(code);
            showPage(request, response, product);
            if (productHistory.getRecentProducts().size() > 2 & productHistory.save(product)) {
                productHistory.delete();
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("product", code);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productDao.getProduct(getCode(request));
        String error = null;
        try {
            Locale locale = request.getLocale();
            String quantityString = request.getParameter("quantity");
            int quantity = NumberFormat.getNumberInstance(locale).parse(quantityString).intValue();
            Cart cart = cartService.getCart(request);
            cartService.add(cart, product, quantity);
            product.setStock(product.getStock() - quantity);
        } catch (ParseException e) {
            error = "Not a number";
        } catch (OutOfStockException e) {
            error = "Not enought stock. Availible: " + e.getValidStock();
        }
        if (error != null) {
            request.setAttribute("error", error);
            showPage(request, response, product);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?success=true");
    }

    private String getCode(HttpServletRequest request) {
        return request.getPathInfo().substring(1);
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response, Product product) throws ServletException, IOException {
        request.setAttribute("product", product);
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }
}