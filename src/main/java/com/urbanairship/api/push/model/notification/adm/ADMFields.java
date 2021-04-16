package com.urbanairship.api.push.model.notification.adm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Objects;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

@JsonDeserialize(builder = ADMFields.Builder.class)
public class ADMFields extends PushModelObject {
    private final Optional<String> alert;
    private final Optional<String> icon;
    private final Optional<String> iconColor;
    private final Optional<String> summary;
    private final Optional<String> title;

    private ADMFields(Builder builder) {
        this.alert = Optional.ofNullable(builder.alert);
        this.icon = Optional.ofNullable(builder.icon);
        this.iconColor = Optional.ofNullable(builder.iconColor);
        this.summary = Optional.ofNullable(builder.summary);
        this.title = Optional.ofNullable(builder.title);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the alert.
     *
     * A string that override the alert value provided at the top level, if any.
     *
     * @return Optional String alert
     */
    @JsonProperty("alert")
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Get the icon.
     *
     * A string representing an image file included in the application’s resources.
     *
     * @return Optional String icon
     */
    @JsonProperty("icon")
    public Optional<String> getIcon() {
        return icon;
    }

    /**
     * Get the icon color.
     *
     * A string representing the icon color in API Color Format. i.e. ‘#rrggbb’
     *
     * @return Optional String iconColor
     */
    @JsonProperty("icon_color")
    public Optional<String> getIconColor() {
        return iconColor;
    }

    /**
     * Get the summary
     *
     * A string representing a summary/subtitle of the notification.
     *
     * @return Optional String summar
     */
    @JsonProperty("summary")
    public Optional<String> getSummary() {
        return summary;
    }

    /**
     * Get the title.
     *
     * A string representing the title of the notification. The default value is the name of the app.
     *
     * @return Optional String title
     */
    @JsonProperty("title")
    public Optional<String> getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ADMFields that = (ADMFields) o;
        return Objects.equal(alert, that.alert) &&
                Objects.equal(icon, that.icon) &&
                Objects.equal(iconColor, that.iconColor) &&
                Objects.equal(summary, that.summary) &&
                Objects.equal(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert, icon, iconColor, summary, title);
    }

    @Override
    public String toString() {
        return "AndroidFields{" +
                "alert=" + alert +
                ", icon=" + icon +
                ", iconColor=" + iconColor +
                ", summary=" + summary +
                ", title=" + title +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String alert = null;
        private String icon = null;
        private String iconColor = null;
        private String summary = null;
        private String title = null;

        /**
         * Set the alert.
         *
         * A string that override the alert value provided at the top level, if any.
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
         * A string representing an image file included in the application’s resources.
         *
         * @param icon String
         * @return Builder
         */
        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        /**
         * Set the icon color.
         *
         * A string representing the icon color in API Color Format. i.e. ‘#rrggbb’
         *
         * @param iconColor String
         * @return Builder
         */
        @JsonProperty("icon_color")
        public Builder setIconColor(String iconColor) {
            this.iconColor = iconColor;
            return this;
        }

        /**
         * Set the summary
         *
         * A string representing a summary/subtitle of the notification.
         *
         * @param summary String
         * @return Builder
         */
        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        /**
         * Set the title.
         *
         * A string representing the title of the notification. The default value is the name of the app.
         *
         * @param title String
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ADMFields build() {
            return new ADMFields(this);
        }
    }
}
