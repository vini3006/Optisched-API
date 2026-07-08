package com.vinibarros.optisched.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TermConverter implements AttributeConverter<Term, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Term term) {
        return term != null ? term.getValue() : null;
    }

    @Override
    public Term convertToEntityAttribute(Integer value) {
        return value != null ? Term.fromValue(value) : null;
    }
}