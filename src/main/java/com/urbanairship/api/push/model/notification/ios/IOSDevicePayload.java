/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Interactive;

import java.util.Map;
import java.util.Objects;

/**
 * IOSDevicePayload for iOS specific push messages.
 */
public final class IOSDevicePayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<IOSAlertData> alert;
    private final Optional<ImmutableMap<String, String>> extra;
    private final Optional<IOSBadgeData> badge;
    private final Optional<Boolean> contentAvailable;
    private final Optional<PushExpiry> expiry;
    private final Optional<Integer> priority;
    private final Optional<String> category;
    private final Optional<Interactive> interactive;
    private final Optional<String> title;
    private final Optional<String> subtitle;
    private final Optional<MediaAttachment> mediaAttachment;
    private final Optional<IOSSoundData> sound;
    private final Optional<Boolean> mutableContent;
    private final Optional<String> collapseId;
    private final Optional<String> threadId;

    private IOSDevicePayload(Optional<IOSAlertData> alert,
                             Optional<IOSBadgeData> badge,
                             Optional<Boolean> contentAvailable,
                             Optional<PushExpiry> expiry,
                             Optional<Integer> priority,
                             Optional<ImmutableMap<String, String>> extra,
                             Optional<String> category,
                             Optional<Interactive> interactive,
                             Optional<String> title,
                             Optional<String> subtitle,
                             Optional<MediaAttachment> mediaAttachment,
                             Optional<IOSSoundData> sound,
                             Optional<Boolean> mutableContent,
                             Optional<String> collapseId,
                             Optional<String> threadId) {
        this.alert = alert;
        this.badge = badge;
        this.contentAvailable = contentAvailable;
        this.extra = extra;
        this.expiry = expiry;
        this.priority = priority;
        this.category = category;
        this.interactive = interactive;
        this.title = title;
        this.subtitle = subtitle;
        this.mediaAttachment = mediaAttachment;
        this.sound = sound;
        this.mutableContent = mutableContent;
        this.collapseId = collapseId;
        this.threadId = threadId;
    }

    /**
     * Get a IOSDevicePayload Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the deviceType.
     * @return DeviceType
     */
    @Override
    public DeviceType getDeviceType() {
        return DeviceType.IOS;
    }

    /**
     * Get the alert if present.
     * @return Optional string representing the alert
     */
    @Override
    public Optional<String> getAlert() {
        return alert.isPresent() ? alert.get().getBody() : Optional.<String>absent();
    }

    /**
     * Get the IOSAlertData
     * @return Optional IOSAlertData
     */
    public Optional<IOSAlertData> getAlertData() {
        return alert;
    }

    /**
     * Get IOSBadgeData
     * @return Optional IOSBadgeData
     */
    public Optional<IOSBadgeData> getBadge() {
        return badge;
    }

    /**
     * Get the content available boolean value
     * @return Optional boolean representation of content available
     */
    public Optional<Boolean> getContentAvailable() {
        return contentAvailable;
    }

    /**
     * Get a Map of the extra key value pairs
     * @return Optional ImmutableMap of strings representing the key value pairs
     */
    public Optional<ImmutableMap<String, String>> getExtra() {
        return extra;
    }

    /**
     * Get the expiry (TTL) if present
     * @return Optional PushExpiry expiry value
     */
    public Optional<PushExpiry> getExpiry() {
        return  expiry;
    }

    /**
     * Get the priority value
     * @return Optional Integer representation of the priority
     */
    public Optional<Integer> getPriority() {
        return priority;
    }

    /**
     * Get the category if present
     * @return Optional string representation of the category
     */
    public Optional<String> getCategory() {
        return category;
    }

    /**
     * Get the Interactive data if present
     * @return Optional Interactive
     */
    public Optional<Interactive> getInteractive() {
        return interactive;
    }

    /**
     * Get the title if present.
     * @return Optional string representation of the title
     */
    public Optional<String> getTitle() {
        return title;
    }

    /**
     * Get the subtitle if present
     * @return Optional string representation of the subtitle
     */
    public Optional<String> getSubtitle() {
        return subtitle;
    }

    /**
     * Get the mutable content boolean value
     * @return Optional boolean representation of the mutable content
     */
    public Optional<Boolean> getMutableContent() {
        return mutableContent;
    }

    /**
     * Get the Media Attachment object that specifies a media attachment to be
     * handled by the UA Media Attachment Extension. iOS 10
     * @return Optional MediaAttachment object.
     */
    public Optional<MediaAttachment> getMediaAttachment() {
        return mediaAttachment;
    }

    /**
     * Get the IOSSoundData if present.
     * @return Optional IOSSoundData object
     */
    public Optional<IOSSoundData> getSoundData() { return sound; }


    /**
     * @deprecated The sound name can now be retrieved from getSoundData() as of 4.1.0.
     *
     * Gets the sound file name.
     * @return String sound file name
     */
    @Deprecated
    public Optional<String> getSound() {
        if (!sound.isPresent()) {
            return Optional.absent();
        }
        return sound.get().getName();
    }

    /**
     * Get the Collapse ID String. When there is a newer message that renders an older, related message irrelevant to the client app,
     * the new message replaces the older message with the same collapse ID. Similar to the GCM collapse key. iOS 10.
     * @return Optional String representation of the collapse ID.
     */
    public Optional<String> getCollapseId() {
        return collapseId;
    }

    /**
     * Get the thread ID.
     * @return Optional String representation of thread ID.
     */
    public Optional<String> getThreadId() {
        return threadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IOSDevicePayload that = (IOSDevicePayload) o;
        return Objects.equals(alert, that.alert) &&
                Objects.equals(extra, that.extra) &&
                Objects.equals(badge, that.badge) &&
                Objects.equals(contentAvailable, that.contentAvailable) &&
                Objects.equals(expiry, that.expiry) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(category, that.category) &&
                Objects.equals(interactive, that.interactive) &&
                Objects.equals(title, that.title) &&
                Objects.equals(subtitle, that.subtitle) &&
                Objects.equals(mediaAttachment, that.mediaAttachment) &&
                Objects.equals(sound, that.sound) &&
                Objects.equals(mutableContent, that.mutableContent) &&
                Objects.equals(collapseId, that.collapseId) &&
                Objects.equals(threadId, that.threadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alert, extra, badge, contentAvailable, expiry, priority, category, interactive, title, subtitle, mediaAttachment, sound, mutableContent, collapseId, threadId);
    }

    @Override
    public String toString() {
        return "IOSDevicePayload{" +
                "alert=" + alert +
                ", extra=" + extra +
                ", badge=" + badge +
                ", contentAvailable=" + contentAvailable +
                ", expiry=" + expiry +
                ", priority=" + priority +
                ", category=" + category +
                ", interactive=" + interactive +
                ", title=" + title +
                ", subtitle=" + subtitle +
                ", mediaAttachment=" + mediaAttachment +
                ", sound=" + sound +
                ", mutableContent=" + mutableContent +
                ", collapseId=" + collapseId +
                ", threadId=" + threadId +
                '}';
    }

    public static class Builder {
        private IOSAlertData alert = null;
        private IOSBadgeData badge = null;
        private Boolean contentAvailable = null;
        private ImmutableMap.Builder<String, String> extra = null;
        private PushExpiry expiry = null;
        private Integer priority = null;
        private String category = null;
        private Interactive interactive = null;
        private String title = null;
        private String subtitle = null;
        private MediaAttachment mediaAttachment = null;
        private IOSSoundData sound = null;
        private Boolean mutableContent = null;
        private String collapseId = null;
        private String threadId = null;

        private Builder() { }

        /**
         * Create an IOSAlertData object with the given alert string. This is a
         * shortcut for setting an alert data object when no additional iOS
         * APNS payload options are needed.
         *
         * @param alert String alert
         * @return Builder
         */
        public Builder setAlert(String alert) {
            this.alert = IOSAlertData.newBuilder()
                    .setBody(alert)
                    .build();
            return this;
        }

        /**
         * Set the IOSAlertData object.
         * @param alert IOSAlertData
         * @return Builder
         */
        public Builder setAlert(IOSAlertData alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Set the badge data.
         * @param badge IOSBadgeData
         * @return Builder
         */
        public Builder setBadge(IOSBadgeData badge) {
            this.badge = badge;
            return this;
        }

        /**
         * Set the flag indicating content availability.
         * @param value Boolean for content availability.
         * @return Builder
         */
        public Builder setContentAvailable(boolean value) {
            this.contentAvailable = value;
            return this;
        }

        /**
         * Set the expiry
         * @param value Integer
         * @return Builder
         **/
        public Builder setExpiry(PushExpiry value) {
            this.expiry = value;
            return this;
        }

        /**
         * Set the priority
         * @param value Integer
         * @return Builder
         **/
        public Builder setPriority(int value) {
            this.priority = value;
            return this;
        }

        /**
         * Add an extra key value pair to the notification payload. Maximum
         * payload is 2000 bytes.
         * @param key String key
         * @param value String value
         * @return Builder
         */
        public Builder addExtraEntry(String key, String value) {
            if (extra == null) {
                extra = ImmutableMap.builder();
            }
            this.extra.put(key, value);
            return this;
        }

        /**
         * Add key value pairs to payload. Maximum payload is 2000 bytes.
         * @param entries Map of key value pairs
         * @return Builder.
         */
        public Builder addAllExtraEntries(Map<String, String> entries) {
            if (extra == null) {
                extra = ImmutableMap.builder();
            }
            this.extra.putAll(entries);
            return this;
        }

        /**
         * Set the category
         * @param value String
         * @return Builder
         */
        public Builder setCategory(String value) {
            this.category = value;
            return this;
        }

        /**
         * Set the Interactive object
         * @param value Interactive
         * @return Builder
         */
        public Builder setInteractive(Interactive value) {
            this.interactive = value;
            return this;
        }

        /**
         * Set the title
         * @param value String
         * @return Builder
         */
        public Builder setTitle(String value) {
            this.title = value;
            return this;
        }

        public Builder setSubtitle(String value){
            this.subtitle = value;
            return this;
        }

        /**
         * Set the media attachment.
         * @param value MediaAttachment
         * @return Builder
         */
        public Builder setMediaAttachment(MediaAttachment value) {
            this.mediaAttachment = value;
            return this;
        }

        /**
         * Set the IOSSoundData object.
         *
         * @param value IOSSoundData
         * @return Builder
         */
        public Builder setSoundData(IOSSoundData value) {
            this.sound = value;
            return this;
        }

        /**
         * @deprecated This can now be set using setSoundData as of 4.1.0.
         *
         * Sets the name of the IOSSoundData object. This is a shortcut for when the only value pertaining to the 'sound' key is a sound file name.
         *
         * @param name String
         * @return Builder
         */
        @Deprecated
        public Builder setSound(String name) {
            this.sound = IOSSoundData.newBuilder()
                    .setName(name)
                    .build();
            return this;
        }

        /**
         * Set the mutable content
         * @param value Boolean
         * @return Builder
         */
        public Builder setMutableContent(Boolean value) {
            this.mutableContent = value;
            return this;
        }

        /**
         * Set the collapse ID. When there is a newer message that renders an older, related message irrelevant to the client app, the new message replaces the older message with the same collapse ID.
         * @param value String
         * @return Builder
         */
        public Builder setCollapseId(String value) {
            this.collapseId = value;
            return this;
        }

        /**
         * Set the thread ID.
         * @param value String
         * @return Builder
         */
        public Builder setThreadId(String value) {
            this.threadId = value;
            return this;
        }

        /**
         * Build IOSDevicePayload
         * @return IOSDevicePayload
         */
        public IOSDevicePayload build() {
            // Yes, empty payloads are valid (for Passes)
            return new IOSDevicePayload(Optional.fromNullable(alert),
                    Optional.fromNullable(badge),
                    Optional.fromNullable(contentAvailable),
                    Optional.fromNullable(expiry),
                    Optional.fromNullable(priority),
                    extra == null ? Optional.<ImmutableMap<String,String>>absent() : Optional.fromNullable(extra.build()),
                    Optional.fromNullable(category),
                    Optional.fromNullable(interactive),
                    Optional.fromNullable(title),
                    Optional.fromNullable(subtitle),
                    Optional.fromNullable(mediaAttachment),
                    Optional.fromNullable(sound),
                    Optional.fromNullable(mutableContent),
                    Optional.fromNullable(collapseId),
                    Optional.fromNullable(threadId));
        }
    }
}
