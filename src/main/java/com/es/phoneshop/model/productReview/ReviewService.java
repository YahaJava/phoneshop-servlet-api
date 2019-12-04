package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ReviewService {
    void add(ProductReview productReview);
    List<ProductReview> getReviews(Product product);
}
