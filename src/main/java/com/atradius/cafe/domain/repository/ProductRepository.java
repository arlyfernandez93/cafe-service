package com.atradius.cafe.domain.repository;

import com.atradius.cafe.domain.model.Product;

import java.util.List;
public interface ProductRepository {
    List<Product> findAll();
    Product findByDrinkName(String drinkName);
}
