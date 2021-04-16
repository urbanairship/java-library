package com.urbanairship.api.push.model.notification.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Objects;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

@JsonDeserialize(builder = WebFields.Builder.class)
public class WebFields extends PushModelObject {
    private final Optional<String> alert;
    private final Optional<WebIcon> webIcon;
    private final Optional<String> title;

    private WebFields(Builder builder) {
        this.alert = Optional.ofNullable(builder.alert);
        this.webIcon = Optional.ofNullable(builder.icon);
        this.title = Optional.ofNullable(builder.title);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the alert.
     *
     * Alert override text for Web devices.
     *
     * @return Optional String
     */
    @JsonProperty("alert")
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Get the icon.
     *
     * A object that describes a icon to be used with the web alert.
     *
     * @return Optional WebIcon
     */
    @JsonProperty("icon")
    public Optional<WebIcon> getWebIcon() {
        return webIcon;
    }

    /**
     * Set the title.
     *
     * A string representing the title of the notification.
     *
     * @return Optional String
     */
    @JsonProperty("title")
    public Optional<String> getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebFields webFields = (WebFields) o;
        return Objects.equal(alert, webFields.alert) &&
                Objects.equal(webIcon, webFields.webIcon) &&
                Objects.equal(title, webFields.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert, webIcon, title);
    }

    @Override
    public String toString() {
        return "WebFields{" +
                "alert=" + alert +
                ", webIcon=" + webIcon +
                ", title=" + title +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        String alert = null;
        WebIcon icon = null;
        String title = null;

        /**
         * Set the alert.
         *
         * Alert override text for Web devices.
         *
         * @param alert String
         * @return Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Set the icon.
         *
         * A object that describes a icon to be used with the web alert.
         *
         * @param icon WebIcon
         * @return Builder
         */
        @JsonProperty("icon")
        public Builder setWebIcon(WebIcon icon) {
            this.icon = icon;
            return this;
        }

        /**
         * Set the title.
         *
         * A string representing the title of the notification.
         *
         * @param title String
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public WebFields build() {
            return new WebFields(this);
        }
    }
}
