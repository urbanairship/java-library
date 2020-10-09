package com.urbanairship.api.channel.model.attributes.audience;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.push.model.audience.sms.SmsSelector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AttributeAudience {
    private final ImmutableSet<SmsSelector> smsSelectors;
    private final ImmutableMap<AttributeAudienceType, Set<String>> attributeDevices;

    public AttributeAudience(Builder builder) {
        this.smsSelectors = builder.smsSelectors;
        this.attributeDevices = ImmutableMap.copyOf(builder.audienceTypeMap);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the sms selector objects representing the sms audience.
     *
     * @return Immutable set of SmsSelector smsSelectors
     */
    public ImmutableSet<SmsSelector> getSmsSelectors() {
        return smsSelectors;
    }

    /**
     * Get the map of Atrribute Audience Types and the set of device id's associated with them.
     *
     * @return Map of AttributeAudienceType to Set of Strings attributeDevices
     */
    public ImmutableMap<AttributeAudienceType, Set<String>> getAttributeDevices() {
        return attributeDevices;
    }



    public static class Builder {
        ImmutableSet.Builder<SmsSelector> smsSelectorsBuilder = ImmutableSet.builder();
        ImmutableSet<SmsSelector> smsSelectors;
        Map<AttributeAudienceType, Set<String>> audienceTypeMap = new HashMap<>();

        /**
         * Add a device identifier and the audience type to update the attributes on.
         *
         * @param audienceType AttributeAudienceType
         * @param deviceId String
         * @return Builder
         */
        public Builder addDeviceId(AttributeAudienceType audienceType, String deviceId) {
            if (audienceTypeMap.containsKey(audienceType)) {
                audienceTypeMap.get(audienceType).add(deviceId);
            } else {
                Set<String> deviceIds = new HashSet<>();
                deviceIds.add(deviceId);
                audienceTypeMap.put(audienceType, deviceIds);
            }

            return this;
        }

        /**
         * Add a sms selector representing an sms device to update attributes on.
         *
         * @param smsSelector SmsSelector
         * @return Builder
         */
        public Builder addSmsSelector(SmsSelector smsSelector) {
            smsSelectorsBuilder.add(smsSelector);
            return this;
        }

        public AttributeAudience build() {
            smsSelectors = smsSelectorsBuilder.build();
            Preconditions.checkArgument(!(smsSelectors.size() == 0 && audienceTypeMap.size() == 0), "Device types or SmsSelectors must be added.");

            return new AttributeAudience(this);
        }
    }
}
