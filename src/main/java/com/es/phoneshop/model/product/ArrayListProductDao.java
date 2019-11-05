package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> products;
    private static ArrayListProductDao instance;

    private ArrayListProductDao() {
        products = new ArrayList<>();
    }

    private ArrayListProductDao(List<Product> products)
    {
        this.products=products;
    }

    synchronized public static ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    public static void setInstance(List<Product> products)
    {
        instance = new ArrayListProductDao(products);
    }

    public List<Product> getAllProducts() {
        return products;
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
    public synchronized List<Product> findProducts(String query,String sort,String order) {
        List<Product> productList = products.stream()
                .filter(product -> ((product.getStock() > 0) && (product.getPrice() != null)))
                .collect(Collectors.toList());
        if(query!=null&&!query.equals(""))
        {
            productList = search(query,productList);
        }
        if(sort!=null)
        {
            productList = sorting(sort,order,productList);
        }
        return productList;
    }
    private List<Product> search(String query,List<Product> searchProducts)
    {
        String[] arrayOfWordsFromQuery = query.toLowerCase().split(" ");
        List<Product> queryResult = new ArrayList<>();
        for (String word:arrayOfWordsFromQuery) {
            queryResult.addAll(searchProducts.stream()
                    .filter(product -> (product.getDescription().toLowerCase().contains(word)))
                    .collect(Collectors.toList()));
        }
        return queryResult;
    }
    private List<Product> sorting (String sort, String order, List<Product> sortProducts) {
        switch (sort){
            case "description":
                switch (order){
                    case "asc":
                        sortProducts = sortProducts.stream()
                                .sorted(Comparator.comparing(Product::getDescription))
                                .collect(Collectors.toList());
                        return sortProducts;
                    case "desc":
                        sortProducts = sortProducts.stream()
                                .sorted(Comparator.comparing(Product::getDescription)
                                        .reversed())
                                .collect(Collectors.toList());
                        return sortProducts;
                }
            case "price":
                switch (order){
                    case "asc":
                        sortProducts = sortProducts.stream()
                                .sorted((o1, o2) -> o1.getPrice().subtract(o2.getPrice()).intValue())
                                .collect(Collectors.toList());
                        return sortProducts;
                    case "desc":
                        sortProducts = sortProducts.stream()
                                .sorted((o1, o2) -> o2.getPrice().subtract(o1.getPrice()).intValue())
                                .collect(Collectors.toList());
                        return sortProducts;
                }
        }
        return sortProducts;
    }

    @Override
    public synchronized void save(Product product) {
        if (checkIdForSave(product)) {
            products.add(product);
        } else {
            throw new IllegalArgumentException("Product with such id is already exist");
        }
    }

    private boolean checkIdForSave(Product product)
    {
        return products.stream().noneMatch(product1 -> product1.getId().equals(product.getId()));
    }

    @Override
    public synchronized void delete(Long id) {
        Product product = products.stream()
                .filter(product1 -> (product1.getId().equals(id)))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Product with such id is not found"));
        products.remove(product);
    }
}
