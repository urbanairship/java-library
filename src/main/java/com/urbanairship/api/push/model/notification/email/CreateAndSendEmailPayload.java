package com.urbanairship.api.push.model.notification.email;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.audience.CreateAndSendAudience;
import com.urbanairship.api.push.model.notification.Notification;

import java.util.Objects;

public class CreateAndSendEmailPayload {

    private final Optional<CreateAndSendAudience> audience;
    private final Optional<Notification> notification;


    private CreateAndSendEmailPayload(com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder builder) {
        this.audience = Optional.fromNullable(builder.audience);
        this.notification = Optional.fromNullable(builder.notification);

    }

    public static com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder newBuilder() {
        return new com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder();
    }



    /**
     * Optional, get the audience object for the payload
     *
     * @return Optional CreateAndSendAudience audience.
     */
    public Optional<CreateAndSendAudience> getAudience() {
        return audience;
    }

    /**
     * Optional, an object representing the notification.
     *
     * @return Optional Notification notification.
     */
    public Optional<Notification> getNotification() {
        return notification;
    }



    @Override
    public String toString() {
        return "CreateAndSendEmailPayload{" +
                "audience=" + audience +
                ", notification=" + notification +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload)) return false;
        com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload that = (com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload) o;
        return Objects.equals(getAudience(), that.getAudience()) &&
                Objects.equals(getNotification(), that.getNotification());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAudience(), getNotification());
    }

    /**
     * CreateAndSendEmail Builder.
     */
    public static class Builder {
        private CreateAndSendAudience audience;
        private Notification notification;

        private Builder() {
        }

        /**
         * Optional, CreateAndSendAudience object.
         *
         * @param audience Optional CreateAndSendAudience
         * @return CreateAndSendEmailPayload Builder
         */
        public com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder setAudience(CreateAndSendAudience audience) {
            this.audience = audience;
            return this;
        }

        /**
         * Optional Notification object
         *
         * @param notification Optional Notification
         * @return CreateAndSendEmailPayload Builder
         */
        public com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload.Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        public com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload build() {

            Preconditions.checkNotNull(notification, "Notification must be set.");

            Preconditions.checkNotNull(audience, "Audience must be set.");


            return new com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload(this);
        }
    }
}
