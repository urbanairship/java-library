package com.urbanairship.api.channel.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.push.model.audience.sms.SmsSelector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChannelAudience {
    private final ImmutableSet<SmsSelector> smsSelectors;
    private final ImmutableMap<ChannelAudienceType, Set<String>> channelDevices;

    public ChannelAudience(Builder builder) {
        this.smsSelectors = builder.smsSelectors;
        this.channelDevices = ImmutableMap.copyOf(builder.audienceTypeMap);
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
     * Get the map of Channel Audience Types and the set of device id's associated with them.
     *
     * @return Map of ChannelAudienceType to Set of Strings channelDevices
     */
    public ImmutableMap<ChannelAudienceType, Set<String>> getChannelDevices() {
        return channelDevices;
    }



    public static class Builder {
        ImmutableSet.Builder<SmsSelector> smsSelectorsBuilder = ImmutableSet.builder();
        ImmutableSet<SmsSelector> smsSelectors;
        Map<ChannelAudienceType, Set<String>> audienceTypeMap = new HashMap<>();

        /**
         * Add a device identifier and the audience type to update the subscriptions list on.
         *
         * @param audienceType ChannelAudienceType
         * @param deviceId String
         * @return Builder
         */
        public Builder addDeviceId(ChannelAudienceType audienceType, String deviceId) {
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
         * Add a sms selector representing an sms device to update.
         *
         * @param smsSelector SmsSelector
         * @return Builder
         */
        public Builder addSmsSelector(SmsSelector smsSelector) {
            smsSelectorsBuilder.add(smsSelector);
            return this;
        }

        public ChannelAudience build() {
            smsSelectors = smsSelectorsBuilder.build();
            Preconditions.checkArgument(!(smsSelectors.size() == 0 && audienceTypeMap.size() == 0), "Device types or SmsSelectors must be added.");

            return new ChannelAudience(this);
        }
    }
}
