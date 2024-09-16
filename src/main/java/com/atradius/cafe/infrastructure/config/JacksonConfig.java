package com.atradius.cafe.infrastructure.config;

import com.atradius.cafe.domain.model.Price;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Price.class, new PriceDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

}
