package com.urbanairship.api.createandsend.model.audience;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class SmsChannels {
    private final ImmutableList<SmsChannel> smsChannels;

    private SmsChannels(Builder builder) {
        this.smsChannels = builder.smsChannels.build();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public ImmutableList<SmsChannel> getSmsChannels() {
        return smsChannels;
    }

    public static class Builder {
        private ImmutableList.Builder<SmsChannel> smsChannels = ImmutableList.builder();

        public Builder addSmsChannel(SmsChannel smsChannel) {
            smsChannels.add(smsChannel);
            return this;
        }

        public Builder addAllSmsChannels(List<SmsChannel> smsChannelList) {
            smsChannels.addAll(smsChannelList);
            return this;
        }

        public SmsChannels build() {
            return new SmsChannels(this);
        }
    }
}
