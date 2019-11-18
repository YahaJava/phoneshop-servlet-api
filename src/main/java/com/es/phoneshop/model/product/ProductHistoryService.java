package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ProductHistoryService {
    private List<Product> recentProducts;

    private ProductHistoryService() {
        recentProducts = new ArrayList<>();
    }

    public static ProductHistoryService getInstance() {
        return ProductHistoryServiceHolder.productHistoryService;
    }

    public List<Product> getRecentProducts(HttpServletRequest request) {
        recentProducts = (List<Product>) request.getSession().getAttribute("recentProducts");
        if (recentProducts == null) {
            recentProducts = new ArrayList<>();
            request.getSession().setAttribute("recentProducts", recentProducts);
        }
        return recentProducts;
    }

    public List<Product> getRecentProducts() {
        return recentProducts;
    }

    public void setRecentProducts(List<Product> recentProducts) {
        recentProducts.clear();
        this.recentProducts = recentProducts;
    }

    public synchronized boolean save(Product product, HttpServletRequest request) {
        if (checkIdForSave(product)) {
            recentProducts.add(product);
            if (recentProducts.size() > 3) {
                delete();
            }
            return true;
        }
        return false;
    }

    private boolean checkIdForSave(Product product) {
        return recentProducts.stream().noneMatch(product1 -> product1.getId().equals(product.getId()));
    }

    public synchronized void delete() {
        recentProducts.remove(0);
    }

    private static class ProductHistoryServiceHolder {
        static final ProductHistoryService productHistoryService = new ProductHistoryService();
    }
}
