/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.interactive;

import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.DeviceType;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.urbanairship.api.push.model.notification.actions.*;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;

import java.util.Iterator;
import java.util.Map;

public final class Interactive extends PushModelObject {
    private final String type;

    private final Optional<Map<String, Actions>> buttonActions;

    private Interactive(String type, Optional<Map<String, Actions>> buttonActions) {
        this.type = type;
        this.buttonActions = buttonActions;
    }

    public static Builder newBuilder() {
        return new Builder(ActionNameRegistry.INSTANCE);
    }

    public Builder toBuilder() {
        return newBuilder().mergeFrom(this);
    }

    public String getType() {
        return type;
    }

    public Optional<Map<String, Actions>> getButtonActions() {
        return this.buttonActions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Interactive that = (Interactive) o;

        return Objects.equal(type, that.type)
            && Objects.equal(buttonActions, that.buttonActions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, buttonActions);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("type", type)
                .add("buttonActions", buttonActions)
                .toString();
    }

    public static class Builder {
        private final ActionNameRegistry registry;
        private String type = null;
        private Map<String, Actions> buttonActions = null;

        private Builder(ActionNameRegistry registry) {
            this.registry = registry;
        }

        public Builder mergeFrom(Interactive other) {
            setType(other.getType());
            if (other.getButtonActions().isPresent()) {
                setButtonActions(other.getButtonActions().get());
            }
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setButtonActions(Map<String, Actions> buttonActions) {
            this.buttonActions = buttonActions;
            return this;
        }

        public Interactive build() {
            Preconditions.checkArgument(type != null, "Must specify the type of the interactive field");

            return new Interactive(
                    type,
                    Optional.fromNullable(buttonActions)
            );
        }
    }
}
