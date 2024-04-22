package com.juliancambraia.crudspring.enums.converters;

import java.util.stream.Stream;

import com.juliancambraia.crudspring.enums.Category;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

  @Override
  public String convertToDatabaseColumn(Category category) {
    if (category == null) {
      return null;
    }

    return category.getName();

  }

  @Override
  public Category convertToEntityAttribute(String value) {
    if (value == null) {
      return null;
    }

    return Stream.of(Category.values())
        .filter(t -> t.getName().equals(value))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
