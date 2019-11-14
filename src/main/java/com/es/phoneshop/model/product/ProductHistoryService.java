package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;

public class ProductHistoryService {
    List<Product> recentProducts;

    private ProductHistoryService() {
        recentProducts = new ArrayList<>();
    }

    public static ProductHistoryService getInstance() {
        return ProductHistoryServiceHolder.productHistoryService;
    }

    public List<Product> getRecentProducts() {
        return recentProducts;
    }

    public void setRecentProducts(List<Product> recentProducts) {
        this.recentProducts = recentProducts;
    }

    public synchronized boolean save(Product product) {
        if (checkIdForSave(product)) {
            recentProducts.add(product);
            if (getRecentProducts().size() > 3) {
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
