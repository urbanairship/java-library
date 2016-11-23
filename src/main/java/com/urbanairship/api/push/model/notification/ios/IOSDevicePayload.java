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

/**
 * IOSDevicePayload for iOS specific push messages.
 */
public final class IOSDevicePayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<IOSAlertData> alert;
    private final Optional<ImmutableMap<String, String>> extra;
    private final Optional<String> sound;
    private final Optional<IOSBadgeData> badge;
    private final Optional<Boolean> contentAvailable;
    private final Optional<PushExpiry> expiry;
    private final Optional<Integer> priority;
    private final Optional<String> category;
    private final Optional<Interactive> interactive;
    private final Optional<String> title;
    private final Optional<String> subtitle;
    private final Optional<MediaAttachment> mediaAttachment;
    private final Optional<Boolean> mutableContent;
    private final Optional<String> collapseId;

    private IOSDevicePayload(Optional<IOSAlertData> alert,
                             Optional<String> sound,
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
                             Optional<Boolean> mutableContent,
                             Optional<String> collapseId) {
        this.alert = alert;
        this.sound = sound;
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
        this.mutableContent = mutableContent;
        this.collapseId = collapseId;
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
     * Get the sound file name
     * @return Optional string representing the sound file name
     */
    public Optional<String> getSound() {
        return sound;
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
     * Get the Collapse ID String. When there is a newer message that renders an older, related message irrelevant to the client app,
     * the new message replaces the older message with the same collapse ID. Similar to the GCM collapse key. iOS 10.
     * @return Optional String representation of the collapse ID.
     */
    public Optional<String> getCollapseId() {
        return collapseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IOSDevicePayload that = (IOSDevicePayload)o;
        if (alert != null ? !alert.equals(that.alert) : that.alert != null) {
            return false;
        }
        if (extra != null ? !extra.equals(that.extra) : that.extra != null) {
            return false;
        }
        if (sound != null ? !sound.equals(that.sound) : that.sound != null) {
            return false;
        }
        if (badge != null ? !badge.equals(that.badge) : that.badge != null) {
            return false;
        }
        if (contentAvailable != null ? !contentAvailable.equals(that.contentAvailable) : that.contentAvailable != null) {
            return false;
        }
        if (expiry != null ? ! expiry.equals(that.expiry) : that.expiry != null) {
            return false;
        }
        if (priority != null ? ! priority.equals(that.priority) : that.priority != null) {
            return false;
        }
        if (category!= null ? ! category.equals(that.category) : that.category != null) {
            return false;
        }
        if (interactive!= null ? ! interactive.equals(that.interactive) : that.interactive != null) {
            return false;
        }
        if (title!= null ? ! title.equals(that.title) : that.title != null) {
            return false;
        }
        if(subtitle!= null ? ! subtitle.equals(that.subtitle) : that.subtitle != null) {
            return false;
        }
        if(mediaAttachment != null ? ! mediaAttachment.equals(that.mediaAttachment) : that.mediaAttachment != null) {
            return false;
        }
        if(mutableContent != null ? ! mutableContent.equals(that.mutableContent) : that.mutableContent != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (alert != null ? alert.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        result = 31 * result + (sound != null ? sound.hashCode() : 0);
        result = 31 * result + (badge != null ? badge.hashCode() : 0);
        result = 31 * result + (contentAvailable != null ? contentAvailable.hashCode() : 0);
        result = 31 * result + ( expiry != null ?  expiry.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (interactive != null ? interactive.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (mediaAttachment != null ? mediaAttachment.hashCode() : 0);
        result = 31 * result + (mutableContent != null ? mutableContent.hashCode() : 0);
        result = 31 * result + (collapseId != null ? collapseId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IOSDevicePayload{" +
            "alert=" + alert +
            ", extra=" + extra +
            ", sound=" + sound +
            ", badge=" + badge +
            ", expiry=" + expiry +
            ", priority" + priority +
            ", contentAvailable=" + contentAvailable +
            ", category=" + category +
            ", interactive=" + interactive +
            ", title=" + title +
            ", subtitle=" + subtitle +
            ", mediaAttachment=" + mediaAttachment +
            ", mutable_content=" + mutableContent +
            ", collapse_id=" + collapseId +
                '}';
    }

    public static class Builder {
        private IOSAlertData alert = null;
        private String sound = null;
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
        private Boolean mutableContent = null;
        private String collapseId = null;

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
         * Set the filename for the sound. A matching sound file that meets
         * Apple requirements needs to reside on the device.
         * @param sound Sound file name
         * @return Builder
         */
        public Builder setSound(String sound) {
            this.sound = sound;
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
         * Build IOSDevicePayload
         * @return IOSDevicePayload
         */
        public IOSDevicePayload build() {
            // Yes, empty payloads are valid (for Passes)
            return new IOSDevicePayload(Optional.fromNullable(alert),
                    Optional.fromNullable(sound),
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
                    Optional.fromNullable(mutableContent),
                    Optional.fromNullable(collapseId));
        }
    }
}
