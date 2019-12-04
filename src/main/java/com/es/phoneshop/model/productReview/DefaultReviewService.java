package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultReviewService implements ReviewService {

    private List<ProductReview> productReviews;

    private DefaultReviewService() {
        productReviews = new ArrayList<>();
    }


    public static DefaultReviewService getInstance() {
        return DefaultReviewService.DefaultReviewServiceHolder.defaultReviewService;
    }

    public List<ProductReview> getProductReviews() {
        return productReviews;
    }

    @Override
    public void add(ProductReview productReview) {
        if (checkEmptyFields(productReview)) {
            throw new IllegalArgumentException();
        }
        int rating = Integer.parseInt(productReview.getRating());
        if (checkValidRating(rating)) {
            throw new NumberFormatException();
        }
        productReviews.add(productReview);
    }

    private boolean checkValidRating(int rating) {
        if (rating > 10 || rating < 0) {
            return true;
        }
        return false;
    }

    private boolean checkEmptyFields(ProductReview productReview) {
        if (productReview.getName().equals("") || productReview.getRating().equals("") || productReview.getComment().equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public List<ProductReview> getReviews(Product product) {
        List<ProductReview> resultProductReview = productReviews.stream()
                .filter(review -> review.getProduct().getCode().equals(product.getCode()))
                .collect(Collectors.toList());
        return resultProductReview;
    }

    private static class DefaultReviewServiceHolder {
        static final DefaultReviewService defaultReviewService = new DefaultReviewService();
    }

}
