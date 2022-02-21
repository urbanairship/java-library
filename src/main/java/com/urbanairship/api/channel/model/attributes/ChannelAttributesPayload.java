package com.urbanairship.api.channel.model.attributes;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.channel.model.ChannelModelObject;
import com.urbanairship.api.channel.model.attributes.audience.AttributeAudience;

/**
 * Represents a Channel Attributes payload for the Airship API.
 */
public class ChannelAttributesPayload extends ChannelModelObject {
    private final AttributeAudience audience;
    ImmutableList<Attribute> attributes;

    private ChannelAttributesPayload(AttributeAudience audience, ImmutableList<Attribute> attributes) {
        this.audience = audience;
        this.attributes = attributes;
    }

    /**
     * ChannelAttributesPayload Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the audience that determines the set of channels to target.
     *
     * @return AttributeAudience audience
     */
    public AttributeAudience getAudience() {
        return audience;
    }

    /**
     * Get the list of attributes to add new attributes, or edit the value of an existing attributes, for the audience.
     *
     * @return ImmutableList of Attribute attributes
     */
    public ImmutableList<Attribute> getAttributes() {
        return attributes;
    }

    public static class Builder {
        AttributeAudience audience;
        ImmutableList.Builder<Attribute> attributesBuilder = ImmutableList.builder();

        /**
         * Set the audience that determines the set of channels to target.
         *
         * @param audience Selector
         * @return Builder
         */
        public Builder setAudience(AttributeAudience audience) {
            this.audience = audience;
            return this;
        }

        /**
         * Add a new attribute, or edit the value of an existing attribute, for the audience.
         *
         * @param attribute Attribute
         * @return Builder
         */
        public Builder addAttribute(Attribute attribute) {
            attributesBuilder.add(attribute);
            return this;
        }

        public ChannelAttributesPayload build() {
            ImmutableList<Attribute> attributes = attributesBuilder.build();
            Preconditions.checkNotNull(audience, "Audience must be set.");
            Preconditions.checkArgument(attributes.size() > 0, "Attributes must be added to ChannelAttributesPayload.");

            return new ChannelAttributesPayload(audience, attributes);
        }
    }
}
