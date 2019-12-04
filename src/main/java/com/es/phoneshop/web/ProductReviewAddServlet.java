package com.es.phoneshop.web;


import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.productReview.DefaultReviewService;
import com.es.phoneshop.model.productReview.ProductReview;
import com.es.phoneshop.model.productReview.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductReviewAddServlet extends HttpServlet {
        private ProductDao productDao;
        private ReviewService reviewService;

        @Override
        public void init() {
            productDao = ArrayListProductDao.getInstance();
            reviewService = DefaultReviewService.getInstance();
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Product product = productDao.getProduct(request.getParameter("productCode"));
            String name = request.getParameter("name");
            String rating = request.getParameter("rating");
            String comment = request.getParameter("comment");
            ProductReview productReview = new ProductReview(name, rating, comment, product);
            try {
                reviewService.add(productReview);
                response.sendRedirect(request.getContextPath() + "/products/" + product.getCode() + "?added=true");
            } catch (NumberFormatException e) {
                String error = "Incorrect rating fill";
                request.setAttribute("error1", error);
                response.sendRedirect(request.getContextPath() + "/products/" + product.getCode() + "?error=incorrect");
            } catch (IllegalArgumentException e) {
                String error = "Fill all empty fields";
                request.setAttribute("error1", error);
                response.sendRedirect(request.getContextPath() + "/products/" + product.getCode() + "?error=empty");
            }
        }
}


