package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

public class ProductReview {
    private String name;
    private String rating;
    private String comment;
    private Product product;

    public ProductReview(String name, String rating, String comment, Product product) {
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Product getProduct() {
        return product;
    }
}
