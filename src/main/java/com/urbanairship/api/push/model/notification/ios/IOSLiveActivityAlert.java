/*
 * Copyright (c) 2013-2023.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.ios;

import com.urbanairship.api.push.model.PushModelObject;

import java.util.Objects;
import java.util.Optional;

public final class IOSLiveActivityAlert extends PushModelObject{
    private final Optional<String> title;
    private final Optional<String> body;
    private final Optional<String> sound;

    private IOSLiveActivityAlert(Optional<String> title, Optional<String> body, Optional<String> sound) {
        this.title = title;
        this.body = body;
        this.sound = sound;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getBody() {
        return body;
    }

    public Optional<String> getSound() {
        return sound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IOSLiveActivityAlert that = (IOSLiveActivityAlert) o;
        return Objects.equals(title, that.title) && Objects.equals(body, that.body) &&
                Objects.equals(sound, that.sound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, sound);
    }

    @Override
    public String toString() {
        return "IOSLiveActivityAlert{" +
                "title=" + title +
                ", body=" + body +
                ", sound=" + sound +
                '}';
    }

    public static class Builder{
        private String title = null;
        private String body = null;
        private String sound = null;

        private Builder() { }

        /**
         * Set the title for the IOSLiveActivityAlert.
         * @param title String
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the body for the IOSLiveActivityAlert.
         * @param body String
         * @return Builder
         */
        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        /**
         * Set the sound for the IOSLiveActivityAlert.
         * @param sound String
         * @return Builder
         */
        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        /**
         * Build IOSMediaContent
         * @return IOSMediaContent
         */
        public IOSLiveActivityAlert build() {
            return new IOSLiveActivityAlert(Optional.ofNullable(title),
                               Optional.ofNullable(body),
                               Optional.ofNullable(sound));
        }
    }
}