package com.urbanairship.api.push.model.notification.web;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.notification.actions.Actions;

public class Button {
    private final Optional<Actions> actions;
    private final String id;
    private final String label;

    private Button(Builder builder) {
        actions = Optional.fromNullable(builder.actions);
        id = builder.id;
        label = builder.label;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the button actions.
     * Describes Actions to be performed by the SDK when a user interacts with the notification.
     *
     * @return Optional Actions
     */
    public Optional<Actions> getActions() {
        return actions;
    }

    /**
     * Set the id.
     * The “action” id on the service worker notification event that fires when your audience clicks the button.
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Get the button label.
     *
     * @return String label
     */
    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Button button = (Button) o;
        return Objects.equal(actions, button.actions) &&
                Objects.equal(id, button.id) &&
                Objects.equal(label, button.label);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actions, id, label);
    }

    public static class Builder {
        private Actions actions = null;
        private String id = null;
        private String label = null;

        /**
         * Set the button Actions
         * Describes Actions to be performed by the SDK when a user interacts with the notification.
         *
         * @param actions Actions
         * @return Builder
         */
        public Builder setActions(Actions actions) {
            this.actions = actions;
            return this;
        }

        /**
         * Set the button id.
         * The “action” id on the service worker notification event that fires when your audience clicks the button.
         *
         * @param id String id
         * @return Builder
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Set the button label.
         *
         * @param label String
         * @return Builder
         */
        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Button build() {
            Preconditions.checkNotNull(id, "Button id must be set");
            Preconditions.checkNotNull(label, "Button label must be set.");

            return new Button(this);
        }
    }
}
