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

    public static Builder newBuilder() {
        return new Builder();
    }

    private Notification(Optional<String> alert,
                        ImmutableMap<NotificationPayloadOverrideKey, ? extends DevicePayloadOverride> deviceTypePayloadOverrides) {
        this.alert = alert;
        this.deviceTypePayloadOverrides = deviceTypePayloadOverrides;
    }

    public Optional<String> getAlert() {
        return alert;
    }

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

    @SuppressWarnings("unchecked")
    public <O extends DevicePayloadOverride> Optional<O> getDeviceTypeOverride(DeviceType deviceType, Class<O> overrideType) {
        // Safe because the builder enforces the tie between the Class key and the value in the map
        return Optional.fromNullable((O) deviceTypePayloadOverrides.get(new NotificationPayloadOverrideKey(deviceType, overrideType)));
    }

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

        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public <P extends DevicePayloadOverride> Builder addDeviceTypeOverride(DeviceType deviceType, P payload) {
            this.deviceTypePayloadOverridesBuilder.put(new NotificationPayloadOverrideKey(deviceType, payload.getClass()), payload);
            return this;
        }

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
