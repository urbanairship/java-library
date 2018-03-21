package com.urbanairship.api.channel.model.open;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Open Platform Options Object.
 */
public class OpenChannel {

    private final String openPlatformName;
    private final Optional<String> oldAddress;
    private final Optional<ImmutableMap<String, String>> identifiers;

    private OpenChannel() {
        this(null, Optional.<String>absent(), Optional.<ImmutableMap<String,String>>absent());
    }

    @Override
    public String toString() {
        return "OpenChannel{" +
                "openPlatformName='" + openPlatformName + '\'' +
                ", oldAddress=" + oldAddress +
                ", identifiers=" + identifiers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenChannel that = (OpenChannel) o;
        return Objects.equal(openPlatformName, that.openPlatformName) &&
                Objects.equal(oldAddress, that.oldAddress) &&
                Objects.equal(identifiers, that.identifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(openPlatformName, oldAddress, identifiers);
    }

    private OpenChannel(String openPlatformName,
                        Optional<String> oldAddress,
                        Optional<ImmutableMap<String, String>> identifiers) {

        this.openPlatformName = openPlatformName;
        this.oldAddress = oldAddress;
        this.identifiers = identifiers;

    }

    private OpenChannel(Builder builder) {
        this.openPlatformName = builder.openPlatformName;
        this.oldAddress = Optional.fromNullable(builder.oldAddress);

        if (builder.identifiers.build().isEmpty()) {
           this.identifiers = Optional.absent();
        } else {
            this.identifiers = Optional.of(builder.identifiers.build());
        }
    }

    /**
     * New OpenChannel Builder.
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the canonical name of the open platform on which the channel is registered.
     * @return String openPlatformName
     */
    public String getOpenPlatformName() {
        return openPlatformName;
    }

    /**
     * Get the old address. If for any reason the primary identifier of the record changes, include this key,
     * and all tag and identifier information associated with the channel will be preserved,
     * while the address will be replaced with the new value in 'address'.
     * After a call swapping the address, new registrations referencing
     * the old address will be assumed to represent a new channel.
     * @return Optional String oldAddress
     */
    public Optional<String> getOldAddress() {
        return oldAddress;
    }

    /**
     * Get identifiers. Optional, array of string pairs. These will be delivered in open channels payloads,
     * but cannot be used for segmentations. Maximum of 100 pairs of string values.
     * This value should be exhaustive whenever this key is present—values will not be unioned
     * with existing identifiers, they will replace them, similar to "set_tags": true above.
     * @return Optional ImmutableMap of Strings
     */
    public Optional<ImmutableMap<String, String>> getIdentifiers() {
        return identifiers;
    }


    /**
     * OpenChannel Builder
     */
    public final static class Builder {
        private String openPlatformName = null;
        private String oldAddress = null;
        private ImmutableMap.Builder<String, String> identifiers = ImmutableMap.builder();

        private Builder() { }

        /**
         * Set the canonical name of the open platform on which the channel is registered.
         *
         * @param platformName String
         * @return OpenChannel Builder
         */
        public Builder setOpenPlatformName(String platformName) {
            this.openPlatformName = platformName;
            return this;
        }

        /**
         * Set old address. If for any reason the primary identifier of the record changes, include this key,
         * and all tag and identifier information associated with the channel will be preserved,
         * while the address will be replaced with the new value in 'address'.
         * After a call swapping the address, new registrations referencing
         * the old address will be assumed to represent a new channel.
         * @param oldAddress String
         * @return OpenChannel Builder
         */
        public Builder setOldAddress(String oldAddress) {
            this.oldAddress = oldAddress;
            return this;
        }

        /**
         * Add identifier. Optional, array of string pairs. These will be delivered in open channels payloads,
         * but cannot be used for segmentations. Maximum of 100 pairs of string values.
         * This value should be exhaustive whenever this key is present—values will not be unioned
         * with existing identifiers, they will replace them, similar to "set_tags": true above.
         * @param key String
         * @param value String
         * @return OpenChannel Builder
         */
        public Builder addIdentifier(String key, String value) {
            this.identifiers.put(key, value);
            return this;
        }

        /**
         * Add all identifiers. Optional, array of string pairs. These will be delivered in open channels payloads,
         * but cannot be used for segmentations. Maximum of 100 pairs of string values.
         * This value should be exhaustive whenever this key is present—values will not be unioned
         * with existing identifiers, they will replace them, similar to "set_tags": true above.
         * @param entries Map of Strings
         * @return OpenChannel Builder
         */
        public Builder addAllIdentifierEntries(Map<String, String> entries) {
            this.identifiers.putAll(entries);
            return this;
        }

        /**
         * Build the OpenChannel object
         * @return OpenChannel
         */
        public OpenChannel build() {
            Preconditions.checkNotNull(openPlatformName, "Open platform name must not be null.");
            return new OpenChannel(this);
        }
    }
}
