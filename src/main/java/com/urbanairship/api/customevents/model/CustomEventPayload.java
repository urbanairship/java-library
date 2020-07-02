package com.urbanairship.api.customevents.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.Campaigns;
import com.urbanairship.api.push.model.PushModelObject;
import org.joda.time.DateTime;

public class CustomEventPayload extends PushModelObject {
    private final DateTime occurred;
    private final CustomEventBody customEventBody;
    private final CustomEventUser customEventUser;

    private CustomEventPayload(Builder builder) {
        this.occurred = builder.occurred;
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
    public DateTime getOccurred() {
        return occurred;
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

        return Objects.equal(occurred, payload.occurred) &&
                Objects.equal(customEventBody, payload.customEventBody) &&
                Objects.equal(customEventUser, payload.customEventUser);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(occurred, customEventBody, customEventUser);
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
        private DateTime occurred = null;
        private CustomEventBody customEventBody = null;
        private CustomEventUser customEventUser = null;

        /**
         * Set the date and time when the event occurred.
         *
         * @param occurred DateTime
         * @return CustomEventPayload Builder
         */
        public Builder setOccurred(DateTime occurred) {
            this.occurred = occurred;
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
            Preconditions.checkNotNull(occurred, "'occurred' must not be null");
            Preconditions.checkNotNull(customEventBody, "'customEventBody' must not be null");
            Preconditions.checkNotNull(customEventUser, "'customEventUser' must not be null");

            return new CustomEventPayload(this);
        }
    }
}
