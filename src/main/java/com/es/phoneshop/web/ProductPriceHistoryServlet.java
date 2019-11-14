package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductPriceHistoryServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getPathInfo().substring(1);
        try {
            request.setAttribute("product", productDao.getProduct(code));
            request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("product", code);
            response.sendError(404);
        }
    }


}