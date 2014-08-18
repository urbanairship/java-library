/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.wns;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;

public class WNSAudioData {
    public enum Sound {
        MUTE,
        DEFAULT,
        IM,
        MAIL,
        REMINDER,
        SMS,
        LOOPING_ALARM,
        LOOPING_ALARM2,
        LOOPING_CALL,
        LOOPING_CALL2;

        private final String id;

        Sound() {
            id = name().toLowerCase().replace('_', '-');
        }

        public String getIdentifier() {
            return id;
        }

        public static Sound get(String value) {
            for (Sound sound : values()) {
                if (value.equalsIgnoreCase(sound.getIdentifier())) {
                    return sound;
                }
            }
            return null;
        }
    }

    private final Sound sound;
    private final Optional<Boolean> loop;

    private WNSAudioData(Sound sound,
                         Optional<Boolean> loop)
    {
        this.sound = sound;
        this.loop = loop;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Sound getSound() {
        return sound;
    }

    public Optional<Boolean> getLoop() {
        return loop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WNSAudioData that = (WNSAudioData)o;
        if (sound != null ? !sound.equals(that.sound) : that.sound != null) {
            return false;
        }
        if (loop != null ? !loop.equals(that.loop) : that.loop != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (sound != null ? sound.hashCode() : 0);
        result = 31 * result + (loop != null ? loop.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private Sound sound;
        private Boolean loop;

        private Builder() { }

        public Builder setSound(Sound value) {
            this.sound = value;
            return this;
        }

        public Builder setLoop(Boolean value) {
            this.loop = value;
            return this;
        }

        public WNSAudioData build() {
            checkArgument(sound != null, "toast.audio must supply a value for 'sound'");
            return new WNSAudioData(sound,
                                    Optional.fromNullable(loop));
        }
    }
}
