/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification;

import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.Platform;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import java.util.Map;

public final class Notification extends PushModelObject {

    private final Optional<String> alert;
    private final ImmutableMap<NotificationPayloadOverrideKey, ? extends DevicePayloadOverride> platformPayloadOverrides;

    /**
     * New Notification Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    private Notification(Optional<String> alert,
                        ImmutableMap<NotificationPayloadOverrideKey, ? extends DevicePayloadOverride> platformPayloadOverrides) {
        this.alert = alert;
        this.platformPayloadOverrides = platformPayloadOverrides;
    }

    /**
     * Get the alert for this notification. This is optional if there are
     * platform overrides
     * @return alert
     */
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Get the platform overrides for this notification. These are optional
     * @return platform overrides
     */
    public Optional<ImmutableSet<Platform>> getOverridePlatforms() {
        if (platformPayloadOverrides == null || platformPayloadOverrides.size() == 0 ) {
            return Optional.<ImmutableSet<Platform>>absent();
        } else {
            ImmutableSet.Builder<Platform> builder = ImmutableSet.<Platform>builder();
            for (NotificationPayloadOverrideKey key : platformPayloadOverrides.keySet()) {
                builder.add(key.getPlatform());
            }
            return Optional.of(builder.build());
        }
    }

    /**
     * Return the Platform override for this Notification for the given key if
     * set.
     * @param platform Platform of the override
     * @param overrideType Class of the override
     * @return override or Optional.absent()
     */
    @SuppressWarnings("unchecked")
    public <O extends DevicePayloadOverride> Optional<O> getPlatformOverride(Platform platform, Class<O> overrideType) {
        // Safe because the builder enforces the tie between the Class key and the value in the map
        return Optional.fromNullable((O) platformPayloadOverrides.get(new NotificationPayloadOverrideKey(platform, overrideType)));
    }

    /**
     * Returna a map of all the platform overrides for the Notification.
     * @return overrides
     */
    public Map<Platform, DevicePayloadOverride> getPlatformPayloadOverrides() {
        Map<Platform, DevicePayloadOverride> overrides = Maps.newHashMap();
        for (Map.Entry<NotificationPayloadOverrideKey, ? extends DevicePayloadOverride> entry : platformPayloadOverrides.entrySet()) {
            overrides.put(entry.getKey().getPlatform(), entry.getValue());
        }

        return overrides;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Notification that = (Notification) o;

        if (alert != null ? !alert.equals(that.alert) : that.alert != null) {
            return false;
        }
        if (platformPayloadOverrides != null ? !platformPayloadOverrides.equals(that.platformPayloadOverrides)
                : that.platformPayloadOverrides != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = alert != null ? alert.hashCode() : 0;
        result = 31 * result + (platformPayloadOverrides != null ? platformPayloadOverrides.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "alert=" + alert +
                ", platformPayloadOverrides=" + platformPayloadOverrides +
                '}';
    }

    public static class Builder {

        private final ImmutableMap.Builder<NotificationPayloadOverrideKey, DevicePayloadOverride> platformPayloadOverridesBuilder = ImmutableMap.builder();

        private String alert = null;

        private Builder() { }

        /**
         * Set an alert for this Notification. If a Platform override is included,
         * this alert is optional.
         * @param alert The alert
         * @return Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Add a platform override. There is a platform override for each supported
         * platform that provide options specific to that platform.
         * @param platform Specific platform
         * @param payload Payload for the platform
         * @return Builder
         */
        public <P extends DevicePayloadOverride> Builder addPlatformOverride(Platform platform, P payload) {
            this.platformPayloadOverridesBuilder.put(new NotificationPayloadOverrideKey(platform, payload.getClass()), payload);
            return this;
        }

        /**
         * Build a Notification
         * @return Notification
         */
        public Notification build() {
            ImmutableMap<NotificationPayloadOverrideKey, DevicePayloadOverride> overrides = platformPayloadOverridesBuilder.build();
            Preconditions.checkArgument(alert != null || !overrides.isEmpty(),
                    "Must either specify default notification keys or at least a single platform override");

            return new Notification(
                Optional.fromNullable(alert),
                overrides
            );
        }
    }
}
