package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> products;

    private ArrayListProductDao() {
        products = new ArrayList<>();
    }


    public static ArrayListProductDao getInstance() {
        return ProductDaoHolder.productDao;
    }


    public List<Product> getAllProducts() {
        return products;
    }

    protected void setProducts(ArrayList<Product> productList) {
        products=productList;
    }

    @Override
    public synchronized Product getProduct(Long id) {
        Product product = products.stream()
                .filter(product1 -> (product1.getId().equals(id)))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Product with such id is not found"));
        return product;
    }

    @Override
    public synchronized Product getProduct(String code) {
        Product product = products.stream()
                .filter(product1 -> (product1.getCode().equals(code)))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Product with such code is not found"));
        return product;
    }

    @Override
    public synchronized List<Product> findProducts(String query, String sort, String order) {
        List<Product> productList = products.stream()
                .filter(product -> ((product.getStock() > 0) && (product.getPrice() != null)))
                .collect(Collectors.toList());
        if (query != null && !query.equals("")) {
            productList = search(query, productList);
        }
        if (sort != null) {
            productList = sorting(sort, order, productList);
        }
        return productList;
    }

    private List<Product> search(String query, List<Product> searchProducts) {
        String[] arrayOfWordsFromQuery = query.toLowerCase().split(" ");
        List<Product> queryResult = new ArrayList<>();
        for (String word : arrayOfWordsFromQuery) {
            queryResult.addAll(searchProducts.stream()
                    .filter(product -> (product.getDescription().toLowerCase().contains(word)))
                    .collect(Collectors.toList()));
        }
        return queryResult;
    }

    private List<Product> sorting(String sort, String order, List<Product> sortProducts) {
        Comparator<Product> comparator = null;
        if (sort.equals("description")) {
            comparator = Comparator.comparing(Product::getDescription);
        }

        if (sort.equals("price")) {
            comparator = Comparator.comparing(Product::getPrice);
        }

        if (order.equals("desc")) {
            comparator = comparator.reversed();
        }
        return sortProducts.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (checkIdForSave(product)) {
            products.add(product);
        } else {
            throw new IllegalArgumentException("Product with such id is already exist");
        }
    }

    private boolean checkIdForSave(Product product) {
        return products.stream().noneMatch(product1 -> product1.getId().equals(product.getId()));
    }

    @Override
    public synchronized void delete(Long id) {
        Product product = products.stream()
                .filter(product1 -> (product1.getId().equals(id)))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Product with such id is not found"));
        products.remove(product);
    }

    private static class ProductDaoHolder {
        static final ArrayListProductDao productDao = new ArrayListProductDao();
    }

}
