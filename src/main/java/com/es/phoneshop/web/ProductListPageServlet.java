package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductHistoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;
    private ProductHistoryService productHistoryService;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        productHistoryService = ProductHistoryService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        request.setAttribute("products", productDao.findProducts(query, sort, order));
        request.setAttribute("recentProducts", productHistoryService.getRecentProducts(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }


}
