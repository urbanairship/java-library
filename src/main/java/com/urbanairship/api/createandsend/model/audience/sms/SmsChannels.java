package com.urbanairship.api.createandsend.model.audience.sms;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Contains all SmsChannel objects to be used in the create and send audience.
 */
public class SmsChannels {
    private final ImmutableList<SmsChannel> smsChannels;

    private SmsChannels(Builder builder) {
        this.smsChannels = builder.smsChannels.build();
    }

    /**
     * Builder for SmsChannels
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get all the audience Sms channels.
     * @return ImmutableList of SmsChannel
     */
    public ImmutableList<SmsChannel> getSmsChannels() {
        return smsChannels;
    }

    public static class Builder {
        private ImmutableList.Builder<SmsChannel> smsChannels = ImmutableList.builder();

        /**
         * Add a SmsChannel for create and send audience.
         * @param smsChannel SmsChannel
         * @return SmsChannels Builder
         */
        public Builder addSmsChannel(SmsChannel smsChannel) {
            smsChannels.add(smsChannel);
            return this;
        }

        /**
         * Add all SmsChannel objects for create and send audience.
         * @param smsChannelList SmsChannel
         * @return SmsChannels Builder
         */
        public Builder addAllSmsChannels(List<SmsChannel> smsChannelList) {
            smsChannels.addAll(smsChannelList);
            return this;
        }

        public SmsChannels build() {
            return new SmsChannels(this);
        }
    }
}
