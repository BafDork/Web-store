//package com.webstore.model;
//
//import lombok.Getter;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Getter
//@SuppressWarnings("FieldMayBeFinal")
//public class Cart {
//
//    private Long userId;
//    private Map<Product, Integer> products = new HashMap<>();
//
//    public Cart(Long userId) {
//        this.userId = userId;
//    }
//
//    public void addProduct(Product product, int quantity) {
//        products.put(product, products.getOrDefault(product, 0) + quantity);
//    }
//
//    public void removeProduct(Product product) {
//        products.remove(product);
//    }
//
//    public void updateProductQuantity(Product product, int quantity) {
//        if (quantity <= 0) {
//            products.remove(product);
//        } else {
//            products.put(product, quantity);
//        }
//    }
//
//    public double getTotalPrice() {
//        return products.entrySet().stream()
//                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
//                .sum();
//    }
//}
