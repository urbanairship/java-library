package com.urbanairship.api.push.model.notification;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.notification.actions.Actions;

public class Interactive {
    private final String type;
    private final ImmutableMap<String, Actions> buttonActions;

    private Interactive(String type, ImmutableMap<String, Actions> buttonActions) {
        this.type = type;
        this.buttonActions = buttonActions;
    }

    public String getType() {
        return type;
    }

    public ImmutableMap<String, Actions> getButtonActions() {
        return buttonActions;
    }

    public static Builder newBuilder() {
        return new Builder();
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
        return Objects.toStringHelper(getClass())
            .add("type", type)
            .add("buttonActions", buttonActions)
            .toString();
    }

    public static class Builder {
        private String type = null;
        private ImmutableMap<String, Actions> buttonActions = null;

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setButtonActions(ImmutableMap<String, Actions> buttonActions) {
            this.buttonActions = buttonActions;
            return this;
        }

        public Interactive build() {
            Preconditions.checkNotNull(type, "interactive payload requires a 'type' field");
            if (buttonActions == null) {
                buttonActions = ImmutableMap.of();
            }

            return new Interactive(type, buttonActions);
        }
    }
}

