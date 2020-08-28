package com.urbanairship.api.customevents.model;

import com.google.common.base.MoreObjects;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomEventPropertyValue {

    private final Object value;
    private final Object propertyType;

    private enum PropertyType {
        BOOLEAN,
        NUMBER,
        STRING,
        ITERABLE,
        MAP;
    }

    public Object getPropertyType() {
        return propertyType;
    }

    public static CustomEventPropertyValue of(boolean value) {
        return new CustomEventPropertyValue(value, PropertyType.BOOLEAN);
    }

    public static CustomEventPropertyValue of(Number value) {
        return new CustomEventPropertyValue(value, PropertyType.NUMBER);
    }

    public static CustomEventPropertyValue of(String value) {
        return new CustomEventPropertyValue(value, PropertyType.STRING);
    }

    public static CustomEventPropertyValue of(Iterable<CustomEventPropertyValue> value) {
        return new CustomEventPropertyValue(value, PropertyType.ITERABLE);
    }

    public static CustomEventPropertyValue of(Map<String, CustomEventPropertyValue> value) {
        return new CustomEventPropertyValue(value, PropertyType.MAP);
    }

    private CustomEventPropertyValue(Boolean value, Object propertyType) {
        this.value = value;
        this.propertyType = propertyType;
    }

    private CustomEventPropertyValue(Number value, Object propertyType) {
        this.value = value;
        this.propertyType = propertyType;
    }

    private CustomEventPropertyValue(String value, Object propertyType) {
        this.value = value;
        this.propertyType = propertyType;
    }

    private CustomEventPropertyValue(Iterable<CustomEventPropertyValue> value, Object propertyType) {
        this.value = value;
        this.propertyType = propertyType;
    }

    private CustomEventPropertyValue(Map value, Object propertyType) {
        this.value = value;
        this.propertyType = propertyType;
    }

    public boolean isBoolean() {
        return this.propertyType == PropertyType.BOOLEAN;
    }

    public boolean isNumber() {
        return this.propertyType == PropertyType.NUMBER;
    }

    public boolean isString() {
        return this.propertyType == PropertyType.STRING;
    }

    public boolean isArray() {
        return this.propertyType == PropertyType.ITERABLE;
    }

    public boolean isObject() {
        return this.propertyType == PropertyType.MAP;
    }

    public boolean getAsBoolean() {
        if (!isBoolean()) {
            throw new IllegalStateException("Unable to convert to Boolean");
        }
        return (boolean) value;
    }

    public Number getAsNumber() {
        if (!isNumber()) {
            throw new IllegalStateException("Unable to convert to Number!");
        }
        return (Number) value;
    }

    public String getAsString() {
        if (!isString()) {
            throw new IllegalStateException("Unable to convert to String!");
        }
        return (String) value;
    }
    public List<CustomEventPropertyValue> getAsList() {
        if (!isArray()) {
            throw new IllegalStateException("Unable to convert to List!");
        }
        return (List<CustomEventPropertyValue>) value;
    }

    public Map<String, CustomEventPropertyValue> getAsMap() {
        if (!isObject()) {
            throw new IllegalStateException("Unable to convert to Map!");
        }
        return (Map<String, CustomEventPropertyValue>) value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("propertyType", propertyType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomEventPropertyValue propValue = (CustomEventPropertyValue) o;
        return Objects.equals(value, propValue.value) &&
                Objects.equals((propertyType), propValue.propertyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, propertyType);
    }
}
