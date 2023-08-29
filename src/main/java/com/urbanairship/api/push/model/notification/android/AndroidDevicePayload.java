/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.android;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;

import java.util.Map;
import java.util.Optional;

public final class AndroidDevicePayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<String> alert;
    private final Optional<String> collapseKey;
    private final Optional<String> notificationChannel;
    private final Optional<String> notificationTag;
    private final Optional<PushExpiry> timeToLive;
    private final Optional<String> deliveryPriority;
    private final Optional<Boolean> delayWhileIdle;
    private final Optional<ImmutableMap<String, String>> extra;
    private final Optional<Interactive> interactive;
    private final Optional<String> title;
    private final Optional<Boolean> localOnly;
    private final Optional<Wearable> wearable;
    private final Optional<String> summary;
    private final Optional<Style> style;
    private final Optional<String> sound;
    private final Optional<String> icon;
    private final Optional<String> iconColor;
    // Android L features
    private final Optional<Integer> priority;
    private final Optional<Category> category;
    private final Optional<Integer> visibility;
    private final Optional<PublicNotification> publicNotification;
    private final Optional<Actions> actions;
    private final Optional<AndroidTemplate> template;
    private final Optional<AndroidLiveUpdate> androidLiveUpdate;

    private AndroidDevicePayload(Builder builder) {
        this.alert = Optional.ofNullable(builder.alert);
        this.collapseKey = Optional.ofNullable(builder.collapseKey);
        this.notificationChannel = Optional.ofNullable(builder.notificationChannel);
        this.notificationTag = Optional.ofNullable(builder.notificationTag);
        this.timeToLive = Optional.ofNullable(builder.timeToLive);
        this.deliveryPriority = Optional.ofNullable(builder.deliveryPriority);
        this.delayWhileIdle = Optional.ofNullable(builder.delayWhileIdle);
        if (builder.extra.build().isEmpty()) {
            this.extra = Optional.empty();
        } else {
            this.extra = Optional.of(builder.extra.build());
        }
        this.interactive = Optional.ofNullable(builder.interactive);
        this.title = Optional.ofNullable(builder.title);
        this.localOnly = Optional.ofNullable(builder.localOnly);
        this.wearable = Optional.ofNullable(builder.wearable);
        this.summary = Optional.ofNullable(builder.summary);
        this.style = Optional.ofNullable(builder.style);
        this.sound = Optional.ofNullable(builder.sound);
        this.icon = Optional.ofNullable(builder.icon);
        this.iconColor = Optional.ofNullable(builder.iconColor);
        this.priority = Optional.ofNullable(builder.priority);
        this.category = Optional.ofNullable(builder.category);
        this.visibility = Optional.ofNullable(builder.visibility);
        this.publicNotification = Optional.ofNullable(builder.publicNotification);
        this.actions = Optional.ofNullable(builder.actions);
        this.template = Optional.ofNullable(builder.template);
        this.androidLiveUpdate = Optional.ofNullable(builder.androidLiveUpdate);
    }

    /**
     * New AndroidDevicePayload Builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the device type.
     *
     * @return DeviceType.ANDROID
     */
    @Override
    public DeviceType getDeviceType() {
        return DeviceType.ANDROID;
    }

    /**
     * Get the alert.
     *
     * @return Optional String alert
     */
    @Override
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Get the collapse key.
     *
     * @return Optional String collapse key
     */
    public Optional<String> getCollapseKey() {
        return collapseKey;
    }

    /**
     * Get the notification channel.
     *
     * @return Optional String notification channel
     */
    public Optional<String> getNotificationChannel() {
        return notificationChannel;
    }

    /**
     * Get the notification tag.
     *
     * @return Optional String notification tag
     */
    public Optional<String> getNotificationTag() {
        return notificationTag;
    }

    /**
     * Get the push expiry.
     *
     * @return Optional PushExpiry object
     */
    public Optional<PushExpiry> getTimeToLive() {
        return timeToLive;
    }

    /**
     * Get the delivery priority.
     *
     * @return Optional String
     */
    public Optional<String> getDeliveryPriority() {
        return deliveryPriority;
    }

    /**
     * Get the delay while idle flag.
     *
     * @return Optional boolean delay while idle
     */
    public Optional<Boolean> getDelayWhileIdle() {
        return delayWhileIdle;
    }

    /**
     * Get an extra mapping of key-value pairs.
     *
     * @return Optional ImmutableMap of Strings
     */
    public Optional<ImmutableMap<String, String>> getExtra() {
        return extra;
    }

    /**
     * Get the interactive notification payload.
     *
     * @return Optional Interactive object
     */
    public Optional<Interactive> getInteractive() {
        return interactive;
    }

    /**
     * Get the title string.
     *
     * @return Optional String title
     */
    public Optional<String> getTitle() {
        return title;
    }

    /**
     * Get the local only flag.
     *
     * @return Optional Boolean local only flag.
     */
    public Optional<Boolean> getLocalOnly() {
        return localOnly;
    }

    /**
     * Get the wearable payload.
     *
     * @return Optional Wearable object.
     */
    public Optional<Wearable> getWearable() {
        return wearable;
    }

    /**
     * Get the summary string.
     *
     * @return Optional String summary
     */
    public Optional<String> getSummary() {
        return summary;
    }

    /**
     * Get the style payload.
     *
     * @return Optional Style object
     */
    public Optional<Style> getStyle() {
        return style;
    }

    /**
     * Get the sound string.
     *
     * @return Optional String sound
     */
    public Optional<String> getSound() {
        return sound;
    }

    /**
     * Get the icon string.
     *
     * @return Optional String icon
     */
    public Optional<String> getIcon() {
        return icon;
    }

    /**
     * Get the icon color string.
     *
     * @return Optional String icon color
     */
    public Optional<String> getIconColor() {
        return iconColor;
    }

    /**
     * Get the priority specifier.
     *
     * @return Optional integer between -2 and 2.
     */
    public Optional<Integer> getPriority() {
        return priority;
    }

    /**
     * Get the category specifier.
     *
     * @return Optional Category object.
     */
    public Optional<Category> getCategory() {
        return category;
    }

    /**
     * Get the visibility specifier.
     *
     * @return Optional integer between -1 and 1
     */
    public Optional<Integer> getVisibility() {
        return visibility;
    }

    /**
     * Get the public notification payload.
     *
     * @return Optional PublicNotification object.
     */
    public Optional<PublicNotification> getPublicNotification() {
        return publicNotification;
    }

    /**
     * Get the actions object that describes Actions to be performed by the SDK when a user interacts with the notification.
     *
     * @return Optional Actions object.
     */
    public Optional<Actions> getActions() {
        return actions;
    }

    /**
     * Get the template with android-specific message.
     *
     * @return Optional AndroidTemplate
     */
    public Optional<AndroidTemplate> getTemplate() {
        return template;
    }

    /**
     * Get the live update with android-specific message.
     *
     * @return Optional AndroidLiveUpdate
     */
    public Optional<AndroidLiveUpdate> getAndroidLiveUpdate() {
        return androidLiveUpdate;
    }

    @Override
    public String toString() {
        return "AndroidDevicePayload{" +
                "alert=" + alert +
                ", collapseKey=" + collapseKey +
                ", notificationChannel=" + notificationChannel +
                ", notificationTag=" + notificationTag +
                ", timeToLive=" + timeToLive +
                ", delayWhileIdle=" + delayWhileIdle +
                ", deliveryPriority=" + deliveryPriority +
                ", extra=" + extra +
                ", interactive=" + interactive +
                ", title=" + title +
                ", localOnly=" + localOnly +
                ", wearable=" + wearable +
                ", summary=" + summary +
                ", style=" + style +
                ", sound=" + sound +
                ", icon=" + icon +
                ", iconColor=" + iconColor +
                ", priority=" + priority +
                ", category=" + category +
                ", visibility=" + visibility +
                ", actions=" + actions +
                ", publicNotification=" + publicNotification +
                ", template=" + template +
                ", androidLiveUpdate=" + androidLiveUpdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AndroidDevicePayload that = (AndroidDevicePayload) o;

        if (!alert.equals(that.alert)) return false;
        if (!category.equals(that.category)) return false;
        if (!collapseKey.equals(that.collapseKey)) return false;
        if (!notificationChannel.equals(that.notificationChannel)) return false;
        if (!notificationTag.equals(that.notificationTag)) return false;
        if (!delayWhileIdle.equals(that.delayWhileIdle)) return false;
        if (!deliveryPriority.equals(that.deliveryPriority)) return false;
        if (!extra.equals(that.extra)) return false;
        if (!interactive.equals(that.interactive)) return false;
        if (!localOnly.equals(that.localOnly)) return false;
        if (!priority.equals(that.priority)) return false;
        if (!style.equals(that.style)) return false;
        if (!sound.equals(that.sound)) return false;
        if (!icon.equals(that.icon)) return false;
        if (!iconColor.equals(that.iconColor)) return false;
        if (!summary.equals(that.summary)) return false;
        if (!timeToLive.equals(that.timeToLive)) return false;
        if (!title.equals(that.title)) return false;
        if (!visibility.equals(that.visibility)) return false;
        if (!wearable.equals(that.wearable)) return false;
        if (!publicNotification.equals(that.publicNotification)) return false;
        if (!actions.equals(that.actions)) return false;
        if (!template.equals(that.template)) return false;
        if (!androidLiveUpdate.equals(that.androidLiveUpdate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = alert.hashCode();
        result = 31 * result + collapseKey.hashCode();
        result = 31 * result + notificationChannel.hashCode();
        result = 31 * result + notificationTag.hashCode();
        result = 31 * result + timeToLive.hashCode();
        result = 31 * result + delayWhileIdle.hashCode();
        result = 31 * result + deliveryPriority.hashCode();
        result = 31 * result + extra.hashCode();
        result = 31 * result + interactive.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + localOnly.hashCode();
        result = 31 * result + wearable.hashCode();
        result = 31 * result + summary.hashCode();
        result = 31 * result + style.hashCode();
        result = 31 * result + sound.hashCode();
        result = 31 * result + icon.hashCode();
        result = 31 * result + iconColor.hashCode();
        result = 31 * result + priority.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + visibility.hashCode();
        result = 31 * result + publicNotification.hashCode();
        result = 31 * result + actions.hashCode();
        result = 31 * result + template.hashCode();
        result = 31 * result + androidLiveUpdate.hashCode();

        return result;
    }

    public static class Builder {
        private String alert = null;
        private String collapseKey = null;
        private String notificationChannel = null;
        private String notificationTag = null;
        private PushExpiry timeToLive = null;
        private Boolean delayWhileIdle = null;
        private String deliveryPriority = null;
        private ImmutableMap.Builder<String, String> extra = ImmutableMap.builder();
        private Interactive interactive = null;
        private String title = null;
        private Boolean localOnly = null;
        private Wearable wearable = null;
        private String summary = null;
        private Style style = null;
        private String sound = null;
        private String icon = null;
        private String iconColor = null;
        // Android L features
        private Integer priority = null;
        private Category category = null;
        private Integer visibility = null;
        private PublicNotification publicNotification = null;
        private Actions actions;
        private AndroidTemplate template;
        private AndroidLiveUpdate androidLiveUpdate;

        private Builder() { }

        /**
         * Set the alert string.
         *
         * @param alert String
         * @return Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Set the collapse key string.
         *
         * @param collapseKey String
         * @return Builder
         */
        public Builder setCollapseKey(String collapseKey) {
            this.collapseKey = collapseKey;
            return this;
        }

        /**
         * Set the notification channel string.
         *
         * @param notificationChannel String
         * @return Builder
         */
        public Builder setNotificationChannel(String notificationChannel) {
            this.notificationChannel = notificationChannel;
            return this;
        }

        /**
         * Set the notification tag string.
         *
         * @param notificationTag String
         * @return Builder
         */
        public Builder setNotificationTag(String notificationTag) {
            this.notificationTag = notificationTag;
            return this;
        }

        /**
         * Set the push expiry.
         *
         * @param value PushExpiry
         * @return Builder
         */
        public Builder setTimeToLive(PushExpiry value) {
            this.timeToLive = value;
            return this;
        }

        /**
         * Set the delivery priority.
         *
         * @param deliveryPriority String
         * @return Builder
         */
        public Builder setDeliveryPriority(String deliveryPriority) {
            Preconditions.checkArgument(
                    deliveryPriority.equals("high") || deliveryPriority.equals("normal"),
                    "Delivery priority must be one of \"high\" or \"normal\"."
            );
            this.deliveryPriority = deliveryPriority;
            return this;
        }

        /**
         * Set the delay while idle flag.
         *
         * @param value boolean
         * @return Builder
         */
        public Builder setDelayWhileIdle(boolean value) {
            this.delayWhileIdle = value;
            return this;
        }

        /**
         * Add an extra key-value pair.
         *
         * @param key String
         * @param value String
         * @return Builder
         */
        public Builder addExtraEntry(String key, String value) {
            this.extra.put(key, value);
            return this;
        }

        /**
         * Add a Map of key-value pairs.
         *
         * @param entries A Map of Strings
         * @return Builder
         */
        public Builder addAllExtraEntries(Map<String, String> entries) {
            this.extra.putAll(entries);
            return this;
        }

        /**
         * Set the interactive payload.
         *
         * @param value Interactive
         * @return Builder
         */
        public Builder setInteractive(Interactive value) {
            this.interactive = value;
            return this;
        }

        /**
         * Set the title string.
         *
         * @param value String
         * @return Builder
         */
        public Builder setTitle(String value) {
            this.title = value;
            return this;
        }

        /**
         * Set the local only flag.
         *
         * @param value Boolean
         * @return Builder
         */
        public Builder setLocalOnly(Boolean value) {
            this.localOnly = value;
            return this;
        }

        /**
         * Set the wearable payload.
         *
         * @param value Wearable
         * @return Builder
         */
        public Builder setWearable(Wearable value) {
            this.wearable = value;
            return this;
        }

        /**
         * Set the summary string.
         *
         * @param value String
         * @return Builder
         */
        public Builder setSummary(String value) {
            this.summary = value;
            return this;
        }

        /**
         * Set the style payload.
         *
         * @param value Style
         * @return Builder
         */
        public Builder setStyle(Style value) {
            this.style = value;
            return this;
        }

        /**
         * Set the sound string.
         *
         * @param sound String
         * @return Builder
         */
        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        /**
         * Set the icon string.
         *
         * @param icon String
         * @return Builder
         */
        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        /**
         * Set the icon color string.
         *
         * @param iconColor String
         * @return Builder
         */
        public Builder setIconColor(String iconColor) {
            this.iconColor = iconColor;
            return this;
        }

        /**
         * Set the priority specifier.
         *
         * @param value Integer
         * @return Builder
         */
        public Builder setPriority(Integer value) {
            Preconditions.checkArgument(value < 3 && value > -3, "priority must be an integer between -2 and 2");
            this.priority = value;
            return this;
        }

        /**
         * Set the category specifier.
         *
         * @param value Category
         * @return Builder
         */
        public Builder setCategory(Category value) {
            this.category = value;
            return this;
        }

        /**
         * Set the visibility specifier.
         *
         * @param value Integer
         * @return Builder
         */
        public Builder setVisibility(Integer value) {
            Preconditions.checkArgument(value < 2 && value > -2, "visibility must be an integer between -2 and 2");
            this.visibility = value;
            return this;
        }

        /**
         * Set the public notification payload.
         *
         * @param publicNotification PublicNotification
         * @return Builder
         */
        public Builder setPublicNotification(PublicNotification publicNotification) {
            this.publicNotification = publicNotification;
            return this;
        }

        /**
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
         * Set a template with android-specific message.
         *
         * @param template AndroidTemplate
         * @return Builder
         */
        public Builder setTemplate(AndroidTemplate template) {
            this.template = template;
            return this;
        }

        /**
         * Set an Android live update for the message.
         *
         * @param androidLiveUpdate AndroidTemplate
         * @return Builder
         */
        public Builder setAndroidLiveUpdate(AndroidLiveUpdate androidLiveUpdate) {
            this.androidLiveUpdate = androidLiveUpdate;
            return this;
        }

        /**
         * Build the AndroidDevicePayload object.
         *
         * @return AndroidDevicePayload
         */
        public AndroidDevicePayload build() {
            return new AndroidDevicePayload(this);
        }
    }
}
