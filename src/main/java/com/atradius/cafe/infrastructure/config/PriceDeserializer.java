package com.atradius.cafe.infrastructure.config;

import com.atradius.cafe.domain.model.Price;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PriceDeserializer extends JsonDeserializer<Price> {
    @Override
    public Price deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Map<String, BigDecimal> prices = new HashMap<>();

        if (!node.isEmpty()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                BigDecimal value = new BigDecimal(field.getValue().asText());
                prices.put(key, value);
            }
        }

        return Price.builder().prices(prices).build();
    }
}
