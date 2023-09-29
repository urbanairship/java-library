package com.urbanairship.api.push.model.localization;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;

import java.util.Optional;

/**
 * Represents a localized message.
 */
public class Localization extends PushModelObject {
    private final Optional<String> country;
    private final Optional<String> language;
    private final Optional<InApp> inApp;
    private final Optional<RichPushMessage> richPushMessage;
    private final Optional<Notification> notification;

    private Localization(Builder builder) {
        this.country = Optional.ofNullable(builder.country);
        this.language = Optional.ofNullable(builder.language);
        this.inApp = Optional.ofNullable(builder.inApp);
        this.richPushMessage = Optional.ofNullable(builder.richPushMessage);
        this.notification = Optional.ofNullable(builder.notification);
    }

    /**
     * New Localization Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the ISO 3166-2 two digit country code for this localization.
     * @return country String
     */
    public Optional<String> getCountry() {
        return country;
    }

    /**
     * Get the ISO 639-2 two digit language code for this localization.
     * @return language String
     */
    public Optional<String> getLanguage() {
        return language;
    }

    /**
     * Get the InApp object describing an in-app message payload.
     * @return Optional InApp inApp
     */
    public Optional<InApp> getInApp() {
        return inApp;
    }

    /**
     * Get the Message Center message.
     * @return Optional RichPushMessage richPushMessage
     */
    public Optional<RichPushMessage> getRichPushMessage() {
        return richPushMessage;
    }

    /**
     * Get the notification payload.
     * @return Optional Notification notification
     */
    public Optional<Notification> getNotification() {
        return notification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localization that = (Localization) o;
        return Objects.equal(country, that.country) &&
                Objects.equal(language, that.language) &&
                Objects.equal(inApp, that.inApp) &&
                Objects.equal(richPushMessage, that.richPushMessage) &&
                Objects.equal(notification, that.notification);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(country, language, inApp, richPushMessage, notification);
    }

    public static class Builder {
        private String country;
        private String language;
        private Notification notification;
        private InApp inApp;
        private RichPushMessage richPushMessage;

        private Builder() {

        }

        /**
         * The ISO 3166-2 two digit country code for this localization.
         * @param country String
         * @return Localization Builder
         */
        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        /**
         * The ISO 639-2 two digit language code for this localization.
         * @param language String
         * @return Localization Builder
         */
        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        /**
         * Set the notification payload that is required unless either message or in_app is present.
         * You can provide an alert and any platform overrides that apply to the device_type platforms you specify.
         * @param notification Notification
         * @return Localization Builder
         */
        public Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }

        /**
         * Set InApp object describing an in-app message payload.
         * @param inApp InApp
         * @return Localization Builder
         */
        public Builder setInApp(InApp inApp) {
            this.inApp = inApp;
            return this;
        }

        /**
         * Set the Message Center message.
         * @param richPushMessage RichPushMessage
         * @return Localization Builder
         */
        public Builder setRichPushMessage(RichPushMessage richPushMessage) {
            this.richPushMessage = richPushMessage;
            return this;
        }

        public Localization build() {
            Preconditions.checkArgument(language != null || country != null, "Language or country must be set.");
            return new Localization(this);
        }
    }
}
