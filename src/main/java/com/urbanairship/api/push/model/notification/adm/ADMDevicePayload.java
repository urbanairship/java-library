/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.adm;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.android.Style;

import java.util.Map;

public final class ADMDevicePayload extends PushModelObject implements DevicePayloadOverride {
    private final Optional<String> alert;
    private final Optional<String> consolidationKey;
    private final Optional<PushExpiry> expiresAfter;
    private final Optional<ImmutableMap<String, String>> extra;
    private final Optional<Interactive> interactive;
    private final Optional<Actions> actions;
    private final Optional<String> icon;
    private final Optional<String> iconColor;
    private final Optional<String> notificationChannel;
    private final Optional<String> notificationTag;
    private final Optional<String> sound;
    private final Optional<String> summary;
    private final Optional<String> title;
    private final Optional<Style> style;
    private final Optional<ADMTemplate> template;

    private ADMDevicePayload(Builder builder) {
        this.alert = Optional.fromNullable(builder.alert);
        this.consolidationKey = Optional.fromNullable(builder.consolidationKey);
        this.expiresAfter = Optional.fromNullable(builder.expiresAfter);
        if (builder.extra.build().isEmpty()) {
            this.extra = Optional.absent();
        } else {
            this.extra = Optional.of(builder.extra.build());
        }
        this.interactive = Optional.fromNullable(builder.interactive);
        this.actions = Optional.fromNullable(builder.actions);
        this.icon = Optional.fromNullable(builder.icon);
        this.iconColor = Optional.fromNullable(builder.iconColor);
        this.notificationChannel = Optional.fromNullable(builder.notificationChannel);
        this.notificationTag = Optional.fromNullable(builder.notificationTag);
        this.sound = Optional.fromNullable(builder.sound);
        this.summary = Optional.fromNullable(builder.summary);
        this.title = Optional.fromNullable(builder.title);
        this.style = Optional.fromNullable(builder.style);
        this.template = Optional.fromNullable(builder.template);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DeviceType getDeviceType() {
        return DeviceType.AMAZON;
    }

    @Override
    public Optional<String> getAlert() {
        return alert;
    }

    public Optional<String> getConsolidationKey() {
        return consolidationKey;
    }

    public Optional<PushExpiry> getExpiresAfter() {
        return expiresAfter;
    }

    public Optional<ImmutableMap<String, String>> getExtra() {
        return extra;
    }

    public Optional<Interactive> getInteractive() {
        return interactive;
    }

    /**
     * Get the Actions.
     * Describes Actions to be performed by the SDK when a user interacts with the notification.
     *
     * @return Optional Actions
     */
    public Optional<Actions> getActions() {
        return actions;
    }

    /**
     * Get the string representing an image file included in the application’s resources.
     *
     * @return Optional String
     */
    public Optional<String> getIcon() {
        return icon;
    }

    /**
     * Get the string representing the icon color in API Color Format. i.e. ‘#rrggbb’
     *
     * @return Optional String
     */
    public Optional<String> getIconColor() {
        return iconColor;
    }

    /**
     * Get the string representing a notification channel. Used to group notifications with similar behavior.
     *
     * @return Optional String
     */
    public Optional<String> getNotificationChannel() {
        return notificationChannel;
    }

    /**
     * Get the string representing a notification tag.
     * If a message is currently displaying and has a tag set, sending another message with the same tag will replace the posted notification.
     *
     * @return Optional String
     */
    public Optional<String> getNotificationTag() {
        return notificationTag;
    }

    /**
     * Get the string representing a sound file name included in the application’s resources.
     *
     * @return Optional String
     */
    public Optional<String> getSound() {
        return sound;
    }

    /**
     * Get the string representing a summary/subtitle of the notification.
     *
     * @return Optional String
     */
    public Optional<String> getSummary() {
        return summary;
    }

    /**
     * Get the string representing the title of the notification. The default value is the name of the app.
     *
     * @return Optional String
     */
    public Optional<String> getTitle() {
        return title;
    }

    /**
     * Get the style.
     * Advanced styles big text, big picture, and inbox are available on Android 4.3+
     * by adding the style to the platform specific notification payload on Android platforms.
     *
     * @return Optional String
     */
    public Optional<Style> getStyle() {
        return style;
    }

    /**
     * Get the template with amazon-specific message.
     *
     * @return Optional ADMTemplate
     */
    public Optional<ADMTemplate> getTemplate() {
        return template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ADMDevicePayload that = (ADMDevicePayload) o;
        return Objects.equal(alert, that.alert) &&
                Objects.equal(consolidationKey, that.consolidationKey) &&
                Objects.equal(expiresAfter, that.expiresAfter) &&
                Objects.equal(extra, that.extra) &&
                Objects.equal(interactive, that.interactive) &&
                Objects.equal(actions, that.actions) &&
                Objects.equal(icon, that.icon) &&
                Objects.equal(iconColor, that.iconColor) &&
                Objects.equal(notificationChannel, that.notificationChannel) &&
                Objects.equal(notificationTag, that.notificationTag) &&
                Objects.equal(sound, that.sound) &&
                Objects.equal(summary, that.summary) &&
                Objects.equal(title, that.title) &&
                Objects.equal(style, that.style) &&
                Objects.equal(template, that.template);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(alert, consolidationKey, expiresAfter, extra, interactive, actions, icon, iconColor, notificationChannel, notificationTag, sound, summary, title, style, template);
    }

    public static class Builder {
        private String alert = null;
        private String consolidationKey = null;
        private PushExpiry expiresAfter = null;
        private ImmutableMap.Builder<String, String> extra = ImmutableMap.builder();
        private Interactive interactive = null;
        private Actions actions = null;
        private String icon = null;
        private String iconColor= null;
        private String notificationChannel = null;
        private String notificationTag = null;
        private String sound = null;
        private String summary = null;
        private String title = null;
        private Style style = null;
        private ADMTemplate template = null;

        private Builder() { }

        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        public Builder setConsolidationKey(String consolidationKey) {
            this.consolidationKey = consolidationKey;
            return this;
        }

        public Builder setExpiresAfter(PushExpiry value) {
            this.expiresAfter = value;
            return this;
        }

        public Builder addExtraEntry(String key, String value) {
            this.extra.put(key, value);
            return this;
        }

        public Builder addAllExtraEntries(Map<String, String> entries) {
            this.extra.putAll(entries);
            return this;
        }

        public Builder setInteractive(Interactive value) {
            this.interactive = value;
            return this;
        }

        /**
         * Set the Actions.
         * Describes Actions to be performed by the SDK when a user interacts with the notification.
         *
         * @return Optional Actions
         */
        public Builder setActions(Actions actions) {
            this.actions = actions;
            return this;
        }

        /**
         * Set the string representing an image file included in the application’s resources.
         *
         * @return Optional String
         */
        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        /**
         * Set the string representing the icon color in API Color Format. i.e. ‘#rrggbb’
         *
         * @return Optional String
         */
        public Builder setIconColor(String iconColor) {
            this.iconColor = iconColor;
            return this;
        }

        /**
         * Set the string representing a notification channel. Used to group notifications with similar behavior.
         *
         * @return Optional String
         */
        public Builder setNotificationChannel(String notificationChannel) {
            this.notificationChannel = notificationChannel;
            return this;
        }

        /**
         * Set the string representing a notification tag.
         * If a message is currently displaying and has a tag set, sending another message with the same tag will replace the posted notification.
         *
         * @return Optional String
         */
        public Builder setNotificationTag(String notificationTag) {
            this.notificationTag = notificationTag;
            return this;
        }

        /**
         * Set the string representing a sound file name included in the application’s resources.
         *
         * @return Optional String
         */
        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        /**
         * Set the string representing a summary/subtitle of the notification.
         *
         * @return Optional String
         */
        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        /**
         * Set the string representing the title of the notification. The default value is the name of the app.
         *
         * @return Optional String
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the style.
         * Advanced styles big text, big picture, and inbox are available on Android 4.3+
         * by adding the style to the platform specific notification payload on Android platforms.
         *
         * @return Optional String
         */
        public Builder setStyle(Style style) {
            this.style = style;
            return this;
        }

        /**
         * Set a template with amazon-specific message.
         *
         * @param template ADMTemplate
         * @return Builder
         */
        public Builder setTemplate(ADMTemplate template) {
            this.template = template;
            return this;
        }

        public ADMDevicePayload build() {
            return new ADMDevicePayload(this);
        }
    }
}
