/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.actions.Action;
import com.urbanairship.api.push.model.notification.actions.ActionNameRegistry;
import com.urbanairship.api.push.model.notification.actions.ActionType;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.AppDefinedAction;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public final class Notification extends PushModelObject {

    private final Optional<String> alert;
    private final ImmutableMap<NotificationPayloadOverrideKey, ? extends DevicePayloadOverride> deviceTypePayloadOverrides;
    private final Optional<Actions> actions;
    private final Optional<Interactive> interactive;

    private Notification(Optional<String> alert,
                        ImmutableMap<NotificationPayloadOverrideKey, ? extends DevicePayloadOverride> deviceTypePayloadOverrides,
                        Optional<Actions> actions,
                        Optional<Interactive> interactive) {
        this.alert = alert;
        this.deviceTypePayloadOverrides = deviceTypePayloadOverrides;
        this.actions = actions;
        this.interactive = interactive;
    }

    public static Builder newBuilder() {
        return new Builder(ActionNameRegistry.INSTANCE);
    }

    public Builder toBuilder() {
        return newBuilder().mergeFrom(this);
    }

    public Optional<String> getAlert() {
        return alert;
    }

    public Optional<ImmutableSet<DeviceType>> getOverrideDeviceTypes() {
        if (deviceTypePayloadOverrides == null || deviceTypePayloadOverrides.size() == 0 ) {
            return Optional.empty();
        } else {
            ImmutableSet.Builder<DeviceType> builder = ImmutableSet.builder();
            for (NotificationPayloadOverrideKey key : deviceTypePayloadOverrides.keySet()) {
                builder.add(key.getDeviceType());
            }
            return Optional.of(builder.build());
        }
    }

    @SuppressWarnings("unchecked")
    public <O extends DevicePayloadOverride> Optional<O> getDeviceTypeOverride(DeviceType deviceType, Class<O> overrideType) {
        // Safe because the builder enforces the tie between the Class key and the value in the map
        return Optional.ofNullable((O) deviceTypePayloadOverrides.get(new NotificationPayloadOverrideKey(deviceType, overrideType)));
    }

    public Map<DeviceType, DevicePayloadOverride> getDeviceTypePayloadOverrides() {
        Map<DeviceType, DevicePayloadOverride> overrides = Maps.newHashMap();
        for (Map.Entry<NotificationPayloadOverrideKey, ? extends DevicePayloadOverride> entry : deviceTypePayloadOverrides.entrySet()) {
            overrides.put(entry.getKey().getDeviceType(), entry.getValue());
        }

        return overrides;
    }

    public Optional<Actions> getActions() {
        return this.actions;
    }

    public Optional<Interactive> getInteractive() {
        return this.interactive;
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

        return Objects.equal(alert, that.alert)
                && Objects.equal(deviceTypePayloadOverrides, that.deviceTypePayloadOverrides)
                && Objects.equal(actions, that.actions)
                && Objects.equal(interactive, that.interactive);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert, deviceTypePayloadOverrides, actions, interactive);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("alert", alert)
                .add("deviceTypePayloadOverrides", deviceTypePayloadOverrides)
                .add("actions", actions)
                .add("interactive", interactive)
                .toString();
    }

    public static class Builder {

        private final ImmutableMap.Builder<NotificationPayloadOverrideKey, DevicePayloadOverride> deviceTypePayloadOverridesBuilder = ImmutableMap.builder();
        private final ActionNameRegistry registry;
        private String alert = null;
        private Actions actions = null;
        private Interactive interactive = null;

        private Builder(ActionNameRegistry registry) {
            this.registry = registry;
        }

        public Builder mergeFrom(Notification other) {
            if (other.getAlert().isPresent()) {
                setAlert(other.getAlert().get());
            }
            if (other.getActions().isPresent()) {
                setActions(other.getActions().get());
            }
            if (other.getInteractive().isPresent()) {
                setInteractive(other.getInteractive().get());
            }
             this.deviceTypePayloadOverridesBuilder.putAll(other.deviceTypePayloadOverrides);
            return this;
        }

        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public <P extends DevicePayloadOverride> Builder addDeviceTypeOverride(DeviceType deviceType, P payload) {
            this.deviceTypePayloadOverridesBuilder.put(new NotificationPayloadOverrideKey(deviceType, payload.getClass()), payload);
            return this;
        }

        public Builder setActions(Actions actions) {
            this.actions = actions;
            return this;
        }

        public Builder setInteractive(Interactive interactive) {
            this.interactive = interactive;
            return this;
        }

        public Notification build() {
            ImmutableMap<NotificationPayloadOverrideKey, DevicePayloadOverride> overrides = deviceTypePayloadOverridesBuilder.build();
            Preconditions.checkArgument(alert != null || !overrides.isEmpty(),
                    "Must either specify default notification keys or at least a single deviceType override");

            IOSDevicePayload iOSOverride = (IOSDevicePayload) overrides.get(new NotificationPayloadOverrideKey(DeviceType.IOS, IOSDevicePayload.class));
            if (actions != null && iOSOverride != null && iOSOverride.getExtra().isPresent()) {
                Map<String, String> extras = iOSOverride.getExtra().get();
                for (Action a : actions.allActions()) {

                    if (a.getActionType() == ActionType.APP_DEFINED) {
                        Iterator<String> fieldNames = ((AppDefinedAction) a).getValue().fieldNames();
                        while (fieldNames.hasNext()) {
                            String field = fieldNames.next();
                            Preconditions.checkArgument(!extras.containsKey(field), "The iOS extra key '" + field
                                    + "' cannot be present when an app_defined action field of the same name is present");
                        }
                    } else {
                        String shortName = registry.getShortName(a.getActionType());
                        String longName = registry.getLongName(a.getActionType());
                        String fieldName = registry.getFieldName(a.getActionType());

                        Preconditions.checkArgument(!extras.containsKey(shortName), "The iOS extras key '" + shortName + "' cannot be present when the action '" +
                                fieldName + "' is present");

                        Preconditions.checkArgument(!extras.containsKey(longName), "The iOS extras key '" + longName + "' cannot be present when the action '" +
                                fieldName + "' is present");
                    }
                }
            }

            return new Notification(
                    Optional.ofNullable(alert),
                    overrides,
                    Optional.ofNullable(actions),
                    Optional.ofNullable(interactive)
            );
        }
    }
}
