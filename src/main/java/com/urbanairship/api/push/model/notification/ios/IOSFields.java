package com.urbanairship.api.push.model.notification.ios;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Objects;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

@JsonDeserialize(builder = IOSFields.Builder.class)
public class IOSFields extends PushModelObject {
    private final Optional<String> alert;
    private final Optional<String> subtitle;
    private final Optional<String> title;

    private IOSFields(Builder builder) {
        this.alert = Optional.ofNullable(builder.alert);
        this.subtitle = Optional.ofNullable(builder.subtitle);
        this.title = Optional.ofNullable(builder.title);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Set the alert.
     *
     * Alert override text for iOS devices.
     *
     * @return Optional String alert.
     */
    @JsonProperty("alert")
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Set the subtitle.
     *
     * A string that will display below the title of the notification. This is provided as a convenience for setting the subtitle in the alert JSON object.
     * If a subtitle is also defined in the alert JSON object, this value is ignored. iOS 10.
     *
     * @return Optional String subtitle.
     */
    @JsonProperty("subtitle")
    public Optional<String> getSubtitle() {
        return subtitle;
    }

    /**
     * Set the title.
     *
     * A short string describing the purpose of the notification. This is provided as a convenience for setting the title in the alert JSON object.
     * If a title is also defined in the alert JSON object, this value is ignored.
     *
     * @return Optional String title.
     */
    @JsonProperty("title")
    public Optional<String> getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IOSFields iosFields = (IOSFields) o;
        return Objects.equal(alert, iosFields.alert) &&
                Objects.equal(subtitle, iosFields.subtitle) &&
                Objects.equal(title, iosFields.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert, subtitle, title);
    }

    @Override
    public String toString() {
        return "IOSFields{" +
                "alert=" + alert +
                ", subtitle=" + subtitle +
                ", title=" + title +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        String alert = null;
        String subtitle = null;
        String title = null;

        /**
         * Set the alert.
         *
         * Alert override text for iOS devices.
         *
         * @param alert String
         * @return Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Set the subtitle.
         *
         * A string that will display below the title of the notification. This is provided as a convenience for setting the subtitle in the alert JSON object.
         * If a subtitle is also defined in the alert JSON object, this value is ignored. iOS 10.
         *
         * @param subtitle String
         * @return Builder
         */
        public Builder setSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        /**
         * Set the title.
         *
         * A short string describing the purpose of the notification. This is provided as a convenience for setting the title in the alert JSON object.
         * If a title is also defined in the alert JSON object, this value is ignored.
         *
         * @param title String
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public IOSFields build() {
            return new IOSFields(this);
        }
    }
}
