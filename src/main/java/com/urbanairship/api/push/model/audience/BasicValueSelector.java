/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceTypeData;

import java.util.Map;

public class BasicValueSelector implements ValueSelector {

    private final SelectorType type;
    private final String value;
    private final Optional<Map<String, String>> attributes;

    protected BasicValueSelector(SelectorType type, String value, Optional<Map<String, String>> attributes) {
        this.type = type;
        this.value = value;
        this.attributes = attributes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public SelectorType getType() {
        return this.type;
    }

    @Override
    public DeviceTypeData getApplicableDeviceTypes() {
        return DeviceTypeData.of(type.getPlatform().get());
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Optional<Map<String, String>> getAttributes() {
        return this.attributes;
    }

    @Override
    public void accept(SelectorVisitor visitor) {
        visitor.enter(this);
        visitor.exit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasicValueSelector that = (BasicValueSelector)o;
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }
        if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (type != null ? type.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BasicValueSelector{" +
            "type=" + type.getIdentifier() +
            ",value=" + value +
            ",attributes=" + attributes +
            '}';
    }

    public static class Builder {
        private SelectorType type;
        private String value;
        private ImmutableMap.Builder<String, String> attributesBuilder;

        private Builder() { }

        public Builder setType(SelectorType value) {
            this.type = value;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder addAttribute(String key, String value) {
            if (attributesBuilder == null) {
                attributesBuilder = ImmutableMap.builder();
            }
            attributesBuilder.put(key, value);
            return this;
        }

        public Builder addAllAttributes(Map<String, String> attributes) {
            if (attributesBuilder == null) {
                attributesBuilder = ImmutableMap.builder();
            }
            attributesBuilder.putAll(attributes);
            return this;
        }

        public BasicValueSelector build() {
            Preconditions.checkArgument(type != SelectorType.AND
                                        && type != SelectorType.OR
                                        && type != SelectorType.NOT,
                                        "Logical operators must have an array of one or more children.");
            Preconditions.checkArgument(type != SelectorType.ALL,
                                        "The 'all' selector cannot have a value.");
            Preconditions.checkArgument(type != SelectorType.TRIGGERED,
            "The 'triggered' selector cannot have a value.");
            Map<String, String> attributes = attributesBuilder != null
                ? attributesBuilder.build() : null;
            return new BasicValueSelector(type, value, Optional.fromNullable(attributes));
        }
    }
}
