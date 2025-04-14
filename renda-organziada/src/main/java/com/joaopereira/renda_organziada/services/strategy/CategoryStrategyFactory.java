package com.joaopereira.renda_organziada.services.strategy;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.joaopereira.renda_organziada.enums.CategoryType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class CategoryStrategyFactory {

    private final Map<CategoryType,CategoryStrategy> categoryStrategyMap;

    public CategoryStrategyFactory(BaseCategoryStrategy base, SimpleCategoryStrategy simple) {
        categoryStrategyMap = Map.of(
                CategoryType.BASE, base,
                CategoryType.SIMPLE, simple
        );
    }
    public CategoryStrategy getCategoryStrategyMap(CategoryType type) {
        return categoryStrategyMap.get(type);
    }
}
