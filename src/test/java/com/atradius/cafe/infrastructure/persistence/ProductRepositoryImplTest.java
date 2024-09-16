package com.atradius.cafe.infrastructure.persistence;

import com.atradius.cafe.domain.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    @BeforeEach
    public void setUp() throws Exception {
        setPrivateField(productRepository, "productFilePath", "static/products.json");
    }
    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void findAll_ShouldReturnProducts_WhenFileIsFound() throws IOException {
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product("espresso", null));
        mockProducts.add(new Product("latte", null));

        when(objectMapper.readValue(any(InputStream.class), any(TypeReference.class))).thenReturn(mockProducts);

        List<Product> result = productRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("espresso", result.get(0).getDrinkName());
        assertEquals("latte", result.get(1).getDrinkName());
    }

}
