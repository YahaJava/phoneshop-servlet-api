package com.es.phoneshop.web;



import com.es.phoneshop.model.card.CartService;
import com.es.phoneshop.model.card.DefaultCartService;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao products;
    private CartService cartService;

    @Override
    public void init() {
        products = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code =  getCode(request);
        try {
            request.setAttribute("product", products.getProduct(code));
            request.setAttribute("card", cartService.getCart().getCartItems());
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("product", code);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = products.getProduct(getCode(request));
        try {
            int quantity = Integer.valueOf(request.getParameter("quantity"));
            cartService.add(product,quantity);
            product.setStock(product.getStock()-quantity);
            request.setAttribute("sucses", "Product added to card!");
        }
        catch (NumberFormatException e)
        {
            request.setAttribute("error", "Not a number");
        }
        catch (OutOfStockException e)
        {
            request.setAttribute("error", "Not enought stock. Availible: "+e.getValidStock());
        }
        request.setAttribute("product", product);
        request.setAttribute("card", cartService.getCart().getCartItems());
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    private String getCode(HttpServletRequest request) {
        return request.getPathInfo().substring(1);
    }
}
