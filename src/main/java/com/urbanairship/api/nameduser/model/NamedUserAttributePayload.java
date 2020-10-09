package com.urbanairship.api.nameduser.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.channel.model.attributes.Attribute;

public class NamedUserAttributePayload extends NamedUserModelObject {
    private final ImmutableList<Attribute> attributes;

    private NamedUserAttributePayload(ImmutableList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the list of attributes to be added to the named user.
     *
     * @return ImmutableList of Attribute attributes
     */
    public ImmutableList<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedUserAttributePayload that = (NamedUserAttributePayload) o;
        return Objects.equal(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attributes);
    }

    @Override
    public String toString() {
        return "NamedUserAttributePayload{" +
                "attributes=" + attributes +
                '}';
    }

    /**
     * NamedAttributePayload Builder
     */
    public static class Builder {
        ImmutableList.Builder<Attribute> attributeBuilder = ImmutableList.builder();

        /**
         * Add an attribute to associate with the named user.
         *
         * @param attribute Attribute
         * @return Builder
         */
        public Builder addAttribute(Attribute attribute) {
            attributeBuilder.add(attribute);
            return this;
        }

        public NamedUserAttributePayload build() {
            ImmutableList<Attribute> attributes = attributeBuilder.build();

            Preconditions.checkArgument(attributes.size() > 0, "At least one attribute must be set.");

            return new NamedUserAttributePayload(attributes);
        }
    }
}
