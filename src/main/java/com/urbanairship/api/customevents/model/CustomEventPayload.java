package com.urbanairship.api.customevents.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;
import org.joda.time.DateTime;

public class CustomEventPayload extends PushModelObject {
    private final CustomEventOccurred customEventOccurred;
    private final CustomEventBody customEventBody;
    private final CustomEventUser customEventUser;

    private CustomEventPayload(Builder builder) {
        this.customEventOccurred = builder.customEventOccurred;
        this.customEventBody = builder.customEventBody;
        this.customEventUser = builder.customEventUser;
    }

    /**
     * New CustomEventPayload Builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the date and time when the event occurred.
     *
     * @return DateTime
     */
    public CustomEventOccurred getCustomEventOccurred() {
        return customEventOccurred;
    }

    /**
     * Set the body object which describes the user action.
     *
     * @return CustomEventBody
     */
    public CustomEventBody getCustomEventBody() {
        return customEventBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomEventPayload payload = (CustomEventPayload) o;

        return Objects.equal(customEventOccurred, payload.customEventOccurred) &&
                Objects.equal(customEventBody, payload.customEventBody) &&
                Objects.equal(customEventUser, payload.customEventUser);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customEventOccurred, customEventBody, customEventUser);
    }

    /**
     * Get the CustomEventUser that is an object containing the
     * Urban Airship channel identifier for the user who triggered the event.
     *
     * @return CustomEventUser

     */
    public CustomEventUser getCustomEventUser() {
        return customEventUser;
    }


    /**
     * CustomEventPayload Builder.
     */
    public static class Builder {
        private CustomEventOccurred customEventOccurred = null;
        private CustomEventBody customEventBody = null;
        private CustomEventUser customEventUser = null;

        /**
         * Set the date and time when the event occurred.
         *
         * @param customEventOccurred DateTime
         * @return CustomEventPayload Builder
         */
        public Builder setCustomEventOccurred(CustomEventOccurred customEventOccurred) {
            this.customEventOccurred = customEventOccurred;
            return this;
        }

        /**
         * Set the body object which describes the user action.
         *
         * @param customEventBody CustomEventBody
         * @return CustomEventPayload Builder
         */
        public Builder setCustomEventBody(CustomEventBody customEventBody) {
            this.customEventBody = customEventBody;
            return this;
        }

        /**
         * Set the CustomEventUser that is an object containing the
         * Urban Airship channel identifier for the user who triggered the event.
         *
         * @param customEventUser CustomEventUser
         * @return CustomEventPayload Builder
         */
        public Builder setCustomEventUser(CustomEventUser customEventUser) {
            this.customEventUser = customEventUser;
            return this;
        }

        public CustomEventPayload build() {
            Preconditions.checkNotNull(customEventOccurred, "'customEventOccurred' must not be null");
            Preconditions.checkNotNull(customEventBody, "'customEventBody' must not be null");
            Preconditions.checkNotNull(customEventUser, "'customEventUser' must not be null");

            return new CustomEventPayload(this);
        }
    }
}
