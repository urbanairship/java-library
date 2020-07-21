package com.urbanairship.api.customevents.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomEventPropValue {

    private static final CustomEventPropValue TRUE_INSTANCE = new CustomEventPropValue(true);
    private static final CustomEventPropValue FALSE_INSTANCE = new CustomEventPropValue(false);

    private final Object value;

    public static CustomEventPropValue of(boolean value) {
        return value ? TRUE_INSTANCE : FALSE_INSTANCE;
    }

    public static CustomEventPropValue of(Number value) {
        return new CustomEventPropValue(value);
    }

    public static CustomEventPropValue of(String value) {
        return new CustomEventPropValue(value);
    }

    public static CustomEventPropValue of(Iterable<CustomEventPropValue> value) {
        return new CustomEventPropValue(ImmutableList.copyOf(value));
    }

    public static CustomEventPropValue of(Map<String, CustomEventPropValue> value) {
        return new CustomEventPropValue(ImmutableMap.copyOf(value));
    }

    private CustomEventPropValue(Object value) {
        this.value = value;
    }

    public boolean isBoolean() {
        return value instanceof Boolean;
    }

    public boolean isNumber() {
        return value instanceof Number;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public boolean isArray() {
        return value instanceof List;
    }

    public boolean isObject() {
        return value instanceof Map;
    }

    public boolean isPrimitive() {
        return isBoolean() || isNumber() || isString();
    }

    public boolean isScalar() {
        return  isPrimitive();
    }

    public boolean getAsBoolean() {
        return value instanceof Boolean ? castValue() : Boolean.parseBoolean(String.valueOf(value));
    }

    public Number getAsNumber() {
        return value instanceof Number ? castValue() : new BigDecimal(String.valueOf(value));
    }

    public String getAsString() {
        return value instanceof String ? castValue() : String.valueOf(value);
    }

    public List<CustomEventPropValue> getAsList() {
        if (!isArray()) {
            throw new IllegalStateException("Unable to convert to List!");
        }
        return castValue();
    }

    public Map<String, CustomEventPropValue> getAsMap() {
        if (!isObject()) {
            throw new IllegalStateException("Unable to convert to Map!");
        }
        return castValue();
    }

    @SuppressWarnings("unchecked")
    private <T> T castValue() {
        return (T) value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomEventPropValue propValue = (CustomEventPropValue) o;
        return Objects.equals(value, propValue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
