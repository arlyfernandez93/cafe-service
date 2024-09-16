package com.atradius.cafe.infrastructure.persistence;

import com.atradius.cafe.domain.model.Product;
import com.atradius.cafe.domain.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Value("${datasource.products}")
    private String productFilePath;

    private final ObjectMapper objectMapper;

    public ProductRepositoryImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Product> findAll() {
        List<Product> allProducts = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(productFilePath)) {
            if (inputStream == null) {
                throw new IOException("File not found: products.json");
            }
            allProducts = objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allProducts;
    }

    @Override
    public Product findByDrinkName(String drinkName) {
        return findAll().stream()
                .filter(product -> product.getDrinkName().equalsIgnoreCase(drinkName))
                .findFirst()
                .orElse(null);
    }

}
