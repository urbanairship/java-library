/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification;

import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.DeviceType;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import java.util.Map;

public final class Notification extends PushModelObject {

    private final Optional<String> alert;
    private final ImmutableMap<NotificationPayloadOverrideKey, ? extends DevicePayloadOverride> deviceTypePayloadOverrides;

    /**
     * New Notification Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    private Notification(Optional<String> alert,
                        ImmutableMap<NotificationPayloadOverrideKey, ? extends DevicePayloadOverride> deviceTypePayloadOverrides) {
        this.alert = alert;
        this.deviceTypePayloadOverrides = deviceTypePayloadOverrides;
    }

    /**
     * Get the alert for this notification. This is optional if there are
     * deviceType overrides
     * @return alert
     */
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Get the deviceType overrides for this notification. These are optional
     * @return deviceType overrides
     */
    public Optional<ImmutableSet<DeviceType>> getOverrideDeviceTypes() {
        if (deviceTypePayloadOverrides == null || deviceTypePayloadOverrides.size() == 0 ) {
            return Optional.<ImmutableSet<DeviceType>>absent();
        } else {
            ImmutableSet.Builder<DeviceType> builder = ImmutableSet.<DeviceType>builder();
            for (NotificationPayloadOverrideKey key : deviceTypePayloadOverrides.keySet()) {
                builder.add(key.getDeviceType());
            }
            return Optional.of(builder.build());
        }
    }

    /**
     * Return the DeviceType override for this Notification for the given key if
     * set.
     * @param deviceType DeviceType of the override
     * @param overrideType Class of the override
     * @return override or Optional.absent()
     */
    @SuppressWarnings("unchecked")
    public <O extends DevicePayloadOverride> Optional<O> getDeviceTypeOverride(DeviceType deviceType, Class<O> overrideType) {
        // Safe because the builder enforces the tie between the Class key and the value in the map
        return Optional.fromNullable((O) deviceTypePayloadOverrides.get(new NotificationPayloadOverrideKey(deviceType, overrideType)));
    }

    /**
     * Returna a map of all the deviceType overrides for the Notification.
     * @return overrides
     */
    public Map<DeviceType, DevicePayloadOverride> getDeviceTypePayloadOverrides() {
        Map<DeviceType, DevicePayloadOverride> overrides = Maps.newHashMap();
        for (Map.Entry<NotificationPayloadOverrideKey, ? extends DevicePayloadOverride> entry : deviceTypePayloadOverrides.entrySet()) {
            overrides.put(entry.getKey().getDeviceType(), entry.getValue());
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
        if (deviceTypePayloadOverrides != null ? !deviceTypePayloadOverrides.equals(that.deviceTypePayloadOverrides)
                : that.deviceTypePayloadOverrides != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = alert != null ? alert.hashCode() : 0;
        result = 31 * result + (deviceTypePayloadOverrides != null ? deviceTypePayloadOverrides.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "alert=" + alert +
                ", deviceTypePayloadOverrides=" + deviceTypePayloadOverrides +
                '}';
    }

    public static class Builder {

        private final ImmutableMap.Builder<NotificationPayloadOverrideKey, DevicePayloadOverride> deviceTypePayloadOverridesBuilder = ImmutableMap.builder();

        private String alert = null;

        private Builder() { }

        /**
         * Set an alert for this Notification. If a DeviceType override is included,
         * this alert is optional.
         * @param alert The alert
         * @return Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Add a deviceType override. There is a deviceType override for each supported
         * deviceType that provide options specific to that deviceType.
         * @param deviceType Specific deviceType
         * @param payload Payload for the deviceType
         * @return Builder
         */
        public <P extends DevicePayloadOverride> Builder addDeviceTypeOverride(DeviceType deviceType, P payload) {
            this.deviceTypePayloadOverridesBuilder.put(new NotificationPayloadOverrideKey(deviceType, payload.getClass()), payload);
            return this;
        }

        /**
         * Build a Notification
         * @return Notification
         */
        public Notification build() {
            ImmutableMap<NotificationPayloadOverrideKey, DevicePayloadOverride> overrides = deviceTypePayloadOverridesBuilder.build();
            Preconditions.checkArgument(alert != null || !overrides.isEmpty(),
                    "Must either specify default notification keys or at least a single deviceType override");

            return new Notification(
                Optional.fromNullable(alert),
                overrides
            );
        }
    }
}
